package com.performgroup.innovation.kickers.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;

import javax.inject.Inject;

public class CreatePlayerDialog extends DialogFragment {

    private View view;
    private EditText playerName;
    @Inject
    GameAPI gameAPI;

    public CreatePlayerDialog() {
        // should be empty

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_player, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        KickersApplication.inject(this);
        return view;
    }

    private void initView() {
        playerName = (EditText) view.findViewById(R.id.et_player_name);

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(playerName, InputMethodManager.SHOW_FORCED);

        view.findViewById(R.id.b_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = playerName.getText().toString();
                if (name.isEmpty()) {
                    //do nothing
                } else {
                    saveNewPlayer(name);
                    dismiss();
                }
            }
        });
    }

    private void saveNewPlayer(String name) {
        gameAPI.createPlayer(name);
    }

}
