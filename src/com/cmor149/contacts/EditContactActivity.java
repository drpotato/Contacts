package com.cmor149.contacts;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class EditContactActivity extends Activity {
	
	private static final String LOG = "Contacts";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
			
			Log.d(LOG, "EditContactActivity: onCreate started");
			
			super.onCreate(savedInstanceState);
			
			
			Log.d(LOG, "EditContactActivity: Change Action Bar title");
			setTitle("Done");
			
			Log.d(LOG, "EditContactActivity: Change Action Bar icon");
			getActionBar().setIcon(R.drawable.navigation_accept);
			
			Log.d(LOG, "EditContactActivity: Enable the action bar button");
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
			
			Log.d(LOG, "EditContactActivity: onCreate finished");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

}
