package com.veggie411.veggie411;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	
	private PreferencesDataSource dataSource;

	public SimpleArrayAdapter(Context context, String[] values) {
		super(context, R.layout.list_item_ingredient, values);
		this.context = context;
		this.values = values;
	}
	
	static class ViewHolder {
	    public TextView text;
	    public ImageView image;
	  }
	
	public String[] getValues() {
		return values;
	}

	  private int getIndex() {
		for (int i=0; i<values.length;i++) {
			if (values[i] == null) {
				return i;
			}
		}
		return -1;
	}

	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View rowView = convertView;
	    if (rowView == null) {
	      LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	      rowView = inflater.inflate(R.layout.list_item_ingredient, null);
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.text = (TextView) rowView.findViewById(R.id.list_item_ingredient_name);
	      viewHolder.image = (ImageView) rowView
	          .findViewById(R.id.list_item_ingredient_result_image);
	      rowView.setTag(viewHolder);
	    }

	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    String s = values[position];
	    holder.text.setText(s);
	    if (MainActivity.blacklistDatabase.containsKey(s)) {
	    	if (MainActivity.blacklistDatabase.get(s) == false) {
	    		holder.image.setImageResource(R.drawable.fail_small);
	    		//Log.i("ingredient", "blacklisted");
		    } else {
		      holder.image.setImageResource(R.drawable.success_small);
		      //Log.i("ingredient", "whitelisted");
		    }
	    } else {
	    	//Log.i("ingredient", "not blacklisted");
	    }
	    return rowView;
	  }
	} 