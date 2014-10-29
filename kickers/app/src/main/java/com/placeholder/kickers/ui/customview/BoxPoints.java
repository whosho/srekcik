package com.placeholder.kickers.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.placeholder.kickers.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoxPoints extends ListView {

    private static final int DEFAULT_MAX_POINTS = 8;

    private Context context;
    private AttributeSet attrs;
    private BoxPointAdapter adapter;

    public BoxPoints(Context context) {
        super(context);
        initView(context);
    }

    public BoxPoints(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        initView(context);
    }

    public BoxPoints(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BoxPoints);

        int teamValue = 0;
        boolean isInverted = false;

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.BoxPoints_teamColor:
                    teamValue = typedArray.getInt(attr, 0);
                    break;
                case R.styleable.BoxPoints_inverted:
                    isInverted = typedArray.getBoolean(attr, false);
                    break;
            }
        }
        typedArray.recycle();

        int itemDrawableId = (teamValue == 0 ? R.drawable.blue_point_box : R.drawable.red_point_box);

        adapter = new BoxPointAdapter(DEFAULT_MAX_POINTS, itemDrawableId, isInverted);
        setAdapter(adapter);
    }

    public void setMaxPoints(int maxPoints) {
        adapter.setMaxPoints(maxPoints);
    }

    public void setPoints(int goals) {
        adapter.setPoints(goals);
    }


    private class BoxPointAdapter extends BaseAdapter {

        private static final int EMPTY_ITEMS = 3;

        private List<Integer> items;
        private int itemDrawableId;
        private boolean isInverted;

        public BoxPointAdapter(int maxPoints, int itemDrawableId, boolean isInverted) {
            this.itemDrawableId = itemDrawableId;
            this.isInverted = isInverted;
            initItems(maxPoints);
        }

        private void initItems(int maxPoints) {
            int size = maxPoints + EMPTY_ITEMS;

            items = new ArrayList<Integer>(size);
            for (int i = 0; i < size; i++) {
                items.add(0);
            }
            setPoints(0);
        }

        public void setMaxPoints(int maxPoints) {
            initItems(maxPoints);
        }

        public void setPoints(int points) {

            for (int i = 0; i < items.size(); i++) {
                boolean isEmptyItem = ((points <= i) && (i < points + EMPTY_ITEMS));

                int visibility = (isEmptyItem ? View.GONE : View.VISIBLE);
                items.set(i, visibility);
            }

            if (isInverted) {
                Collections.reverse(items);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return (items == null ? 0 : items.size());
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.box_point_item, null);

                Drawable drawable = getResources().getDrawable(itemDrawableId);
                ((ImageView) convertView.findViewById(R.id.iv_box_point)).setImageDrawable(drawable);
            }

            int visibility = items.get(position);
            //noinspection ResourceType
            convertView.setVisibility(visibility);

            return convertView;
        }

    }

}
