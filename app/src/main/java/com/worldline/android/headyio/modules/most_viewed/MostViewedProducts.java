package com.worldline.android.headyio.modules.most_viewed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.commons.view.ProductAdapter;
import com.worldline.android.headyio.model.Product;
import com.worldline.android.headyio.utility.HeadyIOUtility;
import com.worldline.android.headyio.utility.ProductsSingleton;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MostViewedProducts extends Fragment
{

	private RecyclerView recyclerView;
	private LinkedList<Product> sortedProductsList;
	private LinkedList<Integer> viewsList;

	//Overriden method onCreateView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.most_viewed, container, false);
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

		if(null==ProductsSingleton.getInstance().getMostViewedProductList() && null==ProductsSingleton.getInstance().getMostViewedViewsList())
		{
			doDataProcessingAndShowProducts();
		}
		else
		{
			sortedProductsList= (LinkedList<Product>) ProductsSingleton.getInstance().getMostViewedProductList();
			viewsList= (LinkedList<Integer>) ProductsSingleton.getInstance().getMostViewedViewsList();
			showDataInListView();
		}


		return view;
	}

	private void doDataProcessingAndShowProducts()
	{
		new AsyncTask<Void, Void, Void>()
		{

			@Override
			protected Void doInBackground(Void... voids)
			{
				try
				{
					JSONObject jsonObject = new JSONObject(getArguments().getString("data"));
					JSONArray jsonArray = jsonObject.getJSONArray("rankings");
					JSONArray jsonArray1 = new JSONArray();
					for (int i = 0; i < jsonArray.length(); i++)
					{
						if (jsonArray.getJSONObject(i).getString("ranking").equals("Most Viewed Products"))
						{
							jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("products");
							break;
						}
					}
					HashMap<String, Integer> obj = new HashMap<>();
					HashMap<Integer, Integer> obj2 = new HashMap<>();
					for (int i = 0; i < jsonArray1.length(); i++)
					{
						obj = new Gson().fromJson(jsonArray1.getJSONObject(i).toString(), new TypeToken<HashMap<String, Integer>>()
						{
						}.getType());
						obj2.put(obj.get("id"), obj.get("view_count"));
					}
					LinkedHashMap<Integer, Integer> sortedIntegerHashMap = HeadyIOUtility.sortByValue(obj2);
					sortedProductsList = new LinkedList<>();
					viewsList = new LinkedList<>();
					Iterator it = sortedIntegerHashMap.entrySet().iterator();
					while (it.hasNext())
					{
						Map.Entry pair = (Map.Entry) it.next();
						viewsList.add((Integer) pair.getKey());
						System.out.println(pair.getKey() + " = " + pair.getValue());
						for (int j = 0; j < ProductsSingleton.getInstance().getProductArrayList().size(); j++)
						{
							if (ProductsSingleton.getInstance().getProductArrayList().get(j).getId() == (Integer) pair.getValue())
							{
								sortedProductsList.add(ProductsSingleton.getInstance().getProductArrayList().get(j));
								break;
							}
						}
					}

					ProductsSingleton.getInstance().setMostViewedProductList(sortedProductsList);
					ProductsSingleton.getInstance().setMostViewedViewsList(viewsList);



				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid)
			{
				super.onPostExecute(aVoid);

				showDataInListView();
			}
		}.execute();

	}

	public void showDataInListView()
	{
		try
		{
			ProductAdapter mAdapter = new ProductAdapter(getFragmentManager(), sortedProductsList, viewsList, "Viewed");
			RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
			recyclerView.setLayoutManager(mLayoutManager);
			recyclerView.setItemAnimator(new DefaultItemAnimator());
			recyclerView.setAdapter(mAdapter);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}