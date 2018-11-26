package com.worldline.android.headyio.modules.cart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.commons.view.CartAdapter;
import com.worldline.android.headyio.commons.view.CartAdapter.CostCalculation;
import com.worldline.android.headyio.utility.ProductsSingleton;
import es.dmoral.toasty.Toasty;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CartFragment extends Fragment implements CostCalculation
{
	private Button checkoutButton;
	//Overriden method onCreateView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_cart_view, container, false);
		//Returning the layout file after inflating
		//Change R.layout.tab1 in you classes
		try
		{

			RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
			checkoutButton = (Button) view.findViewById(R.id.checkoutButton);
			CartAdapter mAdapter = new CartAdapter(getActivity(),getFragmentManager(),ProductsSingleton.getCart(),this);
			RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
			recyclerView.setLayoutManager(mLayoutManager);
			recyclerView.setItemAnimator(new DefaultItemAnimator());
			recyclerView.setAdapter(mAdapter);

			float cost=0;
			for(int i=0;i<ProductsSingleton.getCart().size();i++)
			{
				cost=cost+ProductsSingleton.getCart().get(i).getPrice();
			}
			checkoutButton.setText("Checkout and Pay ₹ "+cost);
			checkoutButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ProductsSingleton.removeAllProductsFromCart();
					Toasty.info(getActivity(),"Products Ordered Successfully").show();
					getFragmentManager().popBackStack();
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return view;
	}

	@Override
	public void updateCost(float cost)
	{
		checkoutButton.setText("Checkout and Pay ₹ "+cost);
	}

	public static LinkedHashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
	{
		List<Entry<Integer, Integer>> list = new LinkedList<Entry<Integer, Integer>>(hm.entrySet());

		Collections.sort(list, new Comparator<Entry<Integer, Integer>>()
		{
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2)
			{
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		LinkedHashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
		for (Map.Entry<Integer, Integer> aa : list)
		{
			temp.put(aa.getValue(), aa.getKey());
		}
		return temp;
	}
}