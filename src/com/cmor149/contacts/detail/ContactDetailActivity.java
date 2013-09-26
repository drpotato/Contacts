package com.cmor149.contacts.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cmor149.contacts.ContactListActivity;
import com.cmor149.contacts.EditContactActivity;
import com.cmor149.contacts.R;
import com.cmor149.contacts.database.ContactsDbHelper;

/**
 * An activity representing a single Contact detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a {@link ContactListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ContactDetailFragment}.
 */
public class ContactDetailActivity extends FragmentActivity {
	
	private static final String TAG = "Contacts"; 
	
	private long id = -1;
	private static final String CONTACT_ID_MESSAGE = "contact_id";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(TAG, "ContactDetailActivity: onCreate started");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			
			Log.d(TAG, "ContactDetailActivity: Creating fragment");
			
			id = getIntent().getLongExtra(ContactDetailFragment.ARG_ITEM_ID, -1);
			
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putLong(ContactDetailFragment.ARG_ITEM_ID, getIntent().getLongExtra(ContactDetailFragment.ARG_ITEM_ID, -1));
			ContactDetailFragment fragment = new ContactDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.contact_detail_container, fragment).commit();
		}
		
		Log.d(TAG, "ContactDetailActivity: onCreate finished");
	}
	
	/**
	 * Creates the menu to edit or delete the contact.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_detail_activity, menu);
		return true;
	}
	
	/**
	 * This is called when the user clicks an action bar menu button. Different
	 * behavior is expected when different buttons are clicked.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown.
			
			// If the user clicks the 'back' section of the action bar, send them
			// to the previous screen.			
			NavUtils.navigateUpTo(this, new Intent(this,
					ContactListActivity.class));
			return true;
		case R.id.edit:
			// If the user clicked the edit contact button, call the method to 
			// edit the contact.
			editContact();
			break;
		case R.id.delete:
			// If the user clicked the delete contact button, call the method to 
			// delete the contact.
			deleteContact();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * This is called when the user clicks on the edit contact button, starts a
	 * new activity to edit the contact.
	 */
	private void editContact() {
		Intent intent = new Intent(this, EditContactActivity.class);
		intent.putExtra(CONTACT_ID_MESSAGE, id);
		startActivity(intent);
	}
	
	/**
	 * Is called when the user clicks on the delete contact button.
	 */
	private void deleteContact() {
		
		final ContactsDbHelper dbHelper = ContactsDbHelper.getInstance(this);
		
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			
			/**
			 * If the user responds positively, delete the contact. Otherwise do nothing.
			 */
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            dbHelper.deleteContact(id);
		            onBackPressed();

		        case DialogInterface.BUTTON_NEGATIVE:
		            // User cancelled deletion, abort.
		            break;
		        }
		    }
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		// Creates a pop up to ask if they really want to delete the contact. Then shows it.
		builder.setMessage("Are you sure?").setNegativeButton("No", dialogClickListener)
			.setPositiveButton("Yes", dialogClickListener).show();
	}
}
