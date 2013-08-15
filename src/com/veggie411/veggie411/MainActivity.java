package com.veggie411.veggie411;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageButton scanButton;
	private ImageButton productsButton;
	private ImageButton ingredientsButton;
	private ImageButton moreButton;
	private Intent viewProductActivity;
	private Intent productListActivity;
	private Intent ingredientsActivity;
	private Intent signInActivity;

	final IntentIntegrator integrator = new IntentIntegrator(this);	

	protected static HashMap<String, Ingredient> ingredientDatabase = new HashMap<String, Ingredient>();
	protected static HashMap<String, Boolean> blacklistDatabase = new HashMap<String, Boolean>();

	private PreferencesDataSource pDataSource;
	private SharedPreferences sp;

	protected static Product curProduct = null;

	private Builder alertDialog;
	private Toast featureNotImplemented;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_MAIN, Constants.CREATED);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = getSharedPreferences(Constants.SP_FILE, MODE_PRIVATE);
		if (!sp.getBoolean(Constants.SP_DATABASE_UP_TO_DATE, false)) {
			Log.i("DB not set up!", "setting up.....");
			new SetUpApp(this).execute();
		}
		alertDialog = new AlertDialog.Builder(this);
		scanButton = (ImageButton) findViewById(R.id.main_scan);
		productsButton = (ImageButton) findViewById(R.id.main_products);
		ingredientsButton = (ImageButton) findViewById(R.id.main_ingredients);
		moreButton = (ImageButton) findViewById(R.id.main_more);
		viewProductActivity = new Intent(this, ViewProductActivity.class);
		productListActivity = new Intent(this, ProductListActivity.class);
		ingredientsActivity = new Intent(this, IngredientsActivity.class);
		featureNotImplemented = Toast.makeText(this, Constants.TOAST_NOT_IMPLEMENTED, Toast.LENGTH_SHORT);		
		pDataSource = new PreferencesDataSource(this);
		if (sp.getString(Constants.SP_NAME, "") == "") {
			final Dialog d = new Dialog(this);
			d.setContentView(R.layout.dialog_signin);
			final ImageButton signInButton = (ImageButton) d.findViewById(R.id.signin_button);
			final EditText signInName = (EditText) d.findViewById(R.id.signin_name);
			final EditText signInCity = (EditText) d.findViewById(R.id.signin_city);
			d.setCancelable(false);
			signInButton.setOnClickListener(new OnClickListener() {
				String n;
				String c;
				@Override
				public void onClick(View v) {
					n = signInName.getText().toString();
					Log.i("N", n);
					c = signInCity.getText().toString();
					Log.i("C", c);
					if (n.matches("")) {
						Toast.makeText(MainActivity.this, "Please fill in your name", Toast.LENGTH_SHORT).show();
					} else if (c.matches("")) {
						Toast.makeText(MainActivity.this, "Please fill in your city", Toast.LENGTH_SHORT).show();
					} else {
						Editor spEditor = sp.edit();
						spEditor.putString(Constants.SP_NAME, n);
						spEditor.putString(Constants.SP_CITY, c);
						spEditor.commit();
						Log.i("sp after edit", sp.getString(Constants.SP_NAME, ""));
						d.cancel();
					}
				}
			});
			d.show();
		} else {
		}
		fillPreferenceHash();

		scanButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					scanButton.setBackgroundResource(R.drawable.scan_pressed);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					scanButton.setBackgroundResource(R.drawable.scan);
				}
				return false;
			}
		});
		scanButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		
				scanButton.setBackgroundResource(R.drawable.scan);
				Log.i("SCANNER", "CALLED");
				if (connectedToInternet()) {
					integrator.initiateScan();
				} else {
					//TODO
					//connect TO INTERNET
				}
			}
		});
		productsButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					//productsButton.getBackground().setColorFilter(0x84b976, PorterDuff.Mode.MULTIPLY);
					productsButton.setBackgroundResource(R.drawable.product_list_pressed);

				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					//productsButton.getBackground().clearColorFilter();
					productsButton.setBackgroundResource(R.drawable.product_list);
				}
				return false;
			}
		});
		productsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		
				featureNotImplemented.show();
				//startActivity(productListActivity);
				//g.execute();
				//viewProductActivity.putExtra(Constants.BARCODE, "52200004265");
				//startActivityForResult(viewProductActivity, Constants.RESULT_VIEW_PRODUCT);
			}
		});
		ingredientsButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					ingredientsButton.setBackgroundResource(R.drawable.ingredients_pressed);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					ingredientsButton.setBackgroundResource(R.drawable.ingredients);
				}
				return false;
			}
		});
		ingredientsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//featureNotImplemented.show();
				startActivity(ingredientsActivity);
			}
		});
		moreButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					moreButton.setBackgroundResource(R.drawable.more_pressed);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					moreButton.setBackgroundResource(R.drawable.more);
				}
				return false;
			}
		});
		moreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				Intent addProductActivity = new Intent(MainActivity.this, MoreActivity.class);
				startActivity(addProductActivity);
			}
		});
	}

	private void fillPreferenceHash() {
		pDataSource.open();
		List<Preference> list = pDataSource.getAllPreferences();
		blacklistDatabase = new HashMap<String, Boolean>();
		for (Preference p:list) {
			blacklistDatabase.put(p.getIngredient(), p.isEdible());
		}
	}

	private boolean connectedToInternet() {
		//TODO if connected return true, not false
		return true;
	}

	protected void onResume() {
		Log.i(Constants.ACTIVITY_MAIN, Constants.ON_RESUME);
		super.onResume();
		pDataSource.open();
		for (Preference p:pDataSource.getAllPreferences()) {
			if (!blacklistDatabase.containsKey(p.getIngredient())) {
				blacklistDatabase.put(p.getIngredient(), p.isEdible());
			}
		}
	}

	protected void onPause() {
		Log.i(Constants.ACTIVITY_MAIN, Constants.ON_PAUSE);
		super.onPause();
		pDataSource.close();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.i("REQUEST CODE IS", String.valueOf(requestCode));
		if (resultCode == RESULT_CANCELED){
			if (requestCode == Constants.RESULT_VIEW_PRODUCT) {
				Toast.makeText(this, "Sorry, we do not have this procuct but we are hoping to have it soon!", Toast.LENGTH_LONG);
			}
		} else if (requestCode == Constants.RESULT_SCAN) {
			IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			if (scanResult != null && scanResult.getContents() != null) {
				String code = scanResult.getContents();
				new GetItem(this, code).execute(); 
				//				Intent viewProductActivity = new Intent(this, ViewProductActivity.class);
				//				viewProductActivity.putExtra(Constants.BARCODE, code);
				//				startActivityForResult(viewProductActivity, Constants.RESULT_VIEW_PRODUCT);
			} else {
				//error
			}
		} else {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
