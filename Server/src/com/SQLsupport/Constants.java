package com.SQLsupport;

public class Constants {
    public static final String DB_NAME="db7";

    public static final String USER_SCHEMA="user";
    public static final String USER_ID="id_user";
    public static final String USER_LOGIN="login";
    public static final String USER_PASSWORD="password";
    public static final String USER_ROLE="role";
    public static final String USER_FIRST_NAME="first_name";
    public static final String USER_LAST_NAME="last_name";
    public static final String USER_MONEY="money";
    public static final String USER_ADDRESS="address";
    public static final String USER_PHONE="phone";

    public static final String MANUFACTURER_SCHEMA="manufacturer";
    public static final String MANUFACTURER_ID="id_manufacturer";
    public static final String MANUFACTURER_NAME="name";
    public static final String MANUFACTURER_COUNTRY="country";
    public static final String MANUFACTURER_EMAIL="email";
    public static final String MANUFACTURER_RATING="rating";
    public static final String MANUFACTURER_NUMBER="number_of_ratings";

    public static final String CAR_SCHEMA="car";
    public static final String CAR_ID="id_product";
    public static final String CAR_NAME="name";
    public static final String CAR_TYPE="type";
    public static final String CAR_COST="cost";
    public static final String CAR_COUNT="count";
    public static final String CAR_MANUFACTURER="id_manufacturer";

    public static final String REVIEW_SCHEMA="review";
    public static final String REVIEW_ID="id_review";
    public static final String REVIEW_USER="id_user";
    public static final String REVIEW_CAR="id_product";
    public static final String REVIEW_TEXT="review_text";

    public static final String PURCHASE_SCHEMA="purchase";
    public static final String PURCHASE_ID="id_purchase";
    public static final String PURCHASE_USER="id_user";
    public static final String PURCHASE_CAR="id_product";

    public static final String FAQ_SCHEMA="faq";
    public static final String FAQ_QUESTION="question";
    public static final String FAQ_ANSWER="answer";

    public static final String REBATE_SCHEMA="rebate";
    public static final String REBATE_ID="id_rebate";
    public static final String REBATE_USER="id_user";
    public static final String REBATE_PERCENT="percent";
    public static final String REBATE_MANUFACTURER="id_manufacturer";

}
