package com.example.susa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.susa.Adapter.CatagoriesAdapter;
import com.example.susa.Web_service.ApiClient;
import com.example.susa.Web_service.ApiInterface;
import com.example.susa.models.JsonObjectModalResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    SharedPreferencesData sharedPreferencesData;

    private LinearLayout qr_code_linear,logout_btn, contactus_btn,aboutus_btn,paymentinfo_btn;
    private TextView username_txt, email_txt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        qr_code_linear = view.findViewById(R.id.qr_code_linear);
        logout_btn = view.findViewById(R.id.logout_btn);
        contactus_btn = view.findViewById(R.id.contactus_btn);
        aboutus_btn = view.findViewById(R.id.aboutus_btn);
        paymentinfo_btn = view.findViewById(R.id.paymentinfo_btn);
        username_txt = view.findViewById(R.id.username_txt);
        email_txt = view.findViewById(R.id.email_txt);

        username_txt.setText(sharedPreferencesData.getUsername());
        email_txt.setText(sharedPreferencesData.getUserEmail());
        qr_code_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanCode();
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesData.putuser_id("");
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });


        contactus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ContactUsActivity.class);
                startActivity(intent);
            }
        });

        aboutus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AboutUSActivity.class);
                startActivity(intent);
            }
        });

        paymentinfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PaymentReportActivity.class);
                startActivity(intent);
            }
        });


    }

    private void  ScanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLanucher.launch(options);
    }


    ActivityResultLauncher<ScanOptions> barLanucher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Are You sure you want to login into this website");
//            builder.setMessage(result.getContents());
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Login_to_website(result.getContents());
                    dialog.dismiss();
                }
            }).show();
        }
    });



    private void  Login_to_website(String result) {
        String[] parts = result.split("-number:");
        String session_id = "";
        String num = "";

        if (parts.length == 2) {
            session_id = parts[0];
            num = parts[1];
        } else {
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",sharedPreferencesData.getUSER_id());
        jsonObject.addProperty("session_id",session_id);
        jsonObject.addProperty("num",num);
        Call<JsonObjectModalResponse> call = apiInterface.cross_login(jsonObject);
        call.enqueue(new Callback<JsonObjectModalResponse>() {
            @Override
            public void onResponse(Call<JsonObjectModalResponse> call, Response<JsonObjectModalResponse> response) {
                if (response.isSuccessful()) {
                        if(response.body().isSuccess()) {
                            Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Couldn't Login Session has expired refresh and try again", Toast.LENGTH_SHORT).show();

                        }
                }else {
                    Toast.makeText(getContext(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
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