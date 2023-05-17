package vn.btl.hdvauthenservice.utils;

public interface Constants {
	public static final String FINANCE_SETTING_TYPE_QR = "QR";
	String REQUEST_ID_KEY = "requestId";

	public static final String FINANCE_SETTING_TYPE_BANK = "BANK";

	public static final int STATUS_ACTIVE = 1;
	String PAYLOAD = "payload";

	// Order Status
	interface STATUS_ORDER {
		int DRAFT = 0;
		int COMPLETED = 1;
		int UNCONFIRMED = 2;
		int CONFIRMED = 3;
		int CUSTOMER_CANCELLED = 4;
		int SHOP_CANCELLED = 5;
		int DELIVERY = 6;
		int DELIVERY_CONFIRMED = 7;
	}

	interface SALEOFF_TYPE {
		int DISCOUNT_MONEY = 0;
		int DISCOUNT_PERCENT = 1;
	}

	interface CALCULATION_TYPE {
		String DISCOUNT_MONEY = "1";
		String DISCOUNT_PERCENT = "2";
	}

	interface TARGET_TYPE {
		String SYSTEM = "1";
		String SHOP = "2";
	}

	interface HEADER {
		String REQUEST_ID_KEY = "requestId";
		String CUSTOMER_ID = "customerId";
		String CUSTOMER_PHONE = "phone";
		String CUSTOMER_TOKEN = "token";
	}

	interface LOYALTY_ACTIVITY {
		String BIRTHDAY_SHOP = "1";
		String BIRTHDAY_CUSTOMER = "2";
		String ORDER_VALUE = "3";
	}

	interface EMAIL_VERIFY_TYPE {
		static final String REGISTER = "REGISTER";
		static final String FORGOT_PASSWORD = "FORGOT_PASSWORD";
	}
}
