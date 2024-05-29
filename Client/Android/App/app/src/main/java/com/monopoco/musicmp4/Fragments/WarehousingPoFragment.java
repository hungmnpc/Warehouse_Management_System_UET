package com.monopoco.musicmp4.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.monopoco.musicmp4.R;

import java.util.UUID;

public class WarehousingPoFragment extends Fragment {

    private UUID poId;

    private AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    private TextInputLayout textInputLayout;

    public WarehousingPoFragment(UUID poId) {
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
        View  view = inflater.inflate(R.layout.fragment_warehousing_po, container, false);

        return view;
    }
}