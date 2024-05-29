package com.monopoco.musicmp4.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.monopoco.musicmp4.Activities.CaptureAct;
import com.monopoco.musicmp4.Models.LoadProductIntoBin;
import com.monopoco.musicmp4.Models.PickProductFromBin;
import com.monopoco.musicmp4.Models.ResponseCommon;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import org.json.JSONObject;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryFragment extends Fragment {


    private FrameLayout frameLayout;

    private EditText binBarcode;

    private EditText productBarcode;

    private EditText currentEditText;

    private EditText quantity;

    private Button pickedBtn;

    private LinearLayout loading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);
        binBarcode = view.findViewById(R.id.bin_barcode);
        productBarcode = view.findViewById(R.id.product_code);
        pickedBtn = view.findViewById(R.id.btn_picked);
        quantity = view.findViewById(R.id.quantity);
        loading = view.findViewById(R.id.loading);
        frameLayout = getActivity().findViewById(R.id.main_frame_layout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        binBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInPuts();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        productBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInPuts();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInPuts();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binBarcode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (binBarcode.getRight() - binBarcode.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) - binBarcode.getPaddingRight()) {
                        // your action here
                        currentEditText = binBarcode;
                        scanCode();
                        return true;
                    }
                }
                return false;
            }
        });

        productBarcode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (productBarcode.getRight() - productBarcode.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) - productBarcode.getPaddingRight()) {
                        // your action here
                        currentEditText = productBarcode;
                        scanCode();
                        return true;
                    }
                }
                return false;
            }
        });

        pickedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                onPicked();
                pickedBtn.setEnabled(false);

            }
        });
    }

    private void onPicked() {

        PickProductFromBin value = new PickProductFromBin(
                binBarcode.getText().toString(),
                productBarcode.getText().toString(),
                Integer.valueOf(quantity.getText().toString())
        );
        SharedPreferences sp1= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String accessToken = String.valueOf(sp1.getString("accessToken", null));
        APIService.getService(accessToken).pickProductFromBin(value).enqueue(new Callback<ResponseCommon<String>>() {
            @Override
            public void onResponse(Call<ResponseCommon<String>> call, Response<ResponseCommon<String>> response) {
                System.out.println(response.errorBody());
                if (response.code() == 200) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Picked product successfully. Continue pick ?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    binBarcode.setText("");
                                    productBarcode.setText("");
                                    quantity.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getActivity().onBackPressed();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                    Toast.makeText(getActivity(), response.body().getData(),
                            Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                    if(!response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.errorBody().string());
                            String message = String.valueOf(jsonObject.getJSONObject("result").get("message"));
                            Toast.makeText(getContext(), message,
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseCommon<String>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error service", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void checkInPuts() {
        if (!binBarcode.getText().toString().isEmpty()) {
            if (!productBarcode.getText().toString().isEmpty()) {
                if (!quantity.getText().toString().isEmpty() && !quantity.getText().toString().equals("0")) {
                    pickedBtn.setEnabled(true);
                } else {
                    pickedBtn.setEnabled(false);
                }
            } else {
                pickedBtn.setEnabled(false);
            }
        } else {
            pickedBtn.setEnabled(false);
        }
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(
            new ScanContract(), result -> {
                if (result.getContents() != null && currentEditText != null) {
                    System.out.println(result.getContents());
                    currentEditText.setText(result.getContents());
                }
            }
    );

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.out_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}