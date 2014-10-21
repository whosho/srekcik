package com.performgroup.innovation.kickers.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.performgroup.innovation.kickers.R;

public class PlayerButtonBottom extends PlayerButtonBase {


    public PlayerButtonBottom(Context context) {
        super(context);
        initView(context);
    }

    public PlayerButtonBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PlayerButtonBottom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.player_control_bottom, this, true);
        initButtons(view);
    }


}
