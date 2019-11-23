/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moodtracker.R;
import com.example.moodtracker.constants;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoodEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoodEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoodEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOOD_EVENT = "mood_event";
    private static final String POSITION = "position";

    private RelativeLayout fragment_layout;
    private TextView frag_mood;
    private ImageView mood_emoji;

    // TODO: Rename and change types of parameters
    private MoodEvent mMoodEvent;
    private int mPosition;

    private OnFragmentInteractionListener mListener;

    public MoodEventFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MoodEventFragment newInstance(MoodEvent moodEvent, int position) {
        MoodEventFragment fragment = new MoodEventFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOOD_EVENT, moodEvent);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMoodEvent = getArguments().getParcelable(MOOD_EVENT);
            mPosition = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mood_event, container, false);
//        // Gives us access to the frontend of the fragment now

        // Setting the background based on the mood
        Mood selected_mood = constants.mood_num_to_mood_obj_mapper.get(mMoodEvent.getMood());
        fragment_layout = view.findViewById(R.id.fragment_layout);
        fragment_layout.setBackgroundColor(Color.parseColor(selected_mood.getColor()));

        frag_mood = view.findViewById(R.id.frag_mood);
        frag_mood.setText(selected_mood.getMoodName());

        mood_emoji = view.findViewById(R.id.emoji_view);
        mood_emoji.setImageResource(selected_mood.getIcon());



        // Todo: Set any onclick listeners in here as well
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
