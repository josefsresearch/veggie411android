package com.veggie411.veggie411;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ViewProductLoadingActivity extends Activity {
	private String barcode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_VIEW_PRODUCT, "CREATED");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_product_loading);
		barcode = getIntent().getExtras().getString(Constants.BARCODE);
		getCode(barcode);
	}

	private void getCode(String barcode) {
		//new GetItem(barcode).execute();
		//MainActivity.requesting = true;
		if (MainActivity.curProduct == null) {
			Toast.makeText(this, "SORRY! We do not have this product yet, but we hope to have it soon.", Toast.LENGTH_LONG).show();
			setResult(Activity.RESULT_CANCELED, new Intent());
			finish();
		}
		setResult(Activity.RESULT_OK, new Intent());
		finish();
	}
}
