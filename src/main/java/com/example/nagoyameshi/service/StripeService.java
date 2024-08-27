package com.example.nagoyameshi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.SubscriptionRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	
	private final SubscriptionService subscriptionService;
	private final UserService userService;
	private final SubscriptionRepository subscriptionRepository;
	
	public StripeService(SubscriptionService subscriptionService, UserService userService, SubscriptionRepository subscriptionRepository) {
		this.subscriptionService = subscriptionService;
		this.userService = userService;
		this.subscriptionRepository = subscriptionRepository;
	}
	
	// セッションを作成し、Stripeに必要な情報を返す
	public String createStripeSession(User user, HttpServletRequest httpServletRequest) {
		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		
		SessionCreateParams params =
				SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)//支払方法(CARD)
				.setCustomerEmail(user.getEmail())// 顧客のメールアドレスを設定
				.addLineItem(
						SessionCreateParams.LineItem.builder()
						.setPriceData(
							SessionCreateParams.LineItem.PriceData.builder()
								.setCurrency("jpy")
								.setUnitAmount(300L)//金額（300円）
								.setProductData(
										SessionCreateParams.LineItem.PriceData.ProductData.builder()
										.setName("Monthly Subscription")
										.build())
								.setRecurring(
										SessionCreateParams.LineItem.PriceData.Recurring.builder()
										.setInterval(SessionCreateParams.LineItem.PriceData.Recurring.Interval.MONTH)
										.build())
								.build())
						.setQuantity(1L)
						.build())
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.setSuccessUrl(requestUrl.replace("/subscription/confirm", ""))
				.setCancelUrl(requestUrl.replace("/confirm",""))
				.build();
		try {
			Session session = Session.create(params);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace();
			return "";
		}
	}
    
	// セッションから顧客のIDから情報を取得し、ReservationServiceとUserServiceクラスを介してデータベースに登録する
	public void processSessionCompleted(Event event) {
	    System.out.println("Webhook Event Received: " + event.getType());

	    Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
	    optionalStripeObject.ifPresentOrElse(stripeObject -> {
	        System.out.println("サブスクリプションの登録処理が成功しました。");
	        System.out.println("Stripe API Version:" + event.getApiVersion());
	        System.out.println("stripe-java Version:" + Stripe.VERSION);

	        if (stripeObject instanceof Session) {
	            System.out.println("StripeObject is instance of Session.");
	            Session session = (Session) stripeObject;
	            String stripeCustomerId = session.getCustomer();
	            String stripeSubscriptionId = session.getSubscription();
	            System.out.println("Customer ID: " + stripeCustomerId);
	            System.out.println("Subscription ID: " + stripeSubscriptionId);
	            
	            try {
	                Customer customer = Customer.retrieve(stripeCustomerId);
	                String email = customer.getEmail();
	                System.out.println("Customer Email: " + email);
	                
	                subscriptionService.create(email, stripeCustomerId, stripeSubscriptionId);
	                userService.roleUpgrade(email);
	                System.out.println("サブスクリプションの登録処理が成功しました。");
	            } catch (StripeException e) {
	                System.err.println("StripeException: " + e.getMessage());
	                e.printStackTrace();
	            }
	        } else {
	            System.err.println("Unexpected object type: " + stripeObject.getClass().getName());
	        }
	    }, () -> {
	        // エラーログを追加して詳細を出力
	        System.err.println("サブスクリプションの登録処理が失敗しました。StripeObject not present.");
	        System.err.println("Event Data Object Deserializer JSON: " + event.getDataObjectDeserializer().getRawJson());
	        System.err.println("Event Type: " + event.getType());
	        System.err.println("Stripe API Version: " + event.getApiVersion());
	        System.err.println("stripe-java Version: " + Stripe.VERSION);
	    });
	}

	
	public void updateSubscription(Event event) {
	    Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
	    optionalStripeObject.ifPresentOrElse(stripeObject -> {
	        System.out.println("サブスクリプションの更新処理が成功しました。");
	        System.out.println("Stripe API Version:" + event.getApiVersion());
	        System.out.println("stripe-java Version:" + Stripe.VERSION);
	        Session session = (Session) stripeObject;
	        String stripeCustomerId = session.getCustomer();
	        String stripeSubscriptionId = session.getSubscription();
	        
	        try {
	            Customer customer = Customer.retrieve(stripeCustomerId);
	            String email = customer.getEmail();
	            
	            subscriptionService.update(email, stripeSubscriptionId);
	            userService.roleUpgrade(email);
	        } catch (StripeException e) {
	            e.printStackTrace();
	        }
	    },
	    () -> {
	        System.out.println("サブスクリプションの更新処理が失敗しました。");
	        System.out.println("Stripe API Version:" + event.getApiVersion());
	        System.out.println("stripe-java Version:" + Stripe.VERSION);
	    });
	}


	// サブスクリプションをキャンセルするメソッド（手動）
    public void cancelSubscription(String subscriptionId) {
        try {
        	com.stripe.model.Subscription subscription = com.stripe.model.Subscription.retrieve(subscriptionId);
            subscription.cancel();
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
	
	//サブスクキャンセル時のデータ削除(カードの不備などで払えない場合　自動)
	public void delete(Event event) {
		Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
		optionalStripeObject.ifPresent(stripeObject -> {
			Subscription subscription = (Subscription)stripeObject;
			String subscriptionId = subscription.getId();

			subscriptionRepository.deleteByStripeSubscriptionId(subscriptionId);
			//userService.rolegrade(email);
		});
	}
}
