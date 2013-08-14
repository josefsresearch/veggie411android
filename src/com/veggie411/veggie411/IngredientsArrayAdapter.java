package com.veggie411.veggie411;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IngredientsArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> objects;
	Builder alertDialog;
	
	public IngredientsArrayAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
		//setUpDialog(context);
	}
	
	public ArrayList<String> getObjects() {
		return this.objects;
	}
	
	public void setUpDialog(Context context) {
		alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("GELATIN");
		alertDialog.setIcon(R.drawable.fail_small);
		alertDialog.setMessage("Derived from pork / horses or cattle bones");
		alertDialog.setCancelable(true);
		alertDialog.setNeutralButton("DONE", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//cancel
			}
		});
	}
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
		
	}
	
	public String[] getAllIngredients() {
		String[] ret = new String[objects.size()];
		for (int i=0;i<objects.size();i++) {
			ret[i] = objects.get(i);
		}
		return ret;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		//convertView.setClickable(true);
		//convertView.setFocusable(true);
		Log.i("getview", "called");
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_ingredient, parent, false);
		}
		
		String cur = objects.get(position);
		if (cur != null) {
			TextView ingredient = (TextView) convertView.findViewById(R.id.list_item_ingredient_name);
			ImageView resultImage = (ImageView) convertView.findViewById(R.id.list_item_ingredient_result_image);
			ingredient.setText(cur);
			if (MainActivity.blacklistDatabase.containsKey(cur) &&
					MainActivity.blacklistDatabase.get(cur) == false) {
				resultImage.setImageResource(R.drawable.fail_small);
			} else {
				resultImage.setImageResource(R.drawable.success_small);
			}
		}
		convertView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					arg0.setBackgroundColor(0x9cc13b);
				} else {
					arg0.setBackgroundColor(0xFFFFFF);
				}
				return false;
			}
		});
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.show();
			}
		});
		return convertView;
	}

}
