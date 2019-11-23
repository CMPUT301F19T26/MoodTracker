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

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moodtracker.R;
import com.example.moodtracker.constants;
import com.example.moodtracker.helpers.FirebaseHelper;
import com.example.moodtracker.helpers.MoodHistoryHelpers;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MoodEvent;
import com.squareup.picasso.Picasso;

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

    private LinearLayout fragment_layout;
    private TextView frag_mood;
    private ImageView mood_emoji;
    private ImageView frag_image;
    private Toolbar toolbar;
    private TextView date;
    private Button delete;
    private Button edit;
    private Button cancel;
    private Button done;
    private EditText reason;

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
        setUpFragmentWithMoodEvent(mMoodEvent, view);
        // Onclick Listeners
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelete(mPosition);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel.setVisibility(View.GONE);
                done.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel.setVisibility(View.GONE);
                done.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public void setUpFragmentWithMoodEvent(MoodEvent e, View v) {
        cancel = v.findViewById(R.id.cancel_edit);
        done = v.findViewById(R.id.done_edit);
        edit = v.findViewById(R.id.edit);
        if (e.getUser_id().equals(FirebaseHelper.getUid())) {
            edit.setVisibility(View.VISIBLE);
        }

        delete = v.findViewById(R.id.delete);
        if (e.getUser_id().equals(FirebaseHelper.getUid())){
            delete.setVisibility(View.VISIBLE);
        }

        // Reason Handler
        reason = v.findViewById(R.id.reason_me);
        if (e.getReason()!= null) {
            reason.setText(e.getReason());
        }

        // Image Handler
        frag_image = v.findViewById(R.id.me_frag_image);
        if (e.getPhoto_url()!= null) {
            Picasso.get().load(e.getPhoto_url()).into(frag_image);
        }
        // Setting the background based on the mood
        Mood selected_mood = constants.mood_num_to_mood_obj_mapper.get(e.getMood());
        fragment_layout = v.findViewById(R.id.fragment_layout);
        fragment_layout.setBackgroundColor(Color.parseColor(selected_mood.getColor()));

        // Handle Mood Related items
        frag_mood = v.findViewById(R.id.frag_mood);
        frag_mood.setText(selected_mood.getMoodName());
        mood_emoji = v.findViewById(R.id.emoji_view);
        mood_emoji.setImageResource(selected_mood.getIcon());

        // Handle the Date
        String formatted_date = MoodHistoryHelpers.formatDate(e.getDate());
        date = v.findViewById(R.id.date);
        date.setText(formatted_date);

        // Set up the Toolbar
        toolbar = v.findViewById(R.id.mood_event_view_tb);
        toolbar.setBackgroundColor(Color.parseColor(selected_mood.getColor()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onDelete(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
            getActivity().onBackPressed();
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
        void onFragmentInteraction(int position);
    }
}
