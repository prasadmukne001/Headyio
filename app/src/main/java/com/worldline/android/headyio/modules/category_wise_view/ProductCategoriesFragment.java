package com.worldline.android.headyio.modules.category_wise_view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.google.gson.Gson;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.model.Category;
import com.worldline.android.headyio.model.Product;
import com.worldline.android.headyio.modules.view_products.ProductViewFragment;
import com.worldline.android.headyio.utility.ProductsSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductCategoriesFragment extends Fragment
{

	private ExpandableListView expandableListView;
	private ExpandableListAdapter expandableListAdapter;
	private List<String> expandableListTitle;
	private HashMap<String, Category> categoriesList;
	private HashMap<String, List<String>> expandableListDetail;

	//Overriden method onCreateView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.product_category, container, false);
		expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

		if(null==ProductsSingleton.getInstance().getProductCategoriesData()  && null==ProductsSingleton.getInstance().getCategoriesList())
		{
			doDataProcessing();
		}
		else
		{
			expandableListDetail=ProductsSingleton.getInstance().getProductCategoriesData();
			categoriesList=ProductsSingleton.getInstance().getCategoriesList();
			showDataInListView();
		}


		return view;
	}

	private void doDataProcessing()
	{
		new AsyncTask<Void, Void, Void>()
		{

			@Override
			protected Void doInBackground(Void... voids)
			{
				try
				{
					JSONObject jsonObject = new JSONObject(getArguments().getString("data"));
					JSONArray jsonArray = jsonObject.getJSONArray("categories");
					expandableListDetail = new HashMap<>();
					ArrayList<Product> productArrayList = new ArrayList<>();
					categoriesList = new HashMap<>();
					for (int i = 0; i < jsonArray.length(); i++)
					{
						if (jsonArray.get(i) instanceof JSONObject)
						{
							JSONObject jsnObj = (JSONObject) jsonArray.get(i);
							Category obj = new Gson().fromJson(jsnObj.toString(), Category.class);
							categoriesList.put(obj.getName(), obj);
							ArrayList<String> stringArrayList = new ArrayList<>();
							for (int j = 0; j < obj.getProducts().length; j++)
							{
								stringArrayList.add(obj.getProducts()[j].getName());
								productArrayList.add(obj.getProducts()[j]);
							}
							expandableListDetail.put(obj.getName(), stringArrayList);

						}
					}
					ProductsSingleton.getInstance().setProductCategoriesData(expandableListDetail);
					ProductsSingleton.getInstance().setProductArrayList(productArrayList);
					ProductsSingleton.getInstance().setCategoriesList(categoriesList);
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
			expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
			expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
			expandableListView.setAdapter(expandableListAdapter);
			expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
			{
				int previousItem = 0;

				@Override
				public void onGroupExpand(int groupPosition)
				{
					//Toasty.info(getActivity(),expandableListTitle.get(groupPosition) + " List Expanded.").show();
					if (groupPosition != previousItem)
					{
						expandableListView.collapseGroup(previousItem);
					}
					previousItem = groupPosition;
				}
			});

			expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener()
			{

				@Override
				public void onGroupCollapse(int groupPosition)
				{
					//Toasty.info(getActivity(), expandableListTitle.get(groupPosition) + " List Collapsed.").show();

				}
			});

			expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
			{
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
				{

					String TAG = "ProductViewFragment";
					Fragment fragment = getFragmentManager().findFragmentByTag(TAG);
					if (null == fragment)
					{
						fragment = ProductViewFragment.newInstance(categoriesList.get(expandableListTitle.get(groupPosition)).getProducts()[childPosition], "");
					}
					if (fragment != null)
					{
						FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
						fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
						if (!fragment.isAdded())
						{
							fragmentTransaction.add(R.id.mainFrameLayout, fragment, TAG).addToBackStack(null).commit();
						}
					}

					return false;
				}
			});

			DisplayMetrics metrics = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int width = metrics.widthPixels;
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
			{
				expandableListView.setIndicatorBounds(width - 150, 0);
			}
			else
			{
				expandableListView.setIndicatorBoundsRelative(width - 150, 0);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
