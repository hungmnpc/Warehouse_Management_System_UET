package com.monopoco.musicmp4.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.monopoco.musicmp4.Activities.PurchaseOrderDetailAct;
import com.monopoco.musicmp4.Models.PurchaseOrderDetail;
import com.monopoco.musicmp4.Models.PurchaseOrderModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderDetailAdapter extends ArrayAdapter<PurchaseOrderDetail> {

    public PurchaseOrderDetailAdapter(@NonNull Context context, List<PurchaseOrderDetail> purchaseOrderDetails) {
        super(context, R.layout.purchase_order_detail_item ,purchaseOrderDetails);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PurchaseOrderDetail purchaseOrderDetail = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.purchase_order_detail_item, parent, false);
        }

        TextView productName = convertView.findViewById(R.id.productName);
        TextView stocked = convertView.findViewById(R.id.stocked);
        productName.setText(purchaseOrderDetail.getProduct().getProductName());
        stocked.setText(String.format("%d/%d", purchaseOrderDetail.getStockedQuantity() == null ? 0 : purchaseOrderDetail.getStockedQuantity(),
                purchaseOrderDetail.getQuantity()));
        ImageButton barcodeBtn = convertView.findViewById(R.id.btn_barcode);
        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Barcode");
            }
        });

        return convertView;
    }


}
