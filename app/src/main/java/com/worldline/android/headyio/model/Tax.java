package com.worldline.android.headyio.model;

import java.io.Serializable;

public class Tax implements Serializable
{

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}

	private String name;
	private float value;
}
