package com.worldline.android.headyio.modules.view_products;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.commons.view.ProductVariantGridView;
import com.worldline.android.headyio.model.CartItem;
import com.worldline.android.headyio.model.Product;
import com.worldline.android.headyio.utility.ProductsSingleton;
import es.dmoral.toasty.Toasty;


public class ProductViewFragment extends Fragment
{

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private int selectedProductCost;
	private int selectedProductPosition;

	// TODO: Rename and change types of parameters
	private Product product;
	private String mParam2;

	public ProductViewFragment()
	{
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param product Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ProductViewFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ProductViewFragment newInstance(Product product, String param2)
	{
		ProductViewFragment fragment = new ProductViewFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_PARAM1, product);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			product = (Product) getArguments().get(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view= inflater.inflate(R.layout.fragment_product_view, container, false);
		GridView gridView = (GridView) view.findViewById(R.id.gridView);
		ProductVariantGridView gridViewAdapter = new ProductVariantGridView(getActivity(), product.getVariants());
		gridView.setAdapter(gridViewAdapter);

		gridView.performItemClick(gridViewAdapter.getView(0, null, null),0,gridViewAdapter.getItemId(0));
		//gridView.setSelection(0);
		//gridView.setItemChecked(0,true);


		TextView productNameTextView = (TextView) view.findViewById(R.id.productNameTextView);
		TextView dateAddedTextView = (TextView) view.findViewById(R.id.dateAddedTextView);
		final TextView productCostTextView = (TextView) view.findViewById(R.id.productCostTextView);
		final TextView taxTextView = (TextView) view.findViewById(R.id.taxTextView);
		final TextView totalCostTextView = (TextView) view.findViewById(R.id.totalCostTextView);
		Button addToCartButton = (Button) view.findViewById(R.id.addToCartButton);

		productNameTextView.setText(product.getName());
		dateAddedTextView.setText("Date Added:-"+product.getDate_added().substring(0,10));
		productCostTextView.setText("Product Cost = Select Product Variant First");
		taxTextView.setText("Tax = "+product.getTax().getValue()+"("+product.getTax().getName()+")");
		float cost= selectedProductCost+((selectedProductCost*product.getTax().getValue())/100);
		totalCostTextView.setText("Total Cost Of Product = "+cost);

		addToCartButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if(selectedProductCost!=0)
				{
					Toasty.info(getActivity(),"Product Added to Cart Successfully").show();
					getFragmentManager().popBackStack();
					CartItem cartItem=new CartItem();
					cartItem.setId(product.getId());
					cartItem.setName(product.getName());
					cartItem.setColor(product.getVariants()[selectedProductPosition].getColor());
					float cost= selectedProductCost+((selectedProductCost*product.getTax().getValue())/100);
					cartItem.setPrice(cost);
					ProductsSingleton.addProductToCart(cartItem);
				}
				else
				{
					Toasty.error(getActivity(),"Please Select the Variant First").show();
				}
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
			{

				adapterView.setSelected(true);
				selectedProductPosition=i;
				selectedProductCost=product.getVariants()[i].getPrice();
				productCostTextView.setText("Product Cost:- "+selectedProductCost+" color:- "+product.getVariants()[i].getColor()+" size:- "+product.getVariants()[i].getSize());
				float cost= selectedProductCost+((selectedProductCost*product.getTax().getValue())/100);
				totalCostTextView.setText("Total Cost = "+cost);
			}
		});


		return view;
	}
}
