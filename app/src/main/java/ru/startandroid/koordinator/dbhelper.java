package ru.startandroid.koordinator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ADM on 26.07.2016.
 */
public class dbhelper extends SQLiteOpenHelper {

    public final static String DB_Name = "DB_koordinator";

    public tasks_list tasks_list_fields = new tasks_list();
    public users users_fields = new users();
    public group group_fields = new group();
    public settings settings_fields = new settings();
    public status_tasks status_tasks_fields = new status_tasks();

    public static class tasks_list{
        public final String TABLE_NAME = "tasks_list";
        public final String KEY_ID = "id_tasks_list";
        public final String KEY_INFO = "info";
        public final String KEY_ID_STATUS_TASKS = "id_status_tasks";
        public final String KEY_ID_USERS = "id_users_tasks";
        public final String KEY_DT_START = "dt_start";
        public final String KEY_DT_STOP = "dt_stop";
        public final String KEY_ID_DT_FINAL = "id_dt_final";
        public final String KEY_STREET = "Street";
    }

    public static class users{
        public final String TABLE_NAME = "users";
        public final String KEY_ID = "id_users";
        public final String KEY_FIO = "fio";
        public final String KEY_ID_GROUP = "id_group_users";
    }

    public static class group{
        public final String TABLE_NAME = "group_users";
        public final String KEY_NAME = "name";
        public final String KEY_ID = "id_group";
    }

    public static class settings{
        public final String TABLE_NAME = "settings";
        public final String KEY_NAME = "name";
        public final String KEY_VALUE = "value";
        public final String KEY_ID = "id_settings";
    }

    public static class status_tasks{
        public final String TABLE_NAME = "status_tasks";
        public final String KEY_NAME = "name";
        public final String KEY_ID = "id_status";
    }

    /*
     switch (v.getId()) {
    // Все записи
    case R.id.btnAll:
      Log.d(LOG_TAG, "--- Все записи ---");
      c = db.query("mytable", null, null, null, null, null, null);
      break;
    // Функция
    case R.id.btnFunc:
      Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
      columns = new String[] { sFunc };
      c = db.query("mytable", columns, null, null, null, null, null);
      break;
    // Население больше, чем
    case R.id.btnPeople:
      Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
      selection = "people > ?";
      selectionArgs = new String[] { sPeople };
      c = db.query("mytable", null, selection, selectionArgs, null, null,
          null);
      break;
    // Население по региону
    case R.id.btnGroup:
      Log.d(LOG_TAG, "--- Население по региону ---");
      columns = new String[] { "region", "sum(people) as people" };
      groupBy = "region";
      c = db.query("mytable", columns, null, null, groupBy, null, null);
      break;
    // Население по региону больше чем
    case R.id.btnHaving:
      Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople
          + " ---");
      columns = new String[] { "region", "sum(people) as people" };
      groupBy = "region";
      having = "sum(people) > " + sRegionPeople;
      c = db.query("mytable", columns, null, null, groupBy, having, null);
      break;
    // Сортировка
    case R.id.btnSort:
      // сортировка по
      switch (rgSort.getCheckedRadioButtonId()) {
      // наименование
      case R.id.rName:
        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
        orderBy = "name";
        break;
      // население
      case R.id.rPeople:
        Log.d(LOG_TAG, "--- Сортировка по населению ---");
        orderBy = "people";
        break;
      // регион
      case R.id.rRegion:
        Log.d(LOG_TAG, "--- Сортировка по региону ---");
        orderBy = "region";
        break;
      }
      c = db.query("mytable", null, null, null, null, null, orderBy+ " ASC");
      break;
    }
                    Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                        do {
                            Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                            ", name = " + cursor.getString(nameIndex) +
                                            ", email = " + cursor.getString(emailIndex));
                        } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
    _------------------------------------------------------
        String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
        + "from people as PL "
        + "inner join position as PS "
        + "on PL.posid = PS.id "
        + "where salary > ?";
    c = db.rawQuery(sqlQuery, new String[] {"12000"});


     */
    public dbhelper(Context context) {
        // конструктор суперкласса
        super(context, dbhelper.DB_Name, null, 1);
    }

    public Cursor getAllGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(group_fields.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(users_fields.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getUsersGroupAndSort(String Group_Name, String Sort) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(users_fields.TABLE_NAME, null, null, null, Group_Name, null, Sort);
    }

    public Cursor Query(String sqlQuery, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        for (int i = 0; i < selectionArgs.length; i++){
            sqlQuery.replace("/{" + i +"}/", selectionArgs[i]);
            sqlQuery.replace("{" + i +"}", "`" + selectionArgs[i] + "`");
        }
        return db.rawQuery(sqlQuery, null);
    }

    public Cursor getAllTasks_List() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(tasks_list_fields.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getAllSetting() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(settings_fields.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getAllStatus() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(status_tasks_fields.TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table " + tasks_list_fields.TABLE_NAME + " ("
                + tasks_list_fields.KEY_ID + " integer primary key,"
                + tasks_list_fields.KEY_INFO + " text,"
                + tasks_list_fields.KEY_ID_STATUS_TASKS + " integer,"
                + tasks_list_fields.KEY_ID_USERS + " integer,"
                + tasks_list_fields.KEY_DT_START + " datetime default current_timestamp,"
                + tasks_list_fields.KEY_DT_STOP + " datetime default current_timestamp,"
                + tasks_list_fields.KEY_STREET + " text" + ");");

        db.execSQL("create table " + users_fields.TABLE_NAME + " ("
                + users_fields.KEY_ID + " integer primary key,"
                + users_fields.KEY_FIO + " text,"
                + users_fields.KEY_ID_GROUP + " integer" + ");");

        db.execSQL("create table " + group_fields.TABLE_NAME + " ("
                + group_fields.KEY_ID + " integer primary key,"
                + group_fields.KEY_NAME + " text" + ");");

        db.execSQL("create table " + settings_fields.TABLE_NAME + " ("
                + settings_fields.KEY_ID + "id_settings integer primary key,"
                + settings_fields.KEY_NAME + "name text,"
                + settings_fields.KEY_VALUE + "value integer" + ");");

        db.execSQL("create table " + status_tasks_fields.TABLE_NAME + " ("
                + status_tasks_fields.KEY_ID + " integer primary key ,"
                + status_tasks_fields.KEY_NAME + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}