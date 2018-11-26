package com.worldline.android.headyio.modules.base;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.application.MyApplication;
import com.worldline.android.headyio.commons.view.BottomNavigationViewHelper;
import com.worldline.android.headyio.commons.view.CustomGridView;
import com.worldline.android.headyio.commons.view.ProgressBarHandler;
import com.worldline.android.headyio.modules.cart.CartFragment;
import com.worldline.android.headyio.modules.search.SearchProductsFragment;
import com.worldline.android.headyio.utility.HeadyIOConstants;
import com.worldline.android.headyio.utility.ProductsSingleton;
import es.dmoral.toasty.Toasty;


public class MasterDashBoardFragment extends Fragment
{


	private static final String TAG = "TAG";

	@InjectView(R.id.usernameTextView)
	public TextView usernameTextView;
	@InjectView(R.id.navigationView)
	public BottomNavigationView bottomNavigationView;
	@InjectView(R.id.gridView)
	public GridView gridView;

	String[] gridViewString = {"Search Products\n", "My Profile\n", "Orders History\n", "Saved Items", "My Cart", "Checkout"};
	int[] gridViewImageId = {R.drawable.ic_search, R.drawable.ic_user_profile, R.drawable.ic_history, R.drawable.ic_save, R.drawable.ic_cart, R.drawable.ic_card_payment};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.fragment_master_dash_board, container, false);
		ButterKnife.inject(this,view);
		initialiseUI(view);

		setGridViewAdapterAndListener();

		getDataFromServer();

		return view;
	}

	private void getDataFromServer()
	{
		(new AsyncTask<Void, Void, Void>()
		{
			ProgressBarHandler progressBarHandler;

			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();
				progressBarHandler = new ProgressBarHandler(getActivity());
				progressBarHandler.show();
			}

			@Override
			protected Void doInBackground(Void... voids)
			{
				MyApplication.getDataFromServer();
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid)
			{
				super.onPostExecute(aVoid);
				progressBarHandler.hide();
			}
		}).execute();
	}


	private void initialiseUI(View view)
	{
		usernameTextView.setText("Welcome User, " + getActivity().getIntent().getStringExtra(HeadyIOConstants.MOBILE_NUMBER));
		BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
	}



	private void setGridViewAdapterAndListener()
	{
		CustomGridView gridViewAdapter = new CustomGridView(getActivity(), gridViewString, gridViewImageId);
		gridView.setAdapter(gridViewAdapter);

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Fragment fragment = null;
				String TAG = "";
				if (position == 0)
				{
					TAG = "SearchProductsFragment";
					fragment = getFragmentManager().findFragmentByTag(TAG);
					if (null == fragment)
					{
						fragment = new SearchProductsFragment();
					}
				}
				else if (position == 4)
				{
					if (ProductsSingleton.getCart().size() > 0)
					{
						TAG = "CartFragment";
						fragment = getFragmentManager().findFragmentByTag(TAG);
						if (null == fragment)
						{
							fragment = new CartFragment();
						}
					}
					else
					{
						Toasty.warning(getActivity(), "Cart is Empty").show();
					}

				}
				else if (position == 5)
				{
					if (ProductsSingleton.getCart().size() > 0)
					{
						Toasty.info(getActivity(), "Products from Cart are ordered Successfully").show();
						ProductsSingleton.removeAllProductsFromCart();
					}
					else
					{
						Toasty.warning(getActivity(), "Cart is Empty").show();
					}

				}

				if (fragment != null)
				{

					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
					if (!fragment.isAdded())
					{
						fragmentTransaction.replace(R.id.mainFrameLayout, fragment, TAG).addToBackStack(null).commit();
					}

				}
			}


		});
	}

}