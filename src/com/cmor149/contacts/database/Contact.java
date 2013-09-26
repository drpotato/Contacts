package com.cmor149.contacts.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.cmor149.contacts.database.ContactsContract.ContactsEntry;

/**
 * @author Christopher Morgan
 * 	  UPI: cmor149
 * 	  ID:  1744263
 * 
 */
public class Contact {
	
	private static final String TAG = "Contacts";
	
	// Public visibility is used for ease of access throughout the application.
	public long id = -1;
	private String firstName;
	private String lastName;
	private String mobilePhone;
	private String homePhone;
	private String workPhone;
	private String emailAddress;
	private String homeAddress;
	private String dateOfBirth;
	private String photoUri;
	
	/**
	 * Creates a blank Contact object.
	 */
	public Contact() {}
	
	public Contact(long id,
			String firstName,
			String lastName,
			String mobilePhone,
			String homePhone,
			String workPhone,
			String emailAddress, 
			String homeAddress,
			String dateOfBirth,
			String photoUri) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobilePhone = mobilePhone;
		this.homePhone = homePhone;
		this.workPhone = workPhone;
		this.emailAddress = emailAddress;
		this.homeAddress = homeAddress;
		this.dateOfBirth = dateOfBirth;
		this.photoUri = photoUri;
		
	}
	
	/**
	 * Gathers information from the database using a cursor and converts it
	 * into a Contact object.
	 * @param cursor - a cursor connected to the Contacts database.
	 */
	public Contact(final Cursor cursor) {
		id 				= cursor.getLong(0);
		firstName 		= cursor.getString(1);
		lastName 		= cursor.getString(2);
		mobilePhone 	= cursor.getString(3);
		homePhone 		= cursor.getString(4);
		workPhone 		= cursor.getString(5);
		emailAddress 	= cursor.getString(6);
		homeAddress 	= cursor.getString(7);
		dateOfBirth 	= cursor.getString(8);
		photoUri 		= cursor.getString(9);
	}
	
	/**
	 * Converts the Contact object's fields into a ContentValues object to
	 * allow for easy insertion into the database.
	 */
	public ContentValues getContent() {
		final ContentValues contentValues = new ContentValues();
		
		contentValues.put(ContactsEntry.COLUMN_NAME_FIRST_NAME, getFirstName());
		contentValues.put(ContactsEntry.COLUMN_NAME_LAST_NAME, getLastName());
		contentValues.put(ContactsEntry.COLUMN_NAME_MOBILE_PHONE, getMobilePhone());
		contentValues.put(ContactsEntry.COLUMN_NAME_HOME_PHONE, getHomePhone());
		contentValues.put(ContactsEntry.COLUMN_NAME_WORK_PHONE, getWorkPhone());
		contentValues.put(ContactsEntry.COLUMN_NAME_EMAIL_ADDRESS, getEmailAddress());
		contentValues.put(ContactsEntry.COLUMN_NAME_HOME_ADDRESS, getHomeAddress());
		contentValues.put(ContactsEntry.COLUMN_NAME_DATE_OF_BIRTH, getDateOfBirth());
		contentValues.put(ContactsEntry.COLUMN_NAME_PHOTO_URI, photoUri);
		
		return contentValues;
	}
	
	/**
	 * Checks whether the Contact has any fields filled in or not.
	 * 
	 * @return true if the Contact contains only empty fields
	 */
	public boolean isEmpty() {
		
		// Just a big conditional statement.
		if (firstName.isEmpty() && lastName.isEmpty() && mobilePhone.isEmpty() &&
				homePhone.isEmpty() && workPhone.isEmpty() && emailAddress.isEmpty() &&
				homeAddress.isEmpty() && dateOfBirth.isEmpty() && photoUri.isEmpty()) {
			return true;
		}
		
		return false;
	}

	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @return homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @return workPhone
	 */
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * @return emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return homeAddress
	 */
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * @return dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @return photoUri
	 */
	public String getPhotoUri() {
		return photoUri;
	}
}
