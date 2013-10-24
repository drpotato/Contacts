package com.cmor149.contacts.detail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
	
	private static final String TAG = "Contacts"; 
	
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
		
		Log.d(TAG, "ContactDetailFragment: onCreate started");
		
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			
			Log.d(TAG, "ContactDetailFragment: Getting contact where id = " + getArguments().getLong(ARG_ITEM_ID));
			
			// Load the contact from the database, passing on the id number provided by the activity.
			contact = ContactsDbHelper.getInstance(getActivity()).getContact(getArguments().getLong(ARG_ITEM_ID));
		} else {
			Log.d(TAG, "ContactDetailFragment: WARNING!!! Missing argument - " + ARG_ITEM_ID);
		}
		
		// Set the action bar title to be the contact's full name. Adds a nice
		// aesthetic.
		getActivity().setTitle(contact.getFullName());
		
		Log.d(TAG, "ContactDetailFragment: onCreate finished");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contact_detail,
				container, false);
		
		// TODO: This is a horrible way of implementing this, need to change!
		TextView textView;
		if (contact != null){
			
			Log.d(TAG, "ContactDetailFragment: Mobile Phone" + contact.getMobilePhone() );
			
			// Only views corresponding to existing data is made visible.
			if (!contact.getMobilePhone().isEmpty()) {
				textView = (TextView)rootView.findViewById(R.id.mobile_phone_content);
				textView.setText(contact.getMobilePhone());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.mobile_phone_title);
				textView.setVisibility(View.VISIBLE);	
			}
			
			Log.d(TAG, "ContactDetailFragment: Home Phone" + contact.getHomePhone() );
			
			if (!contact.getHomePhone().isEmpty()) {
				textView = (TextView)rootView.findViewById(R.id.home_phone_content);
				textView.setText(contact.getHomePhone());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.home_phone_title);
				textView.setVisibility(View.VISIBLE);				
			}
			
			Log.d(TAG, "ContactDetailFragment: Work Phone" + contact.getWorkPhone() );
			
			if (!contact.getWorkPhone().isEmpty()) {
				textView = (TextView)rootView.findViewById(R.id.work_phone_content);
				textView.setText(contact.getWorkPhone());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.work_phone_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			Log.d(TAG, "ContactDetailFragment: Email Address" + contact.getEmailAddress() );
			
			if (!contact.getEmailAddress().isEmpty()) {
				textView = (TextView)rootView.findViewById(R.id.email_address_content);
				textView.setText(contact.getEmailAddress());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.email_address_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			Log.d(TAG, "ContactDetailFragment: Home Address" + contact.getHomeAddress() );
			
			if (!contact.getHomeAddress().isEmpty()) {
				textView = (TextView)rootView.findViewById(R.id.home_address_content);
				textView.setText(contact.getHomeAddress());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.home_address_title);
				textView.setVisibility(View.VISIBLE);
			}
			
			Log.d(TAG, "ContactDetailFragment: Date of Birth" + contact.getDateOfBirth() );
			
			if (!contact.getDateOfBirth().isEmpty()) {
				textView = (TextView)rootView.findViewById(R.id.date_of_birth_content);
				textView.setText(contact.getDateOfBirth());
				textView.setVisibility(View.VISIBLE);
				textView = (TextView)rootView.findViewById(R.id.date_of_birth_title);
				textView.setVisibility(View.VISIBLE);
			}
			// Pass the rootView to the image reader.
			readImage(rootView);
			
		}
		return rootView;
	}
	
	
	/**
	 * Reads the image file associated with the contacts fileName into the
	 * contact_image ImageView of the provided view.
	 * @param view
	 */
	private void readImage(View view) {
		
		//Get the fileName from the contact. If its blank or null, abort.
		String fileName = contact.getPhotoUri();
		if (fileName == null || fileName.isEmpty()) {
			return;
		}
		
		// Open the file and create an input stream to it.
		File file = new File(this.getActivity().getFilesDir(), fileName);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		//Read the file in through the stream and set the ImageView bitmap.
		Bitmap image = BitmapFactory.decodeStream(fileInputStream);
		((ImageView)view.findViewById(R.id.contact_image)).setImageBitmap(image);
	}
}
