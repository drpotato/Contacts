package com.cmor149.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.cmor149.contacts.database.Contact;
import com.cmor149.contacts.database.ContactsDbHelper;

public class EditContactActivity extends Activity {
	
	private static final String LOG = "Contacts";
	
	private ContactsDbHelper dbHelper;
	private Contact contact;

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
			
			Log.d(LOG, "EditContactActivity: Get the intent from the previous activity");
			
			Intent intent = getIntent();
			
			long id = intent.getLongExtra(ContactListActivity.CONTACT_ID_MESSAGE, ContactListActivity.NEW_CONTACT_ID);
			
			dbHelper = ContactsDbHelper.getInstance(this);
			contact = dbHelper.getContact(id);
			
			((EditText) findViewById(R.id.first_name)).setText(contact.getFirstName(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.last_name)).setText(contact.getLastName(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.mobile_phone)).setText(contact.getMobilePhone(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.home_phone)).setText(contact.getHomePhone(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.work_phone)).setText(contact.getWorkPhone(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.email_address)).setText(contact.getEmailAddress(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.home_address)).setText(contact.getHomeAddress(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.date_of_birth)).setText(contact.getDateOfBirth(), TextView.BufferType.EDITABLE);
			
			Log.d(LOG, "EditContactActivity: onCreate finished");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
	@Override
	public void onBackPressed() {
		
		saveContact();
		
		super.onBackPressed();
	}
	
	private void saveContact() {
		
		contact = new Contact(
				((EditText) findViewById(R.id.first_name)).getText().toString(),
				((EditText) findViewById(R.id.last_name)).getText().toString(),
				((EditText) findViewById(R.id.mobile_phone)).getText().toString(),
				((EditText) findViewById(R.id.home_phone)).getText().toString(),
				((EditText) findViewById(R.id.work_phone)).getText().toString(),
				((EditText) findViewById(R.id.email_address)).getText().toString(),
				((EditText) findViewById(R.id.home_address)).getText().toString(),
				((EditText) findViewById(R.id.date_of_birth)).getText().toString(),
				"" // TODO: Photo URI to be added!
				);
		
		if (! contact.isBlank()) {
			dbHelper.updateContact(contact);
		}
		
	}

}
