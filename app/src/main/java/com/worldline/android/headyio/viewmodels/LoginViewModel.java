package com.worldline.android.headyio.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import com.worldline.android.headyio.R;


public class LoginViewModel extends ViewModel
{

	private LoginFields login;
	private View.OnFocusChangeListener onFocusEmail;
	private View.OnFocusChangeListener onFocusPassword;
	private MutableLiveData<LoginFields> buttonClick = new MutableLiveData<>();

	public void init()
	{
		login = new LoginFields();
		onFocusEmail = new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View view, boolean focused)
			{
				EditText et = (EditText) view;
				if (/*et.getText().length() > 0 &&*/ !focused)
				{
					if(!login.isMobileNumberValid(true))
					{
						Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake_anim);
						et.startAnimation(shake);
					}
				}
			}
		};

		onFocusPassword = new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View view, boolean focused)
			{
				EditText et = (EditText) view;
				if (/*et.getText().length() > 0 &&*/ !focused)
				{
					if(!login.isPasswordValid(true))
					{
						Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake_anim);
						et.startAnimation(shake);
					}
				}
			}
		};
	}

	public LoginFields getLogin()
	{
		return login;
	}

	public View.OnFocusChangeListener getEmailOnFocusChangeListener()
	{
		return onFocusEmail;
	}

	public View.OnFocusChangeListener getPasswordOnFocusChangeListener()
	{
		return onFocusPassword;
	}

	public void onPasswordClick(EditText passwordEditText,ImageView showPasswordImageView)
	{
		if ((passwordEditText.getTransformationMethod() instanceof  PasswordTransformationMethod))
		{
			passwordEditText.setTransformationMethod(null);
			showPasswordImageView.setBackgroundDrawable(showPasswordImageView.getContext().getResources().getDrawable(R.drawable.ic_visibility_off));
			passwordEditText.setSelection(passwordEditText.length());
		}
		else
		{
			passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
			showPasswordImageView.setBackgroundDrawable(showPasswordImageView.getContext().getResources().getDrawable(R.drawable.ic_visibility_on));
			passwordEditText.setSelection(passwordEditText.length());
		}
	}

	public void onButtonClick(EditText usernameEditText,EditText passwordEditText)
	{

		if (login.getEmail() == null || login.getEmail().equals(""))
		{
			Animation shake = AnimationUtils.loadAnimation(usernameEditText.getContext(), R.anim.shake_anim);
			usernameEditText.startAnimation(shake);
			usernameEditText.setError("User Id can not be blank");
		}
		else if (login.getPassword() == null || login.getPassword().trim().equals(""))
		{
			Animation shake = AnimationUtils.loadAnimation(passwordEditText.getContext(), R.anim.shake_anim);
			passwordEditText.startAnimation(shake);
			passwordEditText.setError("Password can not be blank");
		}
		else
		{
			if (login.isValid())
			{
				buttonClick.setValue(login);
			}
		}

	}

	public MutableLiveData<LoginFields> getButtonClick()
	{
		return buttonClick;
	}

	@BindingAdapter("error")
	public static void setError(EditText editText, Object strOrResId)
	{
		if (strOrResId instanceof Integer)
		{
			editText.setError(editText.getContext().getString((Integer) strOrResId));
		}
		else
		{
			editText.setError((String) strOrResId);
		}

	}

	@BindingAdapter("onFocus")
	public static void bindFocusChange(EditText editText, View.OnFocusChangeListener onFocusChangeListener)
	{
		if (editText.getOnFocusChangeListener() == null)
		{
			editText.setOnFocusChangeListener(onFocusChangeListener);
		}
	}


}