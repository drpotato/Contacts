package com.cmor149.contacts;

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

	public ContactSimpleCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
    @Override public void setViewImage(ImageView imageView, String fileName) {
    	
    	Log.d(TAG, "ContactSimpleCursorAdapter - File Name = " + fileName);
    	
    	if (context == null) {
    		return;
    	}
    	File file = new File(context.getFilesDir(), fileName);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		Bitmap image = BitmapFactory.decodeStream(fileInputStream);
		imageView.setImageBitmap(image);
    }

}
