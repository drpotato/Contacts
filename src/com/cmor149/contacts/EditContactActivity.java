package com.cmor149.contacts;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cmor149.contacts.database.Contact;
import com.cmor149.contacts.database.ContactsDbHelper;


public class EditContactActivity extends Activity {
	
	private static final String TAG = "Contacts";
	
	private static final int DATE_PICKER_DIALOG_ID = 1;
	
	private ContactsDbHelper dbHelper;
	private long id = -1;
	private Contact contact;
	private TextView dateOfBirth;
	private int year = 0;
	private int month = 0;
	private int day = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
			
			Log.d(TAG, "EditContactActivity: onCreate started");
			
			super.onCreate(savedInstanceState);
			
			
			Log.d(TAG, "EditContactActivity: Change Action Bar title");
			
			setTitle("Done");
			
			Log.d(TAG, "EditContactActivity: Change Action Bar icon");
			
			getActionBar().setIcon(R.drawable.navigation_accept_dark);
			
			Log.d(TAG, "EditContactActivity: Enable the action bar button");
			
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
			
			Log.d(TAG, "EditContactActivity: Get the intent from the previous activity");
			
			Intent intent = getIntent();
			
			id = intent.getLongExtra(ContactListActivity.CONTACT_ID_MESSAGE, ContactListActivity.NEW_CONTACT_ID);
			
			dbHelper = ContactsDbHelper.getInstance(this);
			contact = dbHelper.getContact(id);
			
			((EditText) findViewById(R.id.first_name)).setText(contact.getFirstName(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.last_name)).setText(contact.getLastName(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.mobile_phone)).setText(contact.getMobilePhone(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.home_phone)).setText(contact.getHomePhone(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.work_phone)).setText(contact.getWorkPhone(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.email_address)).setText(contact.getEmailAddress(), TextView.BufferType.EDITABLE);
			((EditText) findViewById(R.id.home_address)).setText(contact.getHomeAddress(), TextView.BufferType.EDITABLE);
			((TextView) findViewById(R.id.date_of_birth)).setText(contact.getDateOfBirth());
			
			String date = contact.getDateOfBirth();
			
			if (date != null) {
				String[] dateParts = date.split("/");
				if (dateParts.length == 3) {
					
					day = new Integer(dateParts[0]);
					month = new Integer(dateParts[1]);
					year = new Integer(dateParts[2]);
					
				}
			}
			
			
			
			dateOfBirth = (TextView)findViewById(R.id.date_of_birth);
			
			dateOfBirth.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					showDialog(DATE_PICKER_DIALOG_ID);
					
				}
			});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
		case R.id.discard:
			finish();
			
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
				id,
				((EditText) findViewById(R.id.first_name)).getText().toString(),
				((EditText) findViewById(R.id.last_name)).getText().toString(),
				((EditText) findViewById(R.id.mobile_phone)).getText().toString(),
				((EditText) findViewById(R.id.home_phone)).getText().toString(),
				((EditText) findViewById(R.id.work_phone)).getText().toString(),
				((EditText) findViewById(R.id.email_address)).getText().toString(),
				((EditText) findViewById(R.id.home_address)).getText().toString(),
				((TextView) findViewById(R.id.date_of_birth)).getText().toString(),
				"" // TODO: Photo URI to be added!
				);
		
		if (! contact.isBlank()) {
			dbHelper.updateContact(contact);
		}
		
	}
	
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int month, int day) {
                    dateOfBirth.setText(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
                }
            };

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_DIALOG_ID:
			
			if (day == 0 && month == 0 && year == 0 ) {
				Calendar calendar = Calendar.getInstance();
				year = calendar.get(Calendar.YEAR);
		        month = calendar.get(Calendar.MONTH);
		        day = calendar.get(Calendar.DAY_OF_MONTH);
			}
			
			return new DatePickerDialog(this, dateSetListener, year, month,
					day);
		}
		return null;
	}

}
