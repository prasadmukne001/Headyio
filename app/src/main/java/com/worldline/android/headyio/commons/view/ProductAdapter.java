package com.worldline.android.headyio.commons.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.model.Product;
import com.worldline.android.headyio.modules.view_products.ProductViewFragment;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

	private List<Product> productList;
	private List<Integer>viewsList;
	private FragmentManager context;
	private String type;
	public class MyViewHolder extends RecyclerView.ViewHolder {
		public TextView productNameTextView, viewedCountTextView;

		public MyViewHolder(View view) {
			super(view);
			productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
			viewedCountTextView = (TextView) view.findViewById(R.id.viewedCountTextView);
		}
	}


	public ProductAdapter(FragmentManager context,List<Product> productList,List<Integer>viewsList,String type) {
		this.productList = productList;
		this.viewsList=viewsList;
		this.context=context;
		this.type=type;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);

		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		try
		{
			Product product = productList.get(position);
			holder.productNameTextView.setText(product.getName());
			holder.viewedCountTextView.setText(type+" "+viewsList.get(position)+" times");
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Fragment fragment = null;
					String TAG ="ProductViewFragment";
					fragment = context.findFragmentByTag(TAG);
					if (null == fragment)
					{
						fragment = ProductViewFragment.newInstance(productList.get(position),"");
					}
					if (fragment != null)
					{

						FragmentTransaction fragmentTransaction = context.beginTransaction();
						fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
						if (!fragment.isAdded())
						{
							fragmentTransaction.add(R.id.mainFrameLayout, fragment, TAG).addToBackStack(null).commit();
						}

					}
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public int getItemCount() {
		return productList.size();
	}
}