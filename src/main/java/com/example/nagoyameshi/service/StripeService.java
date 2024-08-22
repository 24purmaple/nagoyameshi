package com.example.nagoyameshi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	
	public StripeService() {
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
				.setSuccessUrl(requestUrl.replace("subscription//confirm", ""))
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

	// サブスクリプションをキャンセルするメソッド
    public void cancelSubscription(String subscriptionId) {
        try {
        	com.stripe.model.Subscription subscription = com.stripe.model.Subscription.retrieve(subscriptionId);
            subscription.cancel();
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
	
 /* サブスクリプションをキャンセルするメソッド
    public void cancelSubscription(String user) {
        try {
            Subscription subscription = Subscription.retrieve(user);
            subscription.cancel();
        } catch (StripeException e) {
            e.printStackTrace();
        }
    } */
    
	/*// セッションから予約情報を取得し、ReservationServiceクラスを介してデータベースに登録する
	public void processSessionCompleted(Event event) {
		Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
		optionalStripeObject.ifPresentOrElse(stripeObject -> {
			Session session = (Session)stripeObject;
			SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("payment_intent").build();
			
			try {
				session = Session.retrieve(session.getId(), params, null);
				Map<String, String> paymentIntentObject = session.getPaymentIntentObject().getMetadata();
				subscriptionService.create(paymentIntentObject);
			} catch (StripeException e) {
				e.printStackTrace();
			}
			System.out.println("予約一覧ページの登録処理が成功しました。");
			System.out.println("Stripe API Version:" + event.getApiVersion());
			System.out.println("stripe-java Version:" + Stripe.VERSION);
		},
		() -> {
			System.out.println("予約一覧ページの登録処理が失敗しました。");
			System.out.println("Stripe API Version:" + event.getApiVersion());
			System.out.println("stripe-java Version:" + Stripe.VERSION);
		});
	}*/


}
