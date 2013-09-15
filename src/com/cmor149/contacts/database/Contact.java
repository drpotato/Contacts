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
	public String first_name;
	public String last_name;
	public String mobile_phone;
	public String home_phone;
	public String work_phone;
	public String email_address;
	public String home_address;
	public String date_of_birth;
	public String photo_uri;
	
	/**
	 * Creates a blank Contact object.
	 */
	public Contact() {}
	
	/**
	 * Gathers information from the database using a cursor and converts it
	 * into a Contact object.
	 * @param cursor - a cursor connected to the Contacts database.
	 */
	public Contact(final Cursor cursor) {
		this.id 			= cursor.getLong(0);
		this.first_name 	= cursor.getString(1);
		this.last_name 		= cursor.getString(2);
		this.mobile_phone 	= cursor.getString(3);
		this.home_phone 	= cursor.getString(4);
		this.work_phone 	= cursor.getString(5);
		this.email_address 	= cursor.getString(6);
		this.home_address 	= cursor.getString(7);
		this.date_of_birth 	= cursor.getString(8);
		this.photo_uri 		= cursor.getString(9);
	}
	
	/**
	 * Converts the Contact object's fields into a ContentValues object to
	 * allow for easy insertion into the database.
	 */
	public ContentValues getContent() {
		final ContentValues contentValues = new ContentValues();
		
		contentValues.put(ContactsEntry.COLUMN_NAME_FIRST_NAME, first_name);
		contentValues.put(ContactsEntry.COLUMN_NAME_LAST_NAME, last_name);
		contentValues.put(ContactsEntry.COLUMN_NAME_MOBILE_PHONE, mobile_phone);
		contentValues.put(ContactsEntry.COLUMN_NAME_HOME_PHONE, home_phone);
		contentValues.put(ContactsEntry.COLUMN_NAME_WORK_PHONE, work_phone);
		contentValues.put(ContactsEntry.COLUMN_NAME_EMAIL_ADDRESS, email_address);
		contentValues.put(ContactsEntry.COLUMN_NAME_HOME_ADDRESS, home_address);
		contentValues.put(ContactsEntry.COLUMN_NAME_DATE_OF_BIRTH, date_of_birth);
		contentValues.put(ContactsEntry.COLUMN_NAME_PHOTO_URI, photo_uri);
		
		return contentValues;
	}
}
