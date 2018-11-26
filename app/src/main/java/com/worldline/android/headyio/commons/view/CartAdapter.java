package com.worldline.android.headyio.commons.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.model.CartItem;
import es.dmoral.toasty.Toasty;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
{

	private List<CartItem> cartItemList;
	private Context context;
	private FragmentManager fragmentManager;
	public float cost=0;
	private CostCalculation costCalculation;

	public class MyViewHolder extends RecyclerView.ViewHolder
	{

		public TextView productNameTextView, viewedCountTextView;
		public ImageView deleteCartItemImageView;

		public MyViewHolder(View view)
		{
			super(view);
			productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
			viewedCountTextView = (TextView) view.findViewById(R.id.viewedCountTextView);
			deleteCartItemImageView = (ImageView) view.findViewById(R.id.deleteCartItemImageView);
		}
	}


	public CartAdapter(Context context, FragmentManager fragmentManager, List<CartItem> cartItemList,CostCalculation costCalculation)
	{
		this.cartItemList = cartItemList;
		this.context = context;
		this.fragmentManager = fragmentManager;
		this.costCalculation=costCalculation;
	}

	@Override
	public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart_view, parent, false);

		return new CartAdapter.MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(CartAdapter.MyViewHolder holder, final int position)
	{
		cost=cost+cartItemList.get(position).getPrice();
		final CartItem cartItem = cartItemList.get(position);
		holder.productNameTextView.setText(cartItem.getName());
		holder.viewedCountTextView.setText(cartItem.getColor() + "\n" + cartItem.getPrice());
		holder.deleteCartItemImageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

				new AlertDialog.Builder(context).setIcon(null).setTitle("Confirm").setCancelable(false).setMessage("Do you want to Delete this item from Cart?").setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						cost=cost-cartItemList.get(position).getPrice();
						costCalculation.updateCost(cost);
						if (cartItemList.size() > 1)
						{
							cartItemList.remove(position);
							notifyDataSetChanged();
						}
						else
						{
							cartItemList.remove(position);
							Toasty.warning(context,"No items present in Cart").show();
							fragmentManager.popBackStack();
						}
					}
				}).setNegativeButton("No", null).show();
			}
		});

	}

	@Override
	public int getItemCount()
	{
		return cartItemList.size();
	}

	public interface CostCalculation
	{
		public void updateCost(float cost);
	}
}
