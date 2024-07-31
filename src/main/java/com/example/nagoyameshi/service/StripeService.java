package com.example.nagoyameshi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SubscriptionForm;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	
	public StripeService() {
	}
	
	// セッションを作成し、Stripeに必要な情報を返す
	public String createStripeSession(User user, SubscriptionForm subscriptionForm) {
		Stripe.apiKey = stripeApiKey;
		
		SessionCreateParams params =
				SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)//支払方法(CARD)
				.setCustomerEmail(user.getEmail())
				.addLineItem(
						SessionCreateParams.LineItem.builder()
						.setPriceData(
							SessionCreateParams.LineItem.PriceData.builder()
								.setUnitAmount(30000L)//金額（300円）
								.setCurrency("jpy")
								.setProductData(
										SessionCreateParams.LineItem.PriceData.ProductData.builder()
										.setName("Monthly Subscription")
										.build())
								.build())
						.setQuantity(1L)
						.build())
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.setSuccessUrl("http://localhost:8080/subscription/success")
				.setCancelUrl("http://localhost:8080/subscription/canceled")
				.putMetadata("cardHolderName", subscriptionForm.getCardHolderName())
				.putMetadata("cardNumber", subscriptionForm.getCardNumber())
				.putMetadata("expiryMonth", subscriptionForm.getExpiryMonth())
				.putMetadata("expiryYear", subscriptionForm.getExpiryYear())
				.putMetadata("securityCode", subscriptionForm.getSecurityCode())
				.putMetadata("postalCode", subscriptionForm.getPostalCode())
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
