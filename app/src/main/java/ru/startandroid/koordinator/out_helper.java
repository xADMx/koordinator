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

    public SimpleExpandableListAdapter OutListGroupGroup(Context context){
        ArrayList<Map<String, String>> groupData;
        ArrayList<Map<String, String>> childDataItem;
        ArrayList<ArrayList<Map<String, String>>> childData;
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
            groupData = new ArrayList<Map<String, String>>();
            do {
                m = new HashMap<String, String>();
                m.put(db.group_fields.KEY_NAME, cursor.getString(nameIndexCursor)); // имя компании
                groupData.add(m);

                sqlQuery = "Select {0},{1},{2},{3},{4},{5},{6} from {7}"
                        + " inner join {8} on {7}.{9} = {8}.{10}"
                        + " where {8}.{11} = {12}";
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
                    int idIndexCursor2 = cursor.getColumnIndex(db.group_fields.KEY_ID);
                    int nameCursor2 = cursor.getColumnIndex(db.group_fields.KEY_NAME);
                    int nameCursor2 = cursor.getColumnIndex(db.group_fields.KEY_NAME);
                    int nameCursor2 = cursor.getColumnIndex(db.group_fields.KEY_NAME);
                    int nameCursor2 = cursor.getColumnIndex(db.group_fields.KEY_NAME);
                    int nameCursor2 = cursor.getColumnIndex(db.group_fields.KEY_NAME);
                    do {

                    } while (cursor2.moveToNext());
                }
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");

        cursor.close();

        for (Map.Entry entry: staff.entrySet()) {
            String key = entry.getKey();
            Employee value = entry.getValue();
//действия с ключом и значением
        }

        for (String group : groups) {
            // заполняем список атрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        childData.add(childDataItem);

        return new SimpleExpandableListAdapter(
                this,
                groupData,
                R.layout.list,
                new String[]{db.group_fields.KEY_NAME},
                new int[]{R.id.text_head},
                childData,
                R.layout.child_item_list,
                childFrom,
                new int[]{R.id.text_zadanie, R.id.text_date_start, R.id.text_date_stop});
    }
}

