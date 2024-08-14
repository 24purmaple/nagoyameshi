// Flatpickrの初期化
let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

flatpickr("#reservationDate", {
	dateFormat: "Y-m-d",
	minDate: "today",
	maxDate: maxDate,
	locale: 'ja' // 日本語に設定
	});
	
flatpickr("#reservationTime", {
	enableTime: true,
	noCalendar: true,
	dateFormat: "H:i",
	time_24hr: true
	});