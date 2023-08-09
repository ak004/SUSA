package com.example.susa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Adapter.CourseAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView cat_rec, course_rec, mentor_rec;
    CatagoriesAdapter catagoriesAdapter;
    SharedPreferencesData sharedPreferencesData;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    CourseAdapter courseAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesData = SharedPreferencesData.getInstance(getContext());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cat_rec = view.findViewById(R.id.cat_rec);
        course_rec = view.findViewById(R.id.course_rec);
        mentor_rec = view.findViewById(R.id.mentor_rec);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        course_rec.setLayoutManager(layoutManager2);
        cat_rec.setLayoutManager(layoutManager);
        mentor_rec.setLayoutManager(layoutManager3);

        getCatagories(sharedPreferencesData.getUSER_id());
            JsonArray ja = new JsonArray();
        mentor_rec.setAdapter(catagoriesAdapter);


        getCourses(sharedPreferencesData.getUSER_id());
    }


    private void getCatagories(String user_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        Call<JsonObjectModalResponse> call = apiInterface.get_catagories(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("theresss", "THe res pnms is: "+ response.body().getRecord());
                    if(response.body().isSuccess()) {
                        JsonArray ja;
                        ja = response.body().getRecord().get("data").getAsJsonArray();
                        catagoriesAdapter = new CatagoriesAdapter(ja,getContext());
                        cat_rec.setAdapter(catagoriesAdapter);
                    }else {
                        Toast.makeText(getContext(), "THERE IS NO CATEGORIES", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            //check something
            @Override
            public void onFailure(Call<JsonObjectModalResponse> call, Throwable t) {
                Log.d("sliding_category", t.getMessage());
            }
        });
    }


    private void getCourses(String user_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",user_id);
        Call<JsonObjectModalResponse> call = apiInterface.getCourses(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().isSuccess()) {
                        JsonArray ja;
                        ja = response.body().getRecord().get("data").getAsJsonArray();
                        courseAdapter = new CourseAdapter(ja,getContext());
                        course_rec.setAdapter(courseAdapter);
                    }else {
                        Toast.makeText(getContext(), "THERE IS NO COURSE", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            //check something
            @Override
            public void onFailure(Call<JsonObjectModalResponse> call, Throwable t) {
                Log.d("sliding_category", t.getMessage());
            }
        });
    }


}