package com.cmor149.contacts.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cmor149.contacts.database.ContactsContract.ContactsEntry;

public class ContactsDbHelper extends SQLiteOpenHelper {
	
	private static String LOG = "Contacts";
	
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
	
	/**
	 * Queries the database for a contact. If found, returns the Contact
	 * object corresponding to that in the database. Otherwise, creates a new contact.
	 * @param id
	 * @return
	 */
	public synchronized Contact getContact(final long id) {
		
		Log.d(LOG, "ContactsDbHelper: getContact starting");
		Log.d(LOG, "ContactsDbHelper: id = " + Long.toString(id));
		
		// Set up the contact and database.
		Contact contact = new Contact();
		SQLiteDatabase db = this.getReadableDatabase();
		
		// Set up the query statements.
		String selection = ContactsEntry.COLUMN_NAME_CONTACT_ID + " IS ?";
		String[] selectionArgs = {String.valueOf(id)};
		
		Log.d(LOG, "ContactsDbHelper: Querying database.");
		
		// Create a cursor that queries the database for the specific contact.
		Cursor cursor = db.query(ContactsEntry.TABLE_NAME,
				ContactsEntry.COLUMNS,
				selection,
				selectionArgs,
				null,
				null,
				null
				);
		
		if (cursor == null) {
			Log.d(LOG, "ContactsDbHelper: WARNING!!! null cursor");
		}
		if (cursor.isAfterLast()) {
			Log.d(LOG, "ContactsDbHelper: WARNING!!! cursor after last");
		}
		
		
		
		// Checks that the cursor is valid and within the bounds of the table,
		// then moves the cursor to the first element.
		if (!(cursor == null /*|| cursor.isBeforeFirst() || cursor.isAfterLast())*/) && cursor.moveToFirst()) {
			
			// A new contact is created using the cursor
			contact = new Contact(cursor);
			
			String log = "ID: " + contact.id + 
					"\nFirst Name: " + contact.getFirstName() +
					"\nLast Name: " + contact.getLastName() + 
					"\nMobile Phone: " + contact.getMobilePhone() +
					"\nHome Phone: " + contact.getHomePhone() +
					"\nWork Phone: " + contact.getWorkPhone() +
					"\nEmail Address: " + contact.getEmailAddress() + 
					"\nHome Address: " + contact.getHomeAddress() + 
					"\nDateOfBirth: " + contact.getDateOfBirth() + 
					"\nPhotoURI: " + contact.getPhotoUri();
						
			
			Log.d(LOG, "ContactsDbHelper:\n" + log);
		}
		
		// The Cursor should always be closed it is no longer used.
		cursor.close();
		
		// Return the Contact created
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
	
	/**
	 * 
	 * @param contact - Contact object that contains the contact information to
	 * 					be updated in the database.
	 * 
	 * @return		  - True if updating the database was successful
	 * 				  - False if updating the database was unsuccessful
	 */
	public synchronized boolean updateContact(final Contact contact) {
		
		String log = "ID: |" + contact.id + "|" + 
				"\nFirst Name: |" + contact.getFirstName() + "|" + 
				"\nLast Name: |" + contact.getLastName() +  "|" + 
				"\nMobile Phone: |" + contact.getMobilePhone() + "|" + 
				"\nHome Phone: |" + contact.getHomePhone() + "|" + 
				"\nWork Phone: |" + contact.getWorkPhone() + "|" + 
				"\nEmail Address: |" + contact.getEmailAddress() +  "|" + 
				"\nHome Address: |" + contact.getHomeAddress() +  "|" + 
				"\nDateOfBirth: |" + contact.getDateOfBirth() +  "|" + 
				"\nPhotoURI: |" + contact.getPhotoUri() + "|"; 
		
		Log.d(LOG, "ContactsDbHelper:\n" + log);
		
		// Set up the database
		SQLiteDatabase db = this.getWritableDatabase();
		String selection = ContactsEntry.COLUMN_NAME_CONTACT_ID + " IS ?";
		String[] selectionArgs = {Long.toString(contact.id)};
		
		// Try to update the contact in the database. If it was successful,
		// value is set to 1. Otherwise, value is set to 0. 
		int value = db.update(ContactsEntry.TABLE_NAME, contact.getContent(),
				selection,
				selectionArgs);
		
		// If updating the database entry was successful notify the contacts
		// provider that the database has changed. 
		if (value > 0) {
			notifyContactsProvider();
			return true;
			
		} else {
			
			// Otherwise, updating the contact failed so attempt to insert it.
			long id = db.insert(ContactsEntry.TABLE_NAME,
					null,
					contact.getContent());
			
			// If the id of the contact is greater that -1, the contact was
			// successfully added to the database.
			if (id > -1){
				
				// Set id for the contact.
				contact.id = id;
				
				// Notify the Contacts Provider
				notifyContactsProvider();
				
				return true;
			}
			
			//Updating the contact failed, notify the caller.
			return false;
			
		}
	}
	
	/**
	 * Deletes a contact entry from the database.
	 * @param id - The Id of the contact to delete.
	 * @return 
	 */
	public synchronized boolean deleteContact(final long id) {
		
		// Set up the database and queries.
		SQLiteDatabase db = this.getWritableDatabase();
		String selection = ContactsEntry.COLUMN_NAME_CONTACT_ID + " IS ?";
		String[] selectionArgs = {Long.toString(id)};
		
		// Attempt to delete the contact. If successful, result will equal 1.
		// If not, 0.
		int result = db.delete(ContactsEntry.TABLE_NAME,
				selection,
				selectionArgs
				);
		// If deleting the contact was successful, notify the Contacts provider
		if (result > 0) {
			notifyContactsProvider();
		}
		
		// Notifies the caller of success or not.
		return result > 0;
	}
	
	private void notifyContactsProvider() {
		context.getContentResolver().notifyChange(ContactsProvider.URI_CONTACTS, null, false);
	}
	
	/**
	 * Deletes all entries in the database.
	 * @return - If it was successful, returns true. False otherwise.
	 */
	public synchronized boolean flushDatabase() {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		int result = db.delete(ContactsEntry.TABLE_NAME, "1", null);
		
		return result > 0;
		
	}
}
