package com.worldline.android.headyio.modules.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.commons.services.ApplicationService;
import com.worldline.android.headyio.commons.services.LoginNetworkService;
import com.worldline.android.headyio.databinding.ActivityLoginBinding;
import com.worldline.android.headyio.modules.base.BaseDrawerActivity;
import com.worldline.android.headyio.utility.HeadyIOConstants;
import com.worldline.android.headyio.utility.HeadyIOUtility;
import com.worldline.android.headyio.viewmodels.LoginFields;
import com.worldline.android.headyio.viewmodels.LoginViewModel;
import es.dmoral.toasty.Toasty;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
	private LoginViewModel viewModel;
	public ActivityLoginBinding activityBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		try
		{
			setupBindings(savedInstanceState);

			checkIfRememberMeAvailable();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void setupBindings(Bundle savedInstanceState)
	{
		activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
		viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
		if (savedInstanceState == null)
		{
			viewModel.init();
		}
		activityBinding.setModel(viewModel);
		setupButtonClick();
	}

	private void setupButtonClick()
	{
		viewModel.getButtonClick().observe(this, new Observer<LoginFields>()
		{
			@Override
			public void onChanged(LoginFields loginModel)
			{
				validateInputFields(loginModel);
			}
		});
	}

	private void checkIfRememberMeAvailable()
	{
		SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(HeadyIOConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		if (sharedPreferences.getString(HeadyIOConstants.IS_REMEMBER_ME_CHECKED, "").equals("true"))
		{
			activityBinding.rememberMeCheckBox.setChecked(true);
			activityBinding.userIdEditText.setText(sharedPreferences.getString(HeadyIOConstants.LOGGED_IN_USERNAME, ""));
			activityBinding.passwordEditText.setText(sharedPreferences.getString(HeadyIOConstants.PASSWORD, ""));
		}
	}


	private void validateInputFields(LoginFields loginModel)
	{
		try
		{
			validateCredentialsWithServer(loginModel.getEmail(), loginModel.getPassword());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void validateCredentialsWithServer(String userId, String password)
	{
		if (HeadyIOUtility.isNetworkAvailable(LoginActivity.this))
		{
			new LoginNetworkService(this, new ApplicationService()
			{
				@Override
				public void onStart()
				{
					activityBinding.userIdEditText.setEnabled(false);
					activityBinding.passwordEditText.setEnabled(false);
					activityBinding.rememberMeCheckBox.setEnabled(false);
				}

				@Override
				public void onStop(String responseString)
				{
					activityBinding.userIdEditText.setEnabled(true);
					activityBinding.passwordEditText.setEnabled(true);
					activityBinding.rememberMeCheckBox.setEnabled(true);
					if (!activityBinding.userIdEditText.getText().toString().trim().equals("") && !activityBinding.passwordEditText.getText().toString().trim().equals(""))
					{
						SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(HeadyIOConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
						sharedPreferences.edit().putString(HeadyIOConstants.LOGGED_IN_USERNAME, activityBinding.userIdEditText.getText().toString()).commit();
						if (activityBinding.rememberMeCheckBox.isChecked())
						{
							sharedPreferences.edit().putString(HeadyIOConstants.PASSWORD, activityBinding.passwordEditText.getText().toString()).commit();
							sharedPreferences.edit().putString(HeadyIOConstants.IS_REMEMBER_ME_CHECKED, "true").commit();
						}
						Intent intent = new Intent(LoginActivity.this, BaseDrawerActivity.class);
						intent.putExtra(HeadyIOConstants.MOBILE_NUMBER, activityBinding.userIdEditText.getText().toString());
						startActivity(intent);
					}
					else
					{
						Toasty.error(LoginActivity.this, getResources().getString(R.string.invalid_mobile_number), Toast.LENGTH_SHORT).show();
					}

				}
			}).execute(userId, password);
		}
		else
		{
			Toasty.info(LoginActivity.this, "No Internet Available.", Toast.LENGTH_SHORT).show();
		}
	}

}

