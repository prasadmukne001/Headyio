package com.worldline.android.headyio.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import com.worldline.android.headyio.BR;
import com.worldline.android.headyio.R;

public class LoginFields extends BaseObservable
{

	private String email;
	private String password;
	public ObservableField<Integer> emailError = new ObservableField<>();
	public ObservableField<Integer> passwordError = new ObservableField<>();

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
		// Notify that the valid property could have changed.
		notifyPropertyChanged(BR.valid);
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
		// Notify that the valid property could have changed.
		notifyPropertyChanged(BR.valid);
	}

	@Bindable
	public boolean isValid()
	{
		boolean valid = isMobileNumberValid(true);
		valid = isPasswordValid(true) && valid;
		return valid;
	}

	public boolean isMobileNumberValid(boolean setMessage)
	{
		// Minimum a@b.c

		if(null!=email)
		{
			emailError.set(null);
			return true;
		}
		else
		{
			emailError.set(R.string.blank_username);
			return false;
		}

	}

	public boolean isPasswordValid(boolean setMessage)
	{
		if(null!=password)
		{
			passwordError.set(null);
			return true;
		}
		else
		{
			passwordError.set(R.string.blank_password);
			return false;
		}

	}
}