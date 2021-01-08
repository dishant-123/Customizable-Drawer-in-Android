package com.example.learnrevisetwo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyExListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<String> langs;
    private Map<String, List<String>> topics;
    public MyExListAdapter(Context context, List<String> langs, Map<String, List<String>> topics) {
        this.context = context;
        this.langs = langs;
        this.topics = topics;
    }

    @Override
    public int getGroupCount() {
        return langs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return topics.get(langs.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return langs.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return topics.get(langs.get(groupPosition)).get(childPosition);
    }

    public void setData(List<String> langs, Map<String, List<String>> topics){
        this.topics = topics;
        this.langs = langs;
        notifyDataSetChanged();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_parent, parent, false);
        }
        TextView txt = convertView.findViewById(R.id.txt);
        ImageView crossImage = convertView.findViewById(R.id.crossImage);
        crossImage.setOnClickListener(v -> {
            this.langs.remove(groupPosition);
            this.topics.remove(groupPosition);
            notifyDataSetChanged();
        });
        ImageView addImage = convertView.findViewById(R.id.addImage);
        addImage.setOnClickListener(v -> {
            showAlertDialog(groupPosition);
        });
        txt.setText((String)getGroup(groupPosition));
        return convertView;
    }

    private void showAlertDialog(int groupPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add New Topic");
        View view = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null);
        builder.setView(view);

        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, which) -> {
            EditText editText = view.findViewById(R.id.edtTxt);
            topics.get(langs.get(groupPosition)).add(editText.getText().toString());
            notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_child, parent, false);
        }
        ImageView crossImage = convertView.findViewById(R.id.crossImage);
        crossImage.setOnClickListener(v -> {
            topics.get(langs.get(groupPosition)).remove(childPosition);
            notifyDataSetChanged();
        });
        TextView txt = convertView.findViewById(R.id.txt);
        txt.setText((String)getChild(groupPosition, childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
