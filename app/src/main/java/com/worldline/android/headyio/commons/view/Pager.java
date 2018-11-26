package com.worldline.android.headyio.commons.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.worldline.android.headyio.modules.most_ordered.MostOrderedFragment;
import com.worldline.android.headyio.modules.most_shared.MostSharedFragment;
import com.worldline.android.headyio.modules.most_viewed.MostViewedProducts;
import com.worldline.android.headyio.modules.category_wise_view.ProductCategoriesFragment;

public class Pager extends FragmentStatePagerAdapter
{

	//integer to count number of tabs
	private int tabCount;
	private String data;

	//Constructor to the class
	public Pager(FragmentManager fm, int tabCount,String data) {
		super(fm);
		//Initializing tab count
		this.tabCount= tabCount;
		this.data=data;
	}

	//Overriding method getItem
	@Override
	public Fragment getItem(int position) {
		//Returning the current tabs
		Bundle bundle=new Bundle();
		bundle.putString("data",data);
		switch (position) {
			case 0:
				ProductCategoriesFragment tab1 = new ProductCategoriesFragment();
				tab1.setArguments(bundle);
				return tab1;
			case 1:
				MostViewedProducts tab2 = new MostViewedProducts();
				tab2.setArguments(bundle);
				return tab2;
			case 2:
				MostOrderedFragment tab3 = new MostOrderedFragment();
				tab3.setArguments(bundle);
				return tab3;
			case 3:
				MostSharedFragment tab4 = new MostSharedFragment();
				tab4.setArguments(bundle);
				return tab4;
			default:
				return null;
		}
	}

	//Overriden method getCount to get the number of tabs
	@Override
	public int getCount() {
		return tabCount;
	}
}