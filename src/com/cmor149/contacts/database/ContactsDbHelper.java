package com.cmor149.contacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDbHelper extends SQLiteOpenHelper {
	
	private static ContactsDbHelper contactsDbHelper;
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Contacts.db";
	private final Context context;

	
	public static ContactsDbHelper getInstance(final Context context) {
        if (contactsDbHelper == null) {
            contactsDbHelper = new ContactsDbHelper(context);
        }
        return contactsDbHelper;
    }
	
	public ContactsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Good idea to use process context here
        this.context = context.getApplicationContext();
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
