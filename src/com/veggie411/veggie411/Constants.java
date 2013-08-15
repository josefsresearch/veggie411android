package com.veggie411.veggie411;

import java.net.URI;

public class Constants {
	protected final static String BARCODE = "BARCODE";
	
	protected final static int RESULT_SCAN = 49374;
	protected final static int RESULT_PRODUCTS = 2;
	protected final static int RESULT_MORE = 3;
	protected static final int RESULT_ADD_PRODUCT = 4;
	protected static final int RESULT_VIEW_PRODUCT = 5;
	
	protected final static int RESULT_INGREDIENTS = 6;
	protected static final int RESULT_PROFILE = 7;
	protected static final int RESULT_EDIT_PROFILE = 8;
	public static final int RESULT_SIGN_IN = 9;
	public static final int RESULT_BACK = 10;
	public static final int RESULT_VIEW_PRODUCT_LOADING = 11;
	
	
	public static final int UNITS = 13;
	public static final int PACKAGE = 14;
	
	//change to int instead of string
	protected static final String ACTIVITY_MAIN = "MAIN";
	protected static final String ACTIVITY_ADD_PRODUCT = "ADD_PRODUCT";
	protected static final String ACTIVITY_VIEW_PRODUCT = "VIEW_PRODUCT";
	public static final String ACTIVITY_MORE = "MORE";
	public static final String ACTIVITY_PROFILE = "PROFILE";
	public static final String ACTIVITY_EDIT_PROFILE = "EDIT_PROFILE";
	public static final String ACTIVITY_PRODUCT_LIST = "PRODUCT_LIST";
	public static final String ACTIVITY_INGREDIENTS = "INGREDIENTS";
	
	protected static final String CREATED = "CREATED";
	protected static final String ON_RESUME = "ON_RESUME_CALLED";
	protected static final String ON_PAUSE = "ON_PAUSE_CALLED";

	protected static final String ALERT_SELECT_UNIT = "Please select the unit for size.";
	protected static final String ALERT_SELECT_PACKAGING = "Please select the type of packaging.";
	protected static final String ALERT_SELECT_NAME = "Please input the name of the item.";
	protected static final String ALERT_SELECT_BRAND = "Please input the brand of the item.";

	public static final String SERVER_URI = "www.test.com";

	public static final CharSequence TOAST_NOT_IMPLEMENTED = "Sorry feature not implemented yet. Coming soon!";
	public static final CharSequence TOAST_NO_PRODUCT = "Sorry, we do not have this procuct but we are hoping to have it soon!";

	public static final String URI = "https://api.nutritionix.com/v1_1/item?appId=82d7fc68&appKey=42574fb537accef0d28c1003cbb50826&upc=";

	protected static final String SP_NAME = "NAME";
	protected static final String SP_CITY = "CITY";
	protected static final String SP_FILE = "VEG";
	protected static final String SP_STATUS = "STATUS";
	protected static final String SP_DATABASE_UP_TO_DATE = "DATABASE_UP_TO_DATE";

	public static final String I_NAME = "INGREDIENT_NAME";
	public static final String I_ENAME = "INGREDIENT_ENAME";
	public static final String I_REFERENCES = "INGREDIENT_REFERENCES";
	public static final String I_STATUS = "INGREDIENT_STATUS";
	public static final String I_DESCRIPTION = "INGREDIENT_DESCRIPTION";

	public static final String VEGAN_NAME = "VEGAN";
	public static final int VEGAN_INT = 0;
	public static final String VEGETARIAN_NAME = "VEGETARIAN";
	public static final int VEGETARIAN_INT = 1;
	public static final String LACTO_OVO_NAME = "LACTO-OVO";
	public static final int LACTO_OVO_INT = 2;
	public static final String PESCATARIAN_NAME = "PESCATARIAN";
	public static final int PESCATARIAN_INT = 3;
	

}
