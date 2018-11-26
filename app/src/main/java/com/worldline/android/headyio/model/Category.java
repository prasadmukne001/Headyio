package com.worldline.android.headyio.model;

import java.io.Serializable;

public class Category implements Serializable
{
	private int id;
	private String name;
	private Product[] products;
	private int [] child_categories;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Product[] getProducts()
	{
		return products;
	}

	public void setProducts(Product[] products)
	{
		this.products = products;
	}

	public int[] getChild_categories()
	{
		return child_categories;
	}

	public void setChild_categories(int[] child_categories)
	{
		this.child_categories = child_categories;
	}


}
