package ru.startandroid.koordinator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by ADM on 28.07.2016.
 */
public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroups;
    private ArrayList<Map<String, String>> groupData;
    private ArrayList<ArrayList<Map<String, String>>> childData;
    private Context mContext;

    public ExpListAdapter (Context context, ArrayList<Map<String, String>> groups , ArrayList<ArrayList<Map<String, String>>> child){
        mContext = context;
        groupData = groups;
        childData = child;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.text_head);
        Map<String, String> m;

        m = groupData.get(groupPosition);

        textGroup.setText(m.get("head"));

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item_list, null);
        }

        TextView text_zadanie = (TextView) convertView.findViewById(R.id.text_zadanie);
        TextView text_date_start = (TextView) convertView.findViewById(R.id.text_date_start);
        TextView text_date_stop = (TextView) convertView.findViewById(R.id.text_date_stop);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        Map<String, String> m;

        m = childData.get(groupPosition).get(childPosition);
        text_zadanie.setText(m.get("info"));
        text_date_start.setText(m.get("sart"));
        text_date_stop.setText(m.get("stop"));


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateSart = format.parse(m.get("sart"));
            Date dateStop = format.parse(m.get("stop"));
            System.out.println(dateSart);
            System.out.println(dateStop);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

 /*       textChild.setText(mGroups.get(groupPosition).get(childPosition));

        Button button = (Button)convertView.findViewById(R.id.buttonChild);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"button is pressed",5000).show();
            }
        });*/

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
