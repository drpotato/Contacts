package com.cmor149.contacts.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

public class ContactSimpleCursorAdapter extends SimpleCursorAdapter {
	
	private static final String TAG  = "Contacts";
	
	private Context context;
	
	// The standard constructor, just passes on values.
	public ContactSimpleCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}
	
	// Gives the activity context to the CursorAdapter, allowing it to access
	// the private internal storage.
	public void setContext(Context context) {
		this.context = context;
	}
	
	// Override setting an image view to load the image from local storage into
	// the view. 
    @Override public void setViewImage(ImageView imageView, String fileName) {
    	
    	Log.d(TAG, "ContactSimpleCursorAdapter - File Name = " + fileName);
    	
    	// If the context hasn't been set, abort.
    	if (context == null) {
    		return;
    	}
    	
    	// Open the file and attach an input stream.
    	File file = new File(context.getFilesDir(), fileName);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		// Read the input stream into a bitmap image and then set the view image.
		Bitmap image = BitmapFactory.decodeStream(fileInputStream);
		imageView.setImageBitmap(image);
    }

}
