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

import com.monopoco.musicmp4.Fragments.PurchaseOrderDetail;
import com.monopoco.musicmp4.Fragments.ReceiveFragment;
import com.monopoco.musicmp4.Fragments.WarehousingPoFragment;
import com.monopoco.musicmp4.R;

import java.util.Objects;
import java.util.UUID;

public class StockPoAct extends AppCompatActivity {

    private FrameLayout frameLayout;

    private UUID poID;

    private String poCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_po);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
        toolbar.setTitle("Warehousing purchase order");
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        frameLayout = findViewById(R.id.stock_frame);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            poID = (UUID) extras.get("poId");
            poCode = (String) extras.get("poCode");
            System.out.println(poID);
        }
        if (poID != null && poCode != null) {
            setFragment(new ReceiveFragment(poID, poCode));
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