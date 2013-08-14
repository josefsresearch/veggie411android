package com.veggie411.veggie411;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ProductListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_PRODUCT_LIST, "CREATED");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		
	}
	
}
