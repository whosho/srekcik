package com.performgroup.innovation.kickers.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.performgroup.innovation.kickers.R;

public class CreatePlayerDialog extends DialogFragment {

    private View view;
    private EditText playerName;

    public CreatePlayerDialog() {
        // should be empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_player, container);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        initView();

        return view;
    }

    private void initView() {
        playerName = (EditText) view.findViewById(R.id.et_player_name);

        view.findViewById(R.id.b_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewPlayer();
            }
        });
    }

    private void saveNewPlayer() {
        String name = playerName.getText().toString();

        // post some event or sth here to save to DB
    }

}
