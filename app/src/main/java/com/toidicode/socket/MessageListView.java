package com.toidicode.socket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;
import java.util.zip.Inflater;

public class MessageListView extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List list;
    private Context context;

    public MessageListView(List list, Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListViewLayout ListViewLayout;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.list_message, viewGroup, false);

            ListViewLayout = new ListViewLayout();
            ListViewLayout.message = (TextView) view.findViewById(R.id.message);

            view.setTag(ListViewLayout);
        } else {
            ListViewLayout = (ListViewLayout) view.getTag();
        }

        Message message = (Message) list.get(i);
        ListViewLayout.message.setText(String.valueOf(message.getMessage()));

        if (message.getMe() == true) {
            ListViewLayout.message.setGravity(Gravity.RIGHT);
            ListViewLayout.message.setBackgroundResource(R.color.colorPrimary);
            ListViewLayout.message.setTextColor(Color.WHITE);
        } else {
            ListViewLayout.message.setGravity(Gravity.LEFT);
        }
        return view;
    }

    public class ListViewLayout {
        TextView message;
    }


    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
