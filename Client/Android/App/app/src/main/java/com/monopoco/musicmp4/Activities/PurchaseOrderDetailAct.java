package com.monopoco.musicmp4.Activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.monopoco.musicmp4.Fragments.ListPurchaseOrder;
import com.monopoco.musicmp4.Fragments.PurchaseOrderDetail;
import com.monopoco.musicmp4.R;

import java.util.Objects;
import java.util.UUID;

public class PurchaseOrderDetailAct extends AppCompatActivity {

    private FrameLayout frameLayout;

    private UUID poID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_detail);


        Toolbar toolbar = findViewById(R.id.toolbar_po_detail);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
        toolbar.setTitle("Purchase orders detail");
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        frameLayout = findViewById(R.id.po_detail_frame_layout);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            poID = (UUID) extras.get("poId");
            //The key argument here must match that used in the other activity
            System.out.println(poID);
        }
        if (poID != null) {
            setFragment(new PurchaseOrderDetail(poID));
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}