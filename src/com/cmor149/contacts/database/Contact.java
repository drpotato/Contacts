package com.cmor149.contacts.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Contact {
	private long id = -1;
	private String first_name;
	private String last_name;
	private String mobile_phone_number;
	private String home_phone_number;
	private String work_phone_number;
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
		this.mobile_phone_number 	= cursor.getString(3);
		this.home_phone_number 		= cursor.getString(4);
		this.work_phone_number 		= cursor.getString(5);
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
	}
}
