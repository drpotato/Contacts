package com.cmor149.contacts.database;

import com.cmor149.contacts.database.ContactsContract.ContactsEntry;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * @author Christopher Morgan
 * 	  UPI: cmor149
 * 	  ID:  1744263
 * 	  
 *
 */
public class ContactsProvider extends ContentProvider {
	
	public static final String AUTHORITY = "com.cmor149.contacts.provider";
	public static final String SCHEME = "content://";
	
	public static final String CONTACTS = SCHEME + AUTHORITY + "/contact";
	public static final Uri URI_CONTACTS = Uri.parse(CONTACTS);
	public static final String CONTACT_BASE = CONTACTS + "/";
	
	public ContactsProvider() {
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public String getType(Uri uri) {
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO: Implement this to handle requests to insert a new row.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public boolean onCreate() {
		return true;
	}
	
	/**
	 * TODO Potentially change implementation!
	 * This allows the database to be queried asynchronously, not essential
	 * for a project this small but is good practice.
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		
		if (URI_CONTACTS.equals(uri)){
			// If the URI is for the entire database, then create a cursor for all
			// database entries.
			SQLiteOpenHelper dbHelper = ContactsDbHelper.getInstance(getContext());
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			cursor = db.query(ContactsEntry.TABLE_NAME,
					projection,
					selection,
					selectionArgs,
					null,
					null,
					sortOrder);
			
		} else if (uri.toString().startsWith(CONTACT_BASE)){
			// If the URI starts with the base contact URI, a single
			// contact is being requested. Create a cursor to access this
			// database entry.
			long id = Long.parseLong(uri.getLastPathSegment());
			SQLiteOpenHelper dbHelper = ContactsDbHelper.getInstance(getContext());
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			
			cursor = db.query(ContactsEntry.TABLE_NAME,
					ContactsEntry.COLUMNS,
					ContactsEntry.COLUMN_NAME_CONTACT_ID + " IS ? ",
					new String[] {String.valueOf(id)},
					null,
					null,
					null);
		} else {
			// Otherwise, the URI is completely invalid, throw an exception.
			throw new UnsupportedOperationException("Invalid URI");
		}
		
		// Sets the Contacts Provider to listen changes to the database.
		cursor.setNotificationUri(getContext().getContentResolver(), URI_CONTACTS);
		
		// Return the cursor that was created.
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
