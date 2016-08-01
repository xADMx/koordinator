package ru.startandroid.koordinator;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ADM on 26.07.2016.
 */

public class out_helper {

    Context context;

    public out_helper(Context context_new){
        context = context_new;
    }

    public SimpleExpandableListAdapter OutListGroupGroup(settings SET_KOORDINATOR){
        ArrayList<Map<String, String>> groupData = new ArrayList<Map<String, String>>();;
        ArrayList<Map<String, String>> childDataItem;
        ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>();;
        Map<String, String> m;
        dbhelper db = new dbhelper(context);
        String sqlQuery;
        String selectionArgs[];

        sqlQuery = "Select `" + db.group_fields.KEY_ID
                + "` ,`" + db.group_fields.KEY_NAME
                + "` from `" + db.group_fields.TABLE_NAME + "`";

        Cursor cursor = db.Query(sqlQuery, null);

        if (cursor.moveToFirst()) {
            int idIndexCursor = cursor.getColumnIndex(db.group_fields.KEY_ID);
            int nameIndexCursor = cursor.getColumnIndex(db.group_fields.KEY_NAME);
            do {
                m = new HashMap<String, String>();
                m.put(db.group_fields.KEY_NAME, cursor.getString(nameIndexCursor)); // имя компании
                groupData.add(m);

                sqlQuery = "Select {0},{1},{2},{3},{4},{5},{6} from {7}"
                        + " inner join {8} on {7}.{9} = {8}.{10}"
                        + " where {8}.{11} = {12}";

                if (!SET_KOORDINATOR.Sort.equals("")){
                    sqlQuery += " order by `" + SET_KOORDINATOR.Sort + "`";
                }
                selectionArgs = new String[]{db.tasks_list_fields.KEY_ID,
                        db.tasks_list_fields.KEY_INFO,
                        db.tasks_list_fields.KEY_STREET,
                        db.tasks_list_fields.KEY_DT_STOP,
                        db.tasks_list_fields.KEY_DT_START,
                        db.tasks_list_fields.KEY_ID_STATUS_TASKS,
                        db.tasks_list_fields.TABLE_NAME,
                        db.users_fields.TABLE_NAME,
                        db.tasks_list_fields.KEY_ID_USERS,
                        db.users_fields.KEY_ID,
                        db.users_fields.KEY_ID_GROUP,
                        cursor.getString(idIndexCursor)};

                Cursor cursor2 = db.Query(sqlQuery, selectionArgs);
                if (cursor2.moveToFirst()) {
                    int idIndexCursor2 = cursor.getColumnIndex(db.tasks_list_fields.KEY_ID);
                    int infoCursor2 = cursor.getColumnIndex(db.tasks_list_fields.KEY_INFO);
                    int streetCursor2 = cursor.getColumnIndex(db.tasks_list_fields.KEY_STREET);
                    int dtstopCursor2 = cursor.getColumnIndex(db.tasks_list_fields.KEY_DT_STOP);
                    int dtstartCursor2 = cursor.getColumnIndex(db.tasks_list_fields.KEY_DT_START);
                    int statusCursor2 = cursor.getColumnIndex(db.tasks_list_fields.KEY_ID_STATUS_TASKS);
                    childDataItem = new ArrayList<Map<String, String>>();
                    do {
                        m = new HashMap<String, String>();
                        m.put("id", cursor.getString(idIndexCursor2)); // имя компании
                        m.put("info", cursor.getString(infoCursor2)); // имя компании
                        m.put("dtstop", cursor.getString(dtstopCursor2)); // имя компании
                        m.put("dtstart", cursor.getString(dtstartCursor2)); // имя компании
                        m.put("status", cursor.getString(statusCursor2)); // имя компании
                        childDataItem.add(m);
                    } while (cursor2.moveToNext());
                    childData.add(childDataItem);
                }
                cursor2.close();
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");

        cursor.close();

        return new SimpleExpandableListAdapter(
                context,
                groupData,
                R.layout.list,
                new String[]{db.group_fields.KEY_NAME},
                new int[]{R.id.text_head},
                childData,
                R.layout.child_item_list,
                new String[]{"status","info", "dtstop", "dtstart"},
                new int[]{R.id.imageView2,R.id.text_zadanie, R.id.text_date_start, R.id.text_date_stop});
    }

}
