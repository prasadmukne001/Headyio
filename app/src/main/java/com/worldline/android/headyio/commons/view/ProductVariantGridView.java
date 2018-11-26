package com.worldline.android.headyio.commons.view;

/**
 * Created by kunal.jadhav on 11-07-2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.model.Variant;


public class ProductVariantGridView extends BaseAdapter
{

	private final Variant[] gridViewString;

	private Context mContext;

	public ProductVariantGridView(Context context, Variant[] gridViewString)
	{
		mContext = context;

		this.gridViewString = gridViewString;
	}

	@Override
	public int getCount()
	{
		return gridViewString.length;
	}

	@Override
	public Object getItem(int i)
	{
		return gridViewString[i];
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup parent)
	{
		View gridViewAndroid;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null)
		{
			gridViewAndroid = inflater.inflate(R.layout.adapter_product_variant, null);
			TextView colorTextView = (TextView) gridViewAndroid.findViewById(R.id.colorTextView);
			TextView sizeTextView = (TextView) gridViewAndroid.findViewById(R.id.sizeTextView);
			TextView priceTextView= (TextView) gridViewAndroid.findViewById(R.id.priceTextView);
			colorTextView.setText(gridViewString[i].getColor());
			sizeTextView.setText(""+gridViewString[i].getSize());
			priceTextView.setText(""+gridViewString[i].getPrice());
		}
		else
		{
			gridViewAndroid = (View) convertView;
		}

		return gridViewAndroid;
	}
}