package com.example.susa;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.susa.Adapter.OngoingAndCompletedAdapter;
import com.google.gson.JsonArray;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OngoingAndCompletedAdapter OngoingAndCompletedAdapter;
    RecyclerView ongoing_course_rec, completed_course_rec;
    LinearLayout no_ongoing_found,no_completedfound;
    CardView ongoing_card,completed_card;
    TextView completed_txt,ongoing_txt;



    public LearnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearnFragment newInstance(String param1, String param2) {
        LearnFragment fragment = new LearnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ongoing_course_rec =  view.findViewById(R.id.ongoing_course_rec);
        completed_course_rec = view.findViewById(R.id.completed_course_rec);
        no_ongoing_found = view.findViewById(R.id.no_ongoing_found);
        no_completedfound = view.findViewById(R.id.no_completedfound);
        ongoing_card = view.findViewById(R.id.ongoing_card);
        completed_card = view.findViewById(R.id.completed_card);
        completed_txt = view.findViewById(R.id.completed_txt);
        ongoing_txt = view.findViewById(R.id.ongoing_txt);

        completed_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                ColorStateList colorStateList2 = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.grey_white));
                ColorStateList colorStateList3 = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.black));
                ColorStateList colorStateList4 = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));


                ongoing_card.setBackgroundTintList(colorStateList2);
                completed_card.setBackgroundTintList(colorStateList);
                completed_txt.setTextColor(colorStateList4);
                ongoing_txt.setTextColor(colorStateList3);

                ongoing_course_rec.setVisibility(View.GONE);
                completed_course_rec.setVisibility(View.VISIBLE);

            }
        });

        ongoing_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                ColorStateList colorStateList2 = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.grey_white));

                ColorStateList colorStateList3 = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.black));
                ColorStateList colorStateList4 = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white));
                ongoing_card.setBackgroundTintList(colorStateList);
                completed_card.setBackgroundTintList(colorStateList2);

                completed_txt.setTextColor(colorStateList3);
                ongoing_txt.setTextColor(colorStateList4);

                ongoing_course_rec.setVisibility(View.VISIBLE);
                completed_course_rec.setVisibility(View.GONE);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        ongoing_course_rec.setLayoutManager(layoutManager);
        completed_course_rec.setLayoutManager(layoutManager2);

        JsonArray ja = new JsonArray();
        OngoingAndCompletedAdapter = new OngoingAndCompletedAdapter(ja, getContext());
        ongoing_course_rec.setAdapter(OngoingAndCompletedAdapter);
        completed_course_rec.setAdapter(OngoingAndCompletedAdapter);


    }
}