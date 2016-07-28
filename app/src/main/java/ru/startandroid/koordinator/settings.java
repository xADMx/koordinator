package ru.startandroid.koordinator;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by ADM on 27.07.2016.
 */
public class settings {
    public String Sort;
    public String User_ID = null;
    public boolean Loggin_True = false;

    public void settings(Context context){
        dbhelper db = new dbhelper(context);
        String sqlQuery;
        String selectionArgs[];

        sqlQuery = "Select {0},{1} from {2}";

        selectionArgs = new String[]{db.settings_fields.KEY_NAME,
                db.settings_fields.KEY_VALUE,
                db.settings_fields.TABLE_NAME};

        Cursor cursor = db.Query(sqlQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            int nameCursor2 = cursor.getColumnIndex(db.settings_fields.KEY_NAME);
            int valueCursor2 = cursor.getColumnIndex(db.settings_fields.KEY_VALUE);

            do {
                switch(cursor.getString(nameCursor2)) {
                    case "Sort":
                        this.Sort = cursor.getString(valueCursor2);
                        break;
                }
            } while (cursor.moveToNext());
        }
    }
}
