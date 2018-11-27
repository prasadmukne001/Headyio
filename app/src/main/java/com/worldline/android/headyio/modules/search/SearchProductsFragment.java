package com.worldline.android.headyio.modules.search;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.commons.database.SQLiteDatabaseManager;
import com.worldline.android.headyio.commons.view.Pager;


public class SearchProductsFragment extends Fragment implements TabLayout.OnTabSelectedListener{

	//This is our tablayout
	@InjectView(R.id.tabLayout)
	public TabLayout tabLayout;

	//This is our viewPager
	@InjectView(R.id.pager)
	public ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view=inflater.inflate(R.layout.fragment_search_products, container, false);
		ButterKnife.inject(this,view);

		initialiseUI();

		return view;
	}

	public void initialiseUI()
	{
		//Adding the tabs using addTab() method
		tabLayout.addTab(tabLayout.newTab().setText("View Categories"));
		tabLayout.addTab(tabLayout.newTab().setText("Most Viewed"));
		tabLayout.addTab(tabLayout.newTab().setText("Most Ordered"));
		tabLayout.addTab(tabLayout.newTab().setText("Most Shared"));
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

		//Creating our pager adapter
		Pager adapter = new Pager(((AppCompatActivity)getActivity()).getSupportFragmentManager(), tabLayout.getTabCount(),getDataFromDB());

		//Adding adapter to pager
		viewPager.setAdapter(adapter);

		//Adding onTabSelectedListener to swipe views
		tabLayout.setOnTabSelectedListener(this);
		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{

			}

			@Override
			public void onPageSelected(int position)
			{
				tabLayout.getTabAt(position).select();
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});

	}


	private String getDataFromDB()
	{
		String str=null;
		Cursor cursor= SQLiteDatabaseManager.getInstance(getActivity()).search("Select * from "+SQLiteDatabaseManager.RESPONSE_TABLE);
		if(cursor.getCount()>0)
		{
			cursor.moveToFirst();
			str=cursor.getString(cursor.getColumnIndex(SQLiteDatabaseManager.RESPONSE));
			cursor.close();
		}

		return str;
	}

	@Override
	public void onTabSelected(TabLayout.Tab tab) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(TabLayout.Tab tab) {

	}

	@Override
	public void onTabReselected(TabLayout.Tab tab) {

	}
}
