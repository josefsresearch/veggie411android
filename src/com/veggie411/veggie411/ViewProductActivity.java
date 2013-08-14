package com.veggie411.veggie411;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewProductActivity extends Activity {
	private SimpleArrayAdapter adapter;
	private ArrayList<String> ingredients;

	private String barcode;

	private TextView productBarcode;
	private TextView productName;
	private TextView productBrand;
	private ListView listView;
	private ImageButton done;
	private Product product;
	private ImageView resultImage;
	private ImageButton nutritionix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_VIEW_PRODUCT, "CREATED");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_product);
		barcode = getIntent().getExtras().getString(Constants.BARCODE);

		ingredients = new ArrayList<String>();
		productBarcode = (TextView) findViewById(R.id.view_product_barcode);
		productBarcode.setText("#"+barcode);
		productName = (TextView) findViewById(R.id.view_product_name);
		productBrand = (TextView) findViewById(R.id.view_product_brand);
		resultImage = (ImageView) findViewById(R.id.view_product_result_image);
		nutritionix = (ImageButton) findViewById(R.id.nutritionix_poweredby);
		listView = (ListView) findViewById(R.id.view_product_ingredients_list);
		View header = (View)getLayoutInflater().inflate(R.layout.list_ingredients_header, null);
		listView.addHeaderView(header);
		done = (ImageButton) findViewById(R.id.view_product_done);
		nutritionix.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					//TODO add shade highlight?
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					//TODO sett 
				}
				return false;
			}
		});
		nutritionix.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nutritionix.com/api"));
				startActivity(browserIntent);			
			}
		});

		done.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					done.setBackgroundResource(R.drawable.done_pressed);
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					done.setBackgroundResource(R.drawable.done);
				}
				return false;
			}
		});
		done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_OK, new Intent());
				MainActivity.curProduct = null;
				finish();
			}
		});
//		while (MainActivity.requesting){
//		}
		product = MainActivity.curProduct;
		if (product == null) {
			Log.i("WEIRD", "WEIRD");
			setResult(Activity.RESULT_CANCELED, new Intent());
			finish();
		} else {
			Log.i("PRODUCT", product.getName());
			ingredients = product.getIngredients();
			boolean clean = true;
			String[] ingredientsArray = new String[ingredients.size()];
			int i = 0;
			for (String ing:ingredients) {
				if (MainActivity.blacklistDatabase.containsKey(ing) && 
						MainActivity.blacklistDatabase.get(ing) == false) {
					clean = false;
				}
				ingredientsArray[i] = ing;
				i++;
			}
			if (clean) {
				resultImage.setImageResource(R.drawable.success);
			} else {
				resultImage.setImageResource(R.drawable.fail);
			}
			productName.setText(product.getName());
			productBrand.setText(product.getBrand());
			//adapter = new IngredientsArrayAdapter(this, android.R.layout.simple_list_item_1, ingredients);
			adapter = new SimpleArrayAdapter(this, ingredientsArray);
			listView.setAdapter(adapter);
			//listView.setItemsCanFocus(true);
		}
	}

	public void onResume() {
		super.onResume();
		Log.i("ONRESUME", "CALLED");
	}

}