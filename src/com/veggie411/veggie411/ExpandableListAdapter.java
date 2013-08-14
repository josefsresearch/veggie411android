package com.veggie411.veggie411;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context = null;
	//private final String[] groups = {"Please Select One"};
	//private final String[][] packages = {{"box", "can", "bottle", "package", "wrapper", "case", "container","other"}};
	//private final String[][] units = {{"g", "ml", "oz", "KG", "L", "LB", "PT", "QT", "other"}};
	private final String[][] packages = {{"other"}};
	private final String[][] units = {{"g", "ml", "oz", "KG", "L", "LB", "PT", "QT", "other"}};
	//private final String[][] values;

	//	public ExpandableListAdapter(Context context, String[] groups, String[] values) {
	//		super();
	//		this.context = context;
	//		this.groups = groups;
	//		this.values = values;
	//	}

	private String[] groups = {
			"Please Select One"
	};

	private String[][] children = {{"box", "can", "bottle", "package", "wrapper", "case", "container","other"}};

	public ExpandableListAdapter(Context context, int i) {
		this.context = context;
		if (i>0) {
			children = units;
		}
	}

	public Object getChild(int groupPosition, int childPosition) {
		return children[groupPosition][childPosition];
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return children[groupPosition].length;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.child_item, null);
		}

		TextView textView = (TextView) convertView.findViewById(R.id.expandable_child);
		textView.setText(getChild(groupPosition, childPosition).toString());
		return convertView;
	}

	public Object getGroup(int groupPosition) {
		return groups[groupPosition];
	}

	public int getGroupCount() {
		return groups.length;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.group_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.expandable_group);
		textView.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}


}
