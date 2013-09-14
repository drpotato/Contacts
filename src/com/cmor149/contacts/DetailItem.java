package com.cmor149.contacts;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link DetailItem.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link DetailItem#newInstance} factory method to
 * create an instance of this fragment.
 * 
 */
public class DetailItem extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_DETAIL_TITLE = "detail_title";
	private static final String ARG_DETAIL_CONTENT = "detail_content";

	// TODO: Rename and change types of parameters
	private String detailTitle;
	private String detailContent;

	private OnFragmentInteractionListener mListener;

	/**
	 * Creates a new DetailItem fragment.
	 * 
	 * @param detailTitle
	 *            Title of the item.
	 * @param detailContent
	 *            Content of the item.
	 * @return A new instance of fragment DetailItem.
	 */
	public static DetailItem newInstance(String detailTitle, String detailContent) {
		DetailItem fragment = new DetailItem();
		Bundle args = new Bundle();
		args.putString(ARG_DETAIL_TITLE, detailTitle);
		args.putString(ARG_DETAIL_CONTENT, detailContent);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Required empty public constructor
	 */
	public DetailItem() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			detailTitle = getArguments().getString(ARG_DETAIL_TITLE);
			detailContent = getArguments().getString(ARG_DETAIL_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater
				.inflate(R.layout.fragment_detail_item, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
