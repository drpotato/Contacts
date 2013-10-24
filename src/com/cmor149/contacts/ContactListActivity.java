package com.cmor149.contacts;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.cmor149.contacts.detail.ContactDetailActivity;
import com.cmor149.contacts.detail.ContactDetailFragment;

/**
 * 
 * An activity representing a list of Contacts. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ContactDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ContactListFragment} and the item details (if present) is a
 * {@link ContactDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ContactListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class ContactListActivity extends FragmentActivity implements
		ContactListFragment.Callbacks {
	
	public static final String TAG = "Contacts";
	public static final String CONTACT_ID_MESSAGE = "contact_id";
	public static final long NEW_CONTACT_ID = -1;
	
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		Log.d(TAG, "ContactListActivity: onCreate started");
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "ContactListActivity: Setting content view");
		setContentView(R.layout.activity_contact_list);
		
		if (findViewById(R.id.contact_detail_container) != null) {
			
			Log.d(TAG, "ContactListActivity: Large screen detected.");
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ContactListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.contact_list))
					.setActivateOnItemClick(true);
		}
		
		Log.d(TAG, "ContactListActivity: onCreated finished");
	}

	/**
	 * Callback method from {@link ContactListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(long id) {
		
		Log.d(TAG, "ContactListActivity: onItemSelected started");
		
		Log.d(TAG, "ContactListActivity: id = " + Long.toString(id));
		
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putLong(ContactDetailFragment.ARG_ITEM_ID, id);
			ContactDetailFragment fragment = new ContactDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.contact_detail_container, fragment).commit();

		} else {
			
			Log.d(TAG, "ContactListActivity: In single-pane mode, starting ContactDetailActivity");
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ContactDetailActivity.class);
			detailIntent.putExtra(ContactDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	/**
	 * Creates the menu to add a new contact and sort the contacts in the list.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_list_activity, menu);
		return true;
	}
	
	/**
	 * createNewContact is called when the create new contact button is
	 * selected in the action bar.
	 */
	public void createNewContact(MenuItem item) {
		// TODO: Implement method.
		
		Intent intent = new Intent(this, EditContactActivity.class);
		intent.putExtra(CONTACT_ID_MESSAGE, NEW_CONTACT_ID);
		startActivity(intent);
	}
	
	/**
	 * This is called when the user clicks on one of the sort options. It
	 * simply passes the message onto the fragment to deal with.
	 * @param item the menu item that was clicked on.
	 */
	public void changeSortOrder(MenuItem item) {
		((ContactListFragment) getSupportFragmentManager().findFragmentById(R.id.contact_list)).changeSortOrder(item);
	}
}
