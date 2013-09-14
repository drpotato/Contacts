package com.cmor149.contacts.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmor149.contacts.ContactListActivity;
import com.cmor149.contacts.R;
import com.cmor149.contacts.database.Contact;
import com.cmor149.contacts.database.ContactsDbHelper;

/**
 * A fragment representing a single Contact detail screen. This fragment is
 * either contained in a {@link ContactListActivity} in two-pane mode (on
 * tablets) or a {@link ContactDetailActivity} on handsets.
 */
public class ContactDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The Contact the detail fragment will display.
	 */
	private Contact contact;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ContactDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			contact = ContactsDbHelper.getInstance(getActivity()).getContact(getArguments().getLong(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contact_detail,
				container, false);
		
		// TODO: This is a horrible way of implementing this, need to change!
		TextView textView;
		if (contact != null){
			
			if (contact.getMobilePhone() != "") {
				textView = (TextView)rootView.findViewById(R.id.mobile_phone_content);
				textView.setText(contact.getMobilePhone());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.mobile_phone_title);
				textView.setVisibility(View.VISIBLE);
				
			}
			
			if (contact.getHomePhone() != "") {
				textView = (TextView)rootView.findViewById(R.id.home_phone_content);
				textView.setText(contact.getHomePhone());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.home_phone_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			if (contact.getWorkPhone() != "") {
				textView = (TextView)rootView.findViewById(R.id.work_phone_content);
				textView.setText(contact.getWorkPhone());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.work_phone_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			if (contact.getEmailAddress() != "") {
				textView = (TextView)rootView.findViewById(R.id.email_address_content);
				textView.setText(contact.getEmailAddress());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.email_address_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			if (contact.getHomeAddress() != "") {
				textView = (TextView)rootView.findViewById(R.id.home_address_content);
				textView.setText(contact.getHomeAddress());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.home_address_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			if (contact.getDateOfBirth() != "") {
				textView = (TextView)rootView.findViewById(R.id.date_of_birth_content);
				textView.setText(contact.getDateOfBirth());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.date_of_birth_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			
		}
		return rootView;
	}
}
