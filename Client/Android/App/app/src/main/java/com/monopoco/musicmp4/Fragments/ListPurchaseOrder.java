package com.monopoco.musicmp4.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.monopoco.musicmp4.Adapters.PurchaseOrderAdapter;
import com.monopoco.musicmp4.Models.PageResponse;
import com.monopoco.musicmp4.Models.PurchaseOrderModel;
import com.monopoco.musicmp4.Models.ResponseCommon;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListPurchaseOrder extends Fragment {

    private PurchaseOrderAdapter purchaseOrderAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_purchase_order, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        listView = view.findViewById(R.id.po_list);
        getAllPo();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllPo();
            }
        });
        return view;
    }

    private void getAllPo() {
        ArrayList<PurchaseOrderModel> purchaseOrderModels = new ArrayList<>();
        SharedPreferences sp1= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String accessToken = String.valueOf(sp1.getString("accessToken", null));

        APIService.getService(accessToken).getPOEmployee().enqueue(new Callback<ResponseCommon<PageResponse<List<PurchaseOrderModel>>>>() {
            @Override
            public void onResponse(Call<ResponseCommon<PageResponse<List<PurchaseOrderModel>>>> call, Response<ResponseCommon<PageResponse<List<PurchaseOrderModel>>>> response) {
                System.out.println(response.body().getData().getData());
                purchaseOrderModels.addAll(response.body().getData().getData());
                purchaseOrderAdapter = new PurchaseOrderAdapter(getContext(), purchaseOrderModels);
                if (listView != null) {
                    listView.setAdapter(purchaseOrderAdapter);
                }

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<ResponseCommon<PageResponse<List<PurchaseOrderModel>>>> call, Throwable t) {
                System.out.println("error");

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}