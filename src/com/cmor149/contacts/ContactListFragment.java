package com.cmor149.contacts;

import android.app.Activity;
import android.support.v4.app.LoaderManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.cmor149.contacts.database.ContactsContract.ContactsEntry;
import com.cmor149.contacts.database.ContactsProvider;
import com.cmor149.contacts.detail.ContactDetailFragment;
import com.cmor149.contacts.dummy.DummyContent;

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
	
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

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
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(long id) {
		}
	};

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
		
		// The 
		String[] columns = new String[] {
				ContactsEntry.COLUMN_NAME_FIRST_NAME
			};
		
		// The IDs of the views that the data will be loaded into.
		int[] viewIDs = new int [] {
				R.id.contact_list_name
			};
		
		Log.d(TAG, "ContactListFragment: Creating a cursor/list adapter");
		// Creates a cursor adapter to be used to load the data from the
		// database
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
				getActivity(),
				R.layout.contact_list_item,
				null,
				columns,
				viewIDs,
				0
				);
		
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

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
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
	
	// Implemented methods for the loader manager interface:
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(),
				ContactsProvider.URI_CONTACTS, ContactsEntry.COLUMNS, null, null,
                null);
    }

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(cursor);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
	}
}
