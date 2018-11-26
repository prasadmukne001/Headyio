package com.worldline.android.headyio.modules.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.modules.cart.CartFragment;
import com.worldline.android.headyio.modules.login.LoginActivity;
import com.worldline.android.headyio.utility.HeadyIOConstants;
import com.worldline.android.headyio.utility.ProductsSingleton;
import es.dmoral.toasty.Toasty;


public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

	public static String mobileNumber;
	@InjectView(R.id.toolbar)
	public Toolbar toolbar;
	@InjectView(R.id.drawer_layout)
	public DrawerLayout drawer;
	@InjectView(R.id.nav_view)
	public NavigationView navigationView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_drawer);
		ButterKnife.inject(this);

		try
		{
			initialiseUI(toolbar);

			setMenuDrawer(toolbar);

			setDefaultFragment();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void initialiseUI(Toolbar toolbar)
	{
		setSupportActionBar(toolbar);
		mobileNumber = getIntent().getStringExtra(HeadyIOConstants.MOBILE_NUMBER) != null ? getIntent().getStringExtra(HeadyIOConstants.MOBILE_NUMBER) : mobileNumber;
	}


	private void setDefaultFragment()
	{
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
		fragmentTransaction.replace(R.id.mainFrameLayout, new MasterDashBoardFragment(), "MasterDashBoardFragment");
		fragmentTransaction.commit();
	}

	private void setMenuDrawer(Toolbar toolbar)
	{

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		navigationView.setNavigationItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main2, menu);
		Drawable drawable = menu.findItem(R.id.action_cart).getIcon();
		drawable = DrawableCompat.wrap(drawable);
		DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
		menu.findItem(R.id.action_cart).setIcon(drawable);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_cart)
		{
			if (ProductsSingleton.getCart().size() > 0)
			{
				String TAG = "CartFragment";
				Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
				if (null == fragment)
				{
					fragment = new CartFragment();
				}
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
				if (!fragment.isAdded())
				{
					fragmentTransaction.add(R.id.mainFrameLayout, fragment, TAG).addToBackStack(null).commit();
				}
			}
			else
			{
				Toasty.warning(this, "Cart is Empty").show();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed()
	{
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			if (getSupportFragmentManager().getBackStackEntryCount() == 0)
			{
				logoutDialog();
			}
			else
			{
				getSupportFragmentManager().popBackStack();
			}
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		drawer.closeDrawer(GravityCompat.START);
		if (item.getItemId() == R.id.logoutMenu)
		{
			logoutDialog();
		}
		return true;
	}

	private void logoutDialog()
	{
		new AlertDialog.Builder(this).setIcon(null).setTitle("Confirm").setCancelable(false).setMessage("Do you want to logout of this app ?").setPositiveButton("Yes", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				finish();
				Intent intent = new Intent(BaseDrawerActivity.this, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				BaseDrawerActivity.this.startActivity(intent);

			}

		}).setNegativeButton("No", null).show();


	}

}
