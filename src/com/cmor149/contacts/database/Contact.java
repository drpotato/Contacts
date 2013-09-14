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
	
	// Package visibility is used for ease of access throughout the application.
	long id = -1;
	private String first_name;
	private String last_name;
	private String mobile_phone;
	private String home_phone;
	private String work_phone;
	private String email_address;
	private String home_address;
	private String date_of_birth;
	private String photo_uri;
	
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
		this.id 					= cursor.getLong(0);
		this.first_name 			= cursor.getString(1);
		this.last_name 				= cursor.getString(2);
		this.mobile_phone 	= cursor.getString(3);
		this.home_phone 		= cursor.getString(4);
		this.work_phone 		= cursor.getString(5);
		this.email_address 			= cursor.getString(6);
		this.home_address 			= cursor.getString(7);
		this.date_of_birth 			= cursor.getString(8);
		this.photo_uri 				= cursor.getString(9);
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
	
	
	/**
	 * Getters for all fields
	 * @return
	 */
	public long id() {
		return id;
	}
	public String getMobilePhone() {
		return mobile_phone;
	}
	public String getHomePhone() {
		return home_phone;
	}
	public String getWorkPhone() {
		return work_phone;
	}
	public String getEmailAddress() {
		return email_address;
	}
	public String getHomeAddress() {
		return home_address;
	}
	public String getDateOfBirth() {
		return date_of_birth;
	}
	public String getPhotoUri() {
		return photo_uri;
	}
}
