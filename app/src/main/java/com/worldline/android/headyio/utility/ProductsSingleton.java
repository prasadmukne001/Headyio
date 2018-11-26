package com.worldline.android.headyio.utility;

import com.worldline.android.headyio.model.CartItem;
import com.worldline.android.headyio.model.Category;
import com.worldline.android.headyio.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProductsSingleton
{

	private ArrayList<Product> productArrayList;
	private static ArrayList<CartItem> cartProductsArrayList = new ArrayList<>();
	private static ProductsSingleton productsSingleton;
	private HashMap<String, List<String>> expandableListDetail;
	private LinkedList<Product> mostViewedProductList;
	private LinkedList<Product> mostSharedProductList;
	private LinkedList<Product> mostOrderedProductList;
	private LinkedList<Integer> mostViewedViewsList;
	private LinkedList<Integer> mostOrderedViewsList;
	private LinkedList<Integer> mostSharedViewsList;

	private HashMap<String, Category> categoriesList;
	private ProductsSingleton()
	{

	}

	public static ProductsSingleton getInstance()
	{
		if (null == productsSingleton)
		{
			productsSingleton = new ProductsSingleton();
		}
		return productsSingleton;
	}

	public ArrayList<Product> getProductArrayList()
	{
		return productArrayList;
	}

	public void setProductArrayList(ArrayList<Product> productArrayList)
	{
		this.productArrayList = productArrayList;
	}

	public static void addProductToCart(CartItem cartItem)
	{
		cartProductsArrayList.add(cartItem);
	}

	public static void removeAllProductsFromCart()
	{
		cartProductsArrayList = new ArrayList<>();
	}

	public static void removeProductToCart(CartItem cartItem)
	{
		cartProductsArrayList.remove(cartItem);
	}

	public static ArrayList<CartItem> getCart()
	{
		return cartProductsArrayList;
	}

	public void setProductCategoriesData(HashMap<String, List<String>> expandableListDetail)
	{
		this.expandableListDetail = expandableListDetail;
	}

	public HashMap<String, List<String>> getProductCategoriesData()
	{
		return expandableListDetail;
	}

	public LinkedList<Product> getMostViewedProductList()
	{
		return mostViewedProductList;
	}

	public void setMostViewedProductList(LinkedList<Product> mostViewedProductList)
	{
		this.mostViewedProductList = mostViewedProductList;
	}

	public LinkedList<Product> getMostSharedProductList()
	{
		return mostSharedProductList;
	}

	public void setMostSharedProductList(LinkedList<Product> mostSharedProductList)
	{
		this.mostSharedProductList = mostSharedProductList;
	}

	public LinkedList<Product> getMostOrderedProductList()
	{
		return mostOrderedProductList;
	}

	public void setMostOrderedProductList(LinkedList<Product> mostOrderedProductList)
	{
		this.mostOrderedProductList = mostOrderedProductList;
	}

	public LinkedList<Integer> getMostViewedViewsList()
	{
		return mostViewedViewsList;
	}

	public void setMostViewedViewsList(LinkedList<Integer> mostViewedViewsList)
	{
		this.mostViewedViewsList = mostViewedViewsList;
	}

	public LinkedList<Integer> getMostOrderedViewsList()
	{
		return mostOrderedViewsList;
	}

	public void setMostOrderedViewsList(LinkedList<Integer> mostOrderedViewsList)
	{
		this.mostOrderedViewsList = mostOrderedViewsList;
	}

	public LinkedList<Integer> getMostSharedViewsList()
	{
		return mostSharedViewsList;
	}

	public void setMostSharedViewsList(LinkedList<Integer> mostSharedViewsList)
	{
		this.mostSharedViewsList = mostSharedViewsList;
	}

	public HashMap<String, Category> getCategoriesList()
	{
		return categoriesList;
	}

	public void setCategoriesList(HashMap<String, Category> categoriesList)
	{
		this.categoriesList = categoriesList;
	}

}
