package com.cmor149.contacts.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.cmor149.contacts.database.ContactsContract.ContactsEntry;

/**
 * @author Christopher Morgan
 * 	  UPI: cmor149
 * 	  ID:  1744263
 * 
 */
public class Contact {
	
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
	
	public Contact(String firstName,
			String lastName,
			String mobilePhone,
			String homePhone,
			String workPhone,
			String emailAddress, 
			String homeAddress,
			String dateOfBirth,
			String photoUri) {
		
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
		this.id 			= cursor.getLong(0);
		this.firstName 		= cursor.getString(1);
		this.lastName 		= cursor.getString(2);
		this.mobilePhone 	= cursor.getString(3);
		this.homePhone 		= cursor.getString(4);
		this.workPhone 		= cursor.getString(5);
		this.emailAddress 	= cursor.getString(6);
		this.homeAddress 	= cursor.getString(7);
		this.dateOfBirth 	= cursor.getString(8);
		this.photoUri 		= cursor.getString(9);
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
	 * @return the first_name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the last_name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the mobile_phone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @return the home_phone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @return the work_phone
	 */
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * @return the email_address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return the home_address
	 */
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * @return the date_of_birth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
}
