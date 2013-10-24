package com.cmor149.contacts;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.cmor149.contacts.database.ContactsContract.ContactsEntry;
import com.cmor149.contacts.database.ContactsProvider;
import com.cmor149.contacts.detail.ContactDetailFragment;

/**
 * A list fragment representing a list of Contacts. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link ContactDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ContactListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

	private static final String TAG  = "Contacts";

	private String sortOrder = ContactsEntry.COLUMN_NAME_FIRST_NAME;
	
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(long id);
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ContactListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "ContactListFragment: onCreate started");

		super.onCreate(savedInstanceState);

		
		// The names of the columns that will be read from the database into
		// the views.
		String[] columns = new String[] {
				ContactsEntry.COLUMN_NAME_FULL_NAME,
				ContactsEntry.COLUMN_NAME_PHOTO_URI
		};

		
		// The IDs of the views that the data will be loaded into.
		int[] viewIDs = new int [] {
				R.id.contact_list_name,
				R.id.contact_list_image
		};

		Log.d(TAG, "ContactListFragment: Creating a cursor/list adapter");
		
		
		// Creates a cursor adapter to be used to load the data from the
		// database		
		ContactSimpleCursorAdapter cursorAdapter = new ContactSimpleCursorAdapter(
				getActivity(),
				R.layout.contact_list_item,
				null,
				columns,
				viewIDs,
				0
				);
		
		cursorAdapter.setContext(getActivity());
		

		Log.d(TAG, "ContactListFragment: Setting the adapter");

		
		// Sets the list adapter to the cursor adapter created earlier
		setListAdapter(cursorAdapter);

		Log.d(TAG, "ContactListFragment: Initalising the Loader");

		
		// Load the contacts into the list, as fragment implements
		// LoaderManager.LoaderCallbacks<Cursor>, a reference of itself can be
		// passed to the LoaderManager from getLoaderManager().
		getLoaderManager().initLoader(0, null, this);

		Log.d(TAG, "ContactListFragment: onCreate finished");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "ContactListFragment: onCreateView started");

		return inflater.inflate(R.layout.fragment_contact_list, null);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.d(TAG, "ContactListFragment: onViewCreated started");

		super.onViewCreated(view, savedInstanceState);


		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}

		Log.d(TAG, "ContactListFragment: onViewCreate finished.");
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}
		
		// Sets the callback to the activity.
		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(getListAdapter().getItemId(position));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}
	
	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
	
	@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    // Associate searchable configuration with the SearchView
	    SearchManager searchManager =
	           (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView =
	            (SearchView) menu.findItem(R.id.search).getActionView();
	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getActivity().getComponentName()));
	}

	// Implemented methods for the loader manager interface:

	/**
	 * This is called when getLoaderManager is executed. Loads the data from
	 * the database into the list.
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(),
				ContactsProvider.URI_CONTACTS,
				ContactsEntry.COLUMNS,
				null,
				null,
				sortOrder);
	}
	
	/**
	 * Default implementation
	 */
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(cursor);

	}

	/**
	 * Default implementation
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
	}
	
	/**
	 * Changes the sort order of the Contacts in the list view.
	 * @param item - determines how the Contacts are sorted.
	 */
	public void changeSortOrder(MenuItem item) {
		
		// The database column that the list is sorted by is determined by the
		// id of the menu item that was passed to this function.
		switch (item.getItemId()) {
		case R.id.sort_first_name:
			sortOrder = ContactsEntry.COLUMN_NAME_FIRST_NAME;
			break;
		case R.id.sort_last_name:
			sortOrder = ContactsEntry.COLUMN_NAME_LAST_NAME;
			break;
		case R.id.sort_phone_number:
			sortOrder = ContactsEntry.COLUMN_NAME_MOBILE_PHONE;
			break;
		}
		
		// Restarts the Loader, thus reloading the Contacts in a different
		// order.
		getLoaderManager().restartLoader(0, null, this);

	}
}
