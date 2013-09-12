package com.cmor149.contacts.database;

import android.provider.BaseColumns;
/**
 * @author Christopher Morgan
 * 	  UPI: cmor149
 * 	  ID:  1744263
 * 	  
 *
 */

public final class ContactsContract {
	
	public ContactsContract() {};
	
	/**
	 * This class allows other classes to access the database column names
	 * needing to know them. Also, the database can be changed here and it
	 * propagates to all other classes.
	 */
	public static abstract class ContactsEntry implements BaseColumns {
		public static final String TABLE_NAME = "Contact";
		public static final String COLUMN_NAME_CONTACT_ID = "_id";
		public static final String COLUMN_NAME_FIRST_NAME = "first_name";
		public static final String COLUMN_NAME_LAST_NAME = "last_name";
		public static final String COLUMN_NAME_MOBILE_PHONE = "mobile_phone_number";
		public static final String COLUMN_NAME_HOME_PHONE = "home_phone_number";
		public static final String COLUMN_NAME_WORK_PHONE = "work_phone_number";
		public static final String COLUMN_NAME_EMAIL_ADDRESS = "email_address_number";
		public static final String COLUMN_NAME_HOME_ADDRESS = "home_address_number";
		public static final String COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth_number";
		public static final String COLUMN_NAME_PHOTO_URI = "photo_uri";
		
		// The order of the fields in the database
		public static final String[] FIELDS = {COLUMN_NAME_CONTACT_ID,
			COLUMN_NAME_FIRST_NAME,
			COLUMN_NAME_LAST_NAME,
			COLUMN_NAME_MOBILE_PHONE,
			COLUMN_NAME_HOME_PHONE,
			COLUMN_NAME_WORK_PHONE,
			COLUMN_NAME_EMAIL_ADDRESS, 
			COLUMN_NAME_HOME_ADDRESS,
			COLUMN_NAME_DATE_OF_BIRTH,
			COLUMN_NAME_PHOTO_URI};
	}
	
	// Makes the formatting of SQL commands nicer.
	private static final String INTEGER_TYPE = " INTEGER";	 
	private static final String TEXT_TYPE = " TEXT";
	private static final String PRIMARY_KEY_AUTOINCREMENT = " PRIMARY KEY AUTOINCREMENT";
	private static final String COMMA = ",";
	
	// This string allows for easy creation of the table.
	public static final String SQL_CREATE_TABLE = 
			"CREATE TABLE " + ContactsEntry.TABLE_NAME + " (" +
			ContactsEntry.COLUMN_NAME_CONTACT_ID + INTEGER_TYPE + PRIMARY_KEY_AUTOINCREMENT + COMMA +
			ContactsEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_MOBILE_PHONE + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_HOME_PHONE + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_WORK_PHONE + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_EMAIL_ADDRESS + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_HOME_ADDRESS + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_DATE_OF_BIRTH + TEXT_TYPE + COMMA +
			ContactsEntry.COLUMN_NAME_PHOTO_URI + TEXT_TYPE + COMMA + " )";
	
	// This string allows for easy deletion of the table.
	private static final String SQL_DELETE_TABLE = 
			"DROP TABLE IF EXISTS " + ContactsEntry.TABLE_NAME;
}
