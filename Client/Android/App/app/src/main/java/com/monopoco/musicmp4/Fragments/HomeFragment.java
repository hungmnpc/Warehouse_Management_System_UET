package com.monopoco.musicmp4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monopoco.musicmp4.Activities.PickActivity;
import com.monopoco.musicmp4.Activities.PurchaseOrderActivity;
import com.monopoco.musicmp4.Models.PlayListModel;
import com.monopoco.musicmp4.Models.ResponseCommon;
import com.monopoco.musicmp4.Models.SongModel;
import com.monopoco.musicmp4.Models.WarehouseModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;
import com.monopoco.musicmp4.Utils.ImageUtils;
import com.monopoco.musicmp4.Utils.JWTUtils;
import com.monopoco.musicmp4.Utils.UserPrinciple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private FrameLayout frameLayout;

    private CardView btnReceive;

    private CardView btnDeliver;

    private CardView btnTransfer;

    private TextView username;

    private TextView warehouseName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnReceive = view.findViewById(R.id.btn_to_receive);

        frameLayout = getActivity().findViewById(R.id.main_frame_layout);

        btnDeliver = view.findViewById(R.id.btn_to_deliver);

        btnTransfer = view.findViewById(R.id.btn_to_transfer);

        username = view.findViewById(R.id.username);

        warehouseName = view.findViewById(R.id.warehouseName);

        SharedPreferences sp1= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String accessToken = String.valueOf(sp1.getString("accessToken", null));
        if (accessToken != null) {
            UserPrinciple userPrinciple = JWTUtils.getCurrentUser(accessToken);
            username.setText(String.format("Hello, %s", userPrinciple.getUsername()));
            getWarehouseInfo(userPrinciple.getWarehouseId(), accessToken);
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Deliver");
                Intent myIntent = new Intent(getContext(), PickActivity.class);
                getContext().startActivity(myIntent);
            }
        });
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Receive");
                Intent myIntent = new Intent(getContext(), PurchaseOrderActivity.class);
                getContext().startActivity(myIntent);
//                setFragment(new ReceiveFragment())  ;
            }
        });
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Transfer");
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.out_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void getWarehouseInfo(UUID warehouseId, String accesstoken) {
        APIService.getService(accesstoken).getWarehouseInfo(warehouseId).enqueue(new Callback<ResponseCommon<WarehouseModel>>() {
            @Override
            public void onResponse(Call<ResponseCommon<WarehouseModel>> call, Response<ResponseCommon<WarehouseModel>> response) {
                if (response.isSuccessful()) {
                    WarehouseModel warehouseModel = response.body().getData();
                    warehouseName.setText(String.format("Warehouse: %s", warehouseModel.getWarehouseName()));
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon<WarehouseModel>> call, Throwable t) {
                System.out.println("error");
            }
        });
    }
}