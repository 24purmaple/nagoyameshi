// Stripeの公開可能なキーを使って、Stripeのインスタンスを作成
const stripe = Stripe('pk_test_51PSqfXCpRO9yD5E5jvROk7bCuvI9FnIDmMUgPASXg6hmbRo0bnLCuTpI6yd88r0ScnNDOsKtUHQcck2HiMBbfswo00Qm5MuAAb');
// DOMから支払いボタンを取得
const paymentButton = document.querySelector('#paymentButton');

// 支払いボタンがクリックされたときに実行される処理
paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout ({
		// チェックアウトセッションのIDを指定
		sessionId: sessionId
	})
});