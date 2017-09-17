package com.htetznaing.zguni;


import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZgtoUni extends Fragment implements View.OnClickListener {
    ClipboardManager copy;
    EditText edInput, edOutput;
    Button btnPaste,btnClear,btnCopy,btnCopyBoth;
    Typeface zg,uni;
    AdView B;
    AdRequest adRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zgto_uni, container, false);
        copy = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        zg = Typeface.createFromAsset(getActivity().getAssets(),"zg.ttf");
        uni = Typeface.createFromAsset(getActivity().getAssets(),"uni.ttf");

        adRequest = new AdRequest.Builder().build();
        B = (AdView) view.findViewById(R.id.adView);
        B.loadAd(adRequest);

        btnPaste = (Button) view.findViewById(R.id.btnPaset);
        btnClear = (Button) view.findViewById(R.id.btnClear);
        btnCopy = (Button) view.findViewById(R.id.btnCopy);
        btnCopyBoth = (Button) view.findViewById(R.id.btnCopyBoth);

        btnPaste.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        btnCopyBoth.setOnClickListener(this);

        edInput = (EditText) view.findViewById(R.id.edInput);
        edOutput = (EditText) view.findViewById(R.id.edOutput);
        edInput.setTypeface(zg);
        edOutput.setTypeface(uni);
        edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edOutput.setText(new Yone().zg2uni(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPaset:
                edInput.setText("");
                edInput.setText(copy.getText());
                break;
            case R.id.btnClear:
                edInput.setText("");
                break;
            case R.id.btnCopy:
                copy.setText(edOutput.getText());
                if (copy.hasText()){
                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCopyBoth:
                copy.setText("<Zawgyi/>\n"+edInput.getText()+"\n\n<Unicode/>\n"+edOutput.getText());
                if (copy.hasText()){
                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
