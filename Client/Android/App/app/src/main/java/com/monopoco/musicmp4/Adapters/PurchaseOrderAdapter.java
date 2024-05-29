package com.monopoco.musicmp4.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.monopoco.musicmp4.Activities.PurchaseOrderDetailAct;
import com.monopoco.musicmp4.Activities.StockPoAct;
import com.monopoco.musicmp4.Models.PurchaseOrderModel;
import com.monopoco.musicmp4.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderAdapter extends ArrayAdapter<PurchaseOrderModel> {



    public PurchaseOrderAdapter(@NonNull Context context, ArrayList<PurchaseOrderModel> purchaseOrderModels) {
        super(context, R.layout.purchase_order_item ,purchaseOrderModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PurchaseOrderModel purchaseOrderModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.purchase_order_item, parent, false);
        }

        TextView poCode = convertView.findViewById(R.id.poCode);
        TextView deadline = convertView.findViewById(R.id.deadline);
        poCode.setText(purchaseOrderModel.getPoCode());
        deadline.setText("Deadline: " + (purchaseOrderModel.getDeadLineToStock() == null ? "" : purchaseOrderModel.getDeadLineToStock()));
        ImageButton startBtn = convertView.findViewById(R.id.btn_start);
        ImageButton detailBtn = convertView.findViewById(R.id.btn_detail);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Detail");
                Intent detailIntent = new Intent(getContext(), PurchaseOrderDetailAct.class);
                detailIntent.putExtra("poId", purchaseOrderModel.getId());
                getContext().startActivity(detailIntent);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Start");
                Intent sockPOIntent = new Intent(getContext(), StockPoAct.class);
                sockPOIntent.putExtra("poId", purchaseOrderModel.getId());
                sockPOIntent.putExtra("poCode", purchaseOrderModel.getPoCode());
                getContext().startActivity(sockPOIntent);
            }
        });
        return convertView;
    }
}
