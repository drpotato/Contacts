package com.cmor149.contacts.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cmor149.contacts.database.ContactsContract.ContactsEntry;

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
		db.execSQL(ContactsContract.SQL_CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public synchronized Contact getContact(final long id) {
		
		Contact contact = null;
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selection = ContactsEntry.COLUMN_NAME_CONTACT_ID + "IS ?";
		String[] selectionArgs = {String.valueOf(id)};
		
		Cursor cursor = db.query(ContactsEntry.TABLE_NAME,
				ContactsEntry.COLUMNS,
				selection,
				selectionArgs,
				null,
				null,
				null
				);
		
		// Checks that the cursor is valid and within the bounds of the table,
		// then moves the cursor to the first element.
		if (!(cursor == null || /*cursor.isBeforeFirst() ||*/ cursor.isAfterLast()) && cursor.moveToFirst()) {
			
			// A new contact is created using the cursor
			contact = new Contact(cursor);
		}
		
		// The Cursor should always be closed it is no longer used.
		cursor.close();
		
		return contact;
	}
	
	public synchronized boolean insertContact(final Contact contact) {
		
		// Gets the database to use.
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Attempts to insert the contact into the database. Result is stored
		// in 'value'.
		long id = db.insert(ContactsEntry.TABLE_NAME,
				null,
				contact.getContent());
		// If the id of the contact is greater that -1, the contact was
		// successfully added to the database.
		if (id > -1){
			
			// Notify the Contacts Provider
			notifyContactsProvider();
			
			// Set id for the contact id and return the result.
			contact.id = id;
			return true;
		}
		
		// The 
		return false;
	}
	
	public synchronized boolean updateContact(final Contact contact) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		int value = 0;
		String selection = ContactsEntry.COLUMN_NAME_CONTACT_ID + " IS ?";
		String[] selectionArgs = {Long.toString(contact.id)};
		value = db.update(ContactsEntry.TABLE_NAME, contact.getContent(),
				selection,
				selectionArgs);
		
		// Notify the Contacts provider that something has changed and return
		// let the calling method know that it was successful.
		if (value > 0) {
			notifyContactsProvider();
			return true;
		} else {
			
			// Otherwise updating the contact fail.
			return false;
		}
	}
	
	public synchronized int deleteContact(final long id) {
		
		String selection = ContactsEntry.COLUMN_NAME_CONTACT_ID + " IS ?";
		String[] selectionArgs = {Long.toString(id)};
		
		SQLiteDatabase db = this.getWritableDatabase();
		int result = db.delete(ContactsEntry.TABLE_NAME,
				selection,
				selectionArgs
				);
		// If deleting the contact was successful, notify the Contacts provider
		if (result > 0) {
			notifyContactsProvider();
		}
		return result;
	}
	
	private void notifyContactsProvider() {
		context.getContentResolver().notifyChange(ContactsProvider.URI_CONTACTS, null, false);
	}

}
