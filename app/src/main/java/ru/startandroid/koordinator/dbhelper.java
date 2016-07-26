package ru.startandroid.koordinator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ADM on 26.07.2016.
 */
public class dbhelper extends SQLiteOpenHelper {

    public dbhelper(Context context) {
        // конструктор суперкласса
        super(context, "DB_koordinator", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table tasks_list ("
                + "id_tasks_list integer primary key autoincrement,"
                + "info text,"
                + "id_users integer,"
                + "dt_start datetime default current_timestamp,"
                + "dt_stop datetime default current_timestamp,"
                + "Street text" + ");");

        db.execSQL("create table users ("
                + "id_users integer primary key autoincrement,"
                + "fio text,"
                + "id_group integer" + ");");

        db.execSQL("create table group ("
                + "id_group integer primary key autoincrement,"
                + "name text" + ");");

        db.execSQL("create table settings ("
                + "id_settings integer primary key autoincrement,"
                + "name text,"
                + "value integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}