package com.veggie411.veggie411;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddProductActivity extends Activity {

	//private TextView instructions;
	private EditText brandInput;
	private EditText nameInput;
	//private ExpandableListView packagingType;
	//private EditText sizeInput;
	//private ExpandableListView sizeUnit;
	private TextView allIngredients;
	private EditText ingredientsInput;
	private ImageButton addIngredient;
	private ImageView productImage;
	private ImageButton uploadImage;
	private ImageButton save;
	private ImageButton cancel;

	private final static String INSTRUCTIONS = "Input #";
	private final static String INSTURCTIONS_END = " = 10 points";

	private String barcode;
	private Builder alertDialog;
	private byte[] image;
	private ListView listView;
	private ArrayList<String> ingredients;
	private IngredientsArrayAdapter adapter;
	private String brand = "";
	private String name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_ADD_PRODUCT, Constants.CREATED);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
		barcode = getIntent().getExtras().getString(Constants.BARCODE);
		alertDialog = new AlertDialog.Builder(this);
		//instructions = (TextView) findViewById(R.id.add_product_instructions);
		//instructions.setText(INSTRUCTIONS+barcode+INSTURCTIONS_END);
		brandInput = (EditText) findViewById(R.id.add_product_brand_input);
		nameInput = (EditText) findViewById(R.id.add_product_name_input);
		//allIngredients = (TextView) findViewById(R.id.add_product_ingredients_feedback);
		//packagingType = (ExpandableListView) findViewById(R.id.add_product_packaging_options);
		//final ExpandableListAdapter packagingListAdapter = new ExpandableListAdapter((Context) this, 1);
		//packagingType.setAdapter(packagingListAdapter);
		//sizeInput = (EditText) findViewById(R.id.add_product_size_input);
		//sizeUnit = (ExpandableListView) findViewById(R.id.add_product_size_unit);
		//final ExpandableListAdapter unitListAdapter = new ExpandableListAdapter((Context) this, 0);
		//sizeUnit.setAdapter(unitListAdapter);
		listView = (ListView) findViewById(R.id.add_product_ingredients_list);
		View header = (View)getLayoutInflater().inflate(R.layout.list_ingredients_header, null);
		listView.addHeaderView(header);
		ingredients = new ArrayList<String>();
		adapter = new IngredientsArrayAdapter(this, android.R.layout.simple_list_item_1, ingredients);
		listView.setAdapter(adapter);
		addIngredient = (ImageButton) findViewById(R.id.add_product_add_ingredient);
		
		ingredientsInput = (EditText) findViewById(R.id.add_product_ingredients_input);
		productImage = (ImageView) findViewById(R.id.add_product_image);
		uploadImage = (ImageButton) findViewById(R.id.add_product_upload_image);
		save = (ImageButton) findViewById(R.id.add_product_save);
		cancel = (ImageButton) findViewById(R.id.add_product_cancel);
		final Toast t = Toast.makeText(this, "Thank you, you are eligible to earn up to 10 points.", Toast.LENGTH_SHORT);//TODO

//		listView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> lv, View v, int position,
//					long id) {
//				if (id != R.id.list_ingredients_header) {
//					adapter.remove(adapter.getItem(position));
//					adapter.notifyDataSetChanged();
//				}
//			}
//		});
		addIngredient.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String ingred = ingredientsInput.getText().toString();
				ingredientsInput.setText("");
				adapter.add(ingred);
                adapter.notifyDataSetChanged();
			}
		});
		uploadImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = nameInput.getText().toString();
				brand = brandInput.getText().toString();
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, 5);
			}
		});
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = nameInput.getText().toString();
				brand = brandInput.getText().toString();
				if (brand != "" && name != "") { //TODO check if empty
					alertDialog.setTitle("Please Confirm");
					alertDialog.setMessage(brandInput.getText().toString() + " "
							+ nameInput.getText().toString() + ". "
							//+ sizeInput.getText().toString()
							//+ unit + " "
							//+ packaging + ". "
							+ ingredients.toString());

					alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							//TODO send to server. 
							Product p = new Product(barcode);
							p.setName(nameInput.getText().toString());
							p.setBrand(brandInput.getText().toString());
							for (String ing:ingredients) {
								p.addIngredient(ing);
							}
							p.setImage(image);
							t.show();
							//MainActivity.tempProductDatabase.put(barcode, p);
							//new thread to upload to server//TODO
							finish();
						}
					});

					alertDialog.setNegativeButton("Make Changes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//cancel
						}
					});
				} else {
					alertDialog.setTitle("Oops, you missed something");
					if (name == "") {
						alertDialog.setMessage(Constants.ALERT_SELECT_NAME);
						alertDialog.setCancelable(true);
					} else if (brand == "") {
						alertDialog.setMessage(Constants.ALERT_SELECT_BRAND);
						alertDialog.setCancelable(true);
					} else {
						Log.e("ADD_PRODUCT", "LOGIC NOT WORKING");
					}
				}
				alertDialog.show();
			}

			private String getItemFromList(int index, String[] arr) {
				if (index == -1) {
					return null;
				}
				return arr[index];
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				finish();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//productImage.setImageBitmap(bitmap);
		brandInput.setText(brand);
		nameInput.setText(name);
		adapter.notifyDataSetChanged();
	}
	
	//TODO
	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null && data.getExtras() != null) {
			Bitmap bitmap = (Bitmap)data.getExtras().get("data");
			Log.i("Height is ", String.valueOf(bitmap.getHeight()));
			productImage.setImageBitmap(bitmap);
			int bytes = bitmap.getByteCount();
			ByteBuffer buffer = ByteBuffer.allocate(bytes); 
			bitmap.copyPixelsToBuffer(buffer); 
			image = buffer.array();
		}
	}
}
