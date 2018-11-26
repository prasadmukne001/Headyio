package com.worldline.android.headyio.model;

import java.io.Serializable;

public class Product implements Serializable
{
	private int id;
	private String name;
	private String date_added;
	private Variant[] variants;
	private Tax tax;

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

	public String getDate_added()
	{
		return date_added;
	}

	public void setDate_added(String date_added)
	{
		this.date_added = date_added;
	}

	public Variant[] getVariants()
	{
		return variants;
	}

	public void setVariants(Variant[] variants)
	{
		this.variants = variants;
	}

	public Tax getTax()
	{
		return tax;
	}

	public void setTax(Tax tax)
	{
		this.tax = tax;
	}


}
