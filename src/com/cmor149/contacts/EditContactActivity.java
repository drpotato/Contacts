package com.cmor149.contacts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmor149.contacts.database.Contact;
import com.cmor149.contacts.database.ContactsDbHelper;


public class EditContactActivity extends Activity {

	private static final String TAG = "Contacts";

	private static final int DATE_PICKER_DIALOG_ID = 1;
	private static final int PHOTO_SELECTION_REQUEST_ID = 1;
	private static final int PHOTO_CROP_REQUEST_ID = 2;

	private ContactsDbHelper dbHelper;
	private long id = -1;
	private Contact contact;
	private TextView dateOfBirth;
	private ImageView photoView;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private Bitmap image;
	private String fileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);

		Log.d(TAG, "EditContactActivity: onCreate started");

		// Standard super call.
		super.onCreate(savedInstanceState);


		// Change the Action Bar title and icon into a Done button.
		Log.d(TAG, "EditContactActivity: Change Action Bar title");

		setTitle("Done");

		Log.d(TAG, "EditContactActivity: Change Action Bar icon");

		getActionBar().setIcon(R.drawable.navigation_accept_dark);

		Log.d(TAG, "EditContactActivity: Enable the action bar button");

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);


		// ContactListActivity will pass the Contact Id with the intent.
		// This needs to be retrieved so we can query the database.
		Log.d(TAG, "EditContactActivity: Get the intent from the previous activity");

		Intent intent = getIntent();
		id = intent.getLongExtra(ContactListActivity.CONTACT_ID_MESSAGE, ContactListActivity.NEW_CONTACT_ID);


		// Gets an instance of the databaseHelper and ask it for the
		// contact is associated with the Id number.
		// Note: if the id number is -1, a blank/new contact is created and
		// returned.
		dbHelper = ContactsDbHelper.getInstance(this);
		contact = dbHelper.getContact(id);


		// Fill in the fields of the UI with the existing contact data.
		((EditText) findViewById(R.id.first_name)).setText(contact.getFirstName(), TextView.BufferType.EDITABLE);
		((EditText) findViewById(R.id.last_name)).setText(contact.getLastName(), TextView.BufferType.EDITABLE);
		((EditText) findViewById(R.id.mobile_phone)).setText(contact.getMobilePhone(), TextView.BufferType.EDITABLE);
		((EditText) findViewById(R.id.home_phone)).setText(contact.getHomePhone(), TextView.BufferType.EDITABLE);
		((EditText) findViewById(R.id.work_phone)).setText(contact.getWorkPhone(), TextView.BufferType.EDITABLE);
		((EditText) findViewById(R.id.email_address)).setText(contact.getEmailAddress(), TextView.BufferType.EDITABLE);
		((EditText) findViewById(R.id.home_address)).setText(contact.getHomeAddress(), TextView.BufferType.EDITABLE);
		((TextView) findViewById(R.id.date_of_birth)).setText(contact.getDateOfBirth());


		// Converts the date of birth string into a integer representation of the date.
		String date = contact.getDateOfBirth();
		if (date != null) {
			String[] dateParts = date.split("/");
			if (dateParts.length == 3) {
				day = new Integer(dateParts[0]);
				month = new Integer(dateParts[1]);
				year = new Integer(dateParts[2]);
			}
		}


		// Finds the views that contains the contacts photo and date of birth.
		photoView = (ImageView)findViewById(R.id.contact_image);
		dateOfBirth = (TextView)findViewById(R.id.date_of_birth);


		// TODO: Does not work!
		photoView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				pickImage();
			}
		});


		// Set a listener for the date of birth field.
		dateOfBirth.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				showDialog(DATE_PICKER_DIALOG_ID);

			}
		});


		readImage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// TODO: This needs to be redone, ignore this please...
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
		case R.id.discard:
			finish();
		}

		return super.onOptionsItemSelected(item);

	}

	// Saves the contact when the back button is pressed.
	@Override
	public void onBackPressed() {

		saveContact();

		super.onBackPressed();
	}

	/**
	 * Saves the contact using the database helper. Will not save the contact
	 * if all fields are blank.
	 */
	private void saveContact() {

		// The calendar will be used for getting the current time in
		// milliseconds
		Calendar calendar = Calendar.getInstance();

		// Creates a new file according to the current time.
		String fileName = Long.toString(calendar.getTimeInMillis()) + ".png";
		File file = new File(this.getFilesDir(), fileName);

		// Tries to create a new file.
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}


		// Tries to write the image to the private section of the hard drive.
		try { 
			FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
			image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		// Creates a new contact with the information from the views.
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
				fileName
				);

		// If the contact isn't blank, saves the image.
		if (!contact.isEmpty()) {
			dbHelper.updateContact(contact);
		}

	}

	/**
	 * When the user sets the date in the dialog, this method is called.
	 * It changes the information given back into a string and applies it to
	 * the date_of_birth view.
	 */
	private DatePickerDialog.OnDateSetListener dateSetListener =
			new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year,
				int month, int day) {
			dateOfBirth.setText(Integer.toString(day) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year));
		}
	};

	/**
	 * Is called when a call to create a dialog is made.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_DIALOG_ID:

			// If the date of birth field is empty, 
			if (day == 0 && month == 0 && year == 0 ) {
				Calendar calendar = Calendar.getInstance();
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH);
				day = calendar.get(Calendar.DAY_OF_MONTH);
			}

			// Build the DatePickerDialog and return it.
			return new DatePickerDialog(this, dateSetListener, year, month + 1,
					day);
		}
		return null;
	}

	/**
	 * Makes a call to the system to get an image.
	 */
	private void pickImage() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, PHOTO_SELECTION_REQUEST_ID);
	}

	private void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");   
		intent.setData(uri);  
		intent.putExtra("crop", "true");  
		intent.putExtra("aspectX", 1);  
		intent.putExtra("aspectY", 1);  
		intent.putExtra("outputX", 768);  
		intent.putExtra("outputY", 768);  
		intent.putExtra("noFaceDetection", true);  
		intent.putExtra("return-data", true);                                  
		startActivityForResult(intent, PHOTO_CROP_REQUEST_ID);
	}

	private void savePhoto(Intent intent) {

		// The calendar will be used for getting the current time in
		// milliseconds
		Calendar calendar = Calendar.getInstance();

		// Creates a new file according to the current time.
		String fileName = Long.toString(calendar.getTimeInMillis()) + ".png";
		File file = new File(this.getFilesDir(), fileName);

		// Tries to create a new file.
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Bundle extras = intent.getExtras(); 

		try {
			image = extras.getParcelable("data");
			FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
			image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
			photoView.setImageBitmap(image);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	private void readImage() {
		fileName = contact.getPhotoUri();
		if (fileName == null || fileName.isEmpty()) {
			return;
		}
		
		File file = new File(this.getFilesDir(), fileName);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		image = BitmapFactory.decodeStream(fileInputStream);
		photoView.setImageBitmap(image);
	}

	/**
	 * This is called when the system returns the image that was requested.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK)
			switch (requestCode) {
			case PHOTO_SELECTION_REQUEST_ID:
				cropPhoto(data.getData());
				break;
			case PHOTO_CROP_REQUEST_ID:
				savePhoto(data);
				break;
			}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
