package com.monopoco.musicmp4.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.monopoco.musicmp4.Adapters.PurchaseOrderAdapter;
import com.monopoco.musicmp4.Adapters.PurchaseOrderDetailAdapter;
import com.monopoco.musicmp4.Models.PageResponse;
import com.monopoco.musicmp4.Models.PurchaseOrderModel;
import com.monopoco.musicmp4.Models.ResponseCommon;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseOrderDetail extends Fragment {


    private UUID poId;

    private TextView poCode;

    private TextView referenceNum;

    private TextView supplier;

    private TextView deadline;

    private PurchaseOrderModel purchaseOrderModel;

    private ListView detailList;

    private SwipeRefreshLayout swipeRefreshLayout;

    private PurchaseOrderDetailAdapter purchaseOrderDetailAdapter;
    private List<com.monopoco.musicmp4.Models.PurchaseOrderDetail> purchaseOrderDetails;

    public PurchaseOrderDetail(UUID poId) {
        this.poId = poId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_order_detail, container, false);
        poCode = view.findViewById(R.id.po_detail_poCode);
        referenceNum = view.findViewById(R.id.txt_po_ref);
        supplier = view.findViewById(R.id.txt_supplier);
        deadline = view.findViewById(R.id.txt_dealine);
        detailList = view.findViewById(R.id.detail_list);

        getPoDetail();
        return view;
    }

    private void getPoDetail() {
        if (poId != null) {
            SharedPreferences sp1= getActivity().   getSharedPreferences("Login", Context.MODE_PRIVATE);
            String accessToken = String.valueOf(sp1.getString("accessToken", null));
            APIService.getService(accessToken).getPoById(poId).enqueue(new Callback<ResponseCommon<PurchaseOrderModel>>() {
                @Override
                public void onResponse(@NonNull Call<ResponseCommon<PurchaseOrderModel>> call, @NonNull Response<ResponseCommon<PurchaseOrderModel>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        purchaseOrderModel = response.body().getData();
                        poCode.setText(purchaseOrderModel.getPoCode());
                        referenceNum.setText(purchaseOrderModel.getReferenceNumber());
                        supplier.setText(purchaseOrderModel.getSupplierName());
                        deadline.setText(purchaseOrderModel.getDeadLineToStock() == null ? "No info" : purchaseOrderModel.getDeadLineToStock());
                        APIService.getService(accessToken).getProductInPo(poId).enqueue(new Callback<ResponseCommon<PageResponse<List<com.monopoco.musicmp4.Models.PurchaseOrderDetail>>>>() {
                            @Override
                            public void onResponse(Call<ResponseCommon<PageResponse<List<com.monopoco.musicmp4.Models.PurchaseOrderDetail>>>> call, Response<ResponseCommon<PageResponse<List<com.monopoco.musicmp4.Models.PurchaseOrderDetail>>>> response) {
                                System.out.println(response);
                                if (response.isSuccessful()) {
                                    assert response.body() != null;
                                    purchaseOrderDetails = response.body().getData().getData();
                                    if (purchaseOrderDetails != null && detailList != null) {
                                        purchaseOrderDetailAdapter = new PurchaseOrderDetailAdapter(getContext(), purchaseOrderDetails);
                                        detailList.setAdapter(purchaseOrderDetailAdapter);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseCommon<PageResponse<List<com.monopoco.musicmp4.Models.PurchaseOrderDetail>>>> call, Throwable t) {
                                System.out.println("error");
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call<ResponseCommon<PurchaseOrderModel>> call, Throwable t) {
                    t.printStackTrace();
                    System.out.println("error");
                }
            });
        }
    }

}