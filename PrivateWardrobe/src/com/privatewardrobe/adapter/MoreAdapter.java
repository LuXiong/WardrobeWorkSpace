package com.privatewardrobe.adapter;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.privatewardrobe.R;

public class MoreAdapter extends SimpleAdapter {
	private LayoutInflater inflater = null;
	private List<Map<String,Object>> styles = null;
	public List<Map<String, Object>> getStyles() {
		return styles;
	}

	public void setStyles(List<Map<String, Object>> styles) {
		this.styles = styles;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View result = super.getView(position, convertView, parent);
		if(result != null){
			inflater.inflate(R.layout.activity_more_item, null);
		}
		return result;
	}

	public SetAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		inflater = LayoutInflater.from(context);
	}
}

