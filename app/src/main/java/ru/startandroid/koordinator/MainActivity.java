package ru.startandroid.koordinator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ExpandableListView elvMain;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static String LOG_TAG = "my_log";
    public String User_ID = null;
    public String Loggin_True = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User_ID = getIntent().getStringExtra("user_id");
        Loggin_True = getIntent().getStringExtra("login");

        if (!Loggin_True.equals("1")) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            zapolnenie_list();

            // /You will setup the action bar with pull to refresh layout
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

            mSwipeRefreshLayout.setColorSchemeColors(
                    getResources().getColor(R.color.blue),
                    getResources().getColor(R.color.green),
                    getResources().getColor(R.color.orange),
                    getResources().getColor(R.color.purple));

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    zapolnenie_list();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }



    public void zapolnenie_list (){
        // названия компаний (групп)
        String[] groups = new String[] {"HTC", "Samsung", "LG"};
        // названия телефонов (элементов)
        String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
        String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
        String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};
        // коллекция для групп
        ArrayList<Map<String, String>> groupData;
        // коллекция для элементов одной группы
        ArrayList<Map<String, String>> childDataItem;
        // общая коллекция для коллекций элементов
        ArrayList<ArrayList<Map<String, String>>> childData;
        // в итоге получится childData = ArrayList<childDataItem>
        // список атрибутов группы или элемента
        Map<String, String> m;
        // заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            // заполняем список атрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }
        // список атрибутов групп для чтения
        String groupFrom[] = new String[]{"groupName"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[]{R.id.text_head};
        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();
        // создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список атрибутов для каждого элемента
        for (String phone : phonesHTC) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone);
            m.put("start", "24.05.2016 22:23"); // название телефона
            m.put("stop", "28.05.2016 22:23"); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

            // создаем коллекцию элементов для второй группы       
            childDataItem = new ArrayList<Map<String, String>>();
            for (String phone : phonesSams) {
                m = new HashMap<String, String>();
                m.put("phoneName", phone);
                m.put("start", "24.05.2016 22:23"); // название телефона
                m.put("stop", "28.05.2016 22:23"); // название телефона
                childDataItem.add(m);
                }
            childData.add(childDataItem);
            // создаем коллекцию элементов для третьей группы       
            childDataItem = new ArrayList<Map<String, String>>();
            for (String phone : phonesLG) {
                m = new HashMap<String, String>();
                m.put("phoneName", phone);
                m.put("start", "24.05.2016 22:23"); // название телефона
                m.put("stop", "28.05.2016 22:23"); // название телефона
                childDataItem.add(m);
                }
            childData.add(childDataItem);
        // список атрибутов элементов для чтения
        String childFrom[] = new String[]{"phoneName", "start", "stop"};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[]{R.id.text_zadanie, R.id.text_date_start, R.id.text_date_stop};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                R.layout.list,
                groupFrom,
                groupTo,
                childData,
                R.layout.child_item_list,
                childFrom,
                childTo);

        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }

    public void set_user_id(String id) {
        User_ID = id;
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL("http://androiddocs.ru/api/friends.json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
            Log.d(LOG_TAG, strJson);

            JSONObject dataJsonObj = null;
            String secondName = "";

            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray friends = dataJsonObj.getJSONArray("friends");

                // 1. достаем инфо о втором друге - индекс 1
                JSONObject secondFriend = friends.getJSONObject(1);
                secondName = secondFriend.getString("name");
                Log.d(LOG_TAG, "Второе имя: " + secondName);

                // 2. перебираем и выводим контакты каждого друга
                for (int i = 0; i < friends.length(); i++) {
                    JSONObject friend = friends.getJSONObject(i);

                    JSONObject contacts = friend.getJSONObject("contacts");

                    String phone = contacts.getString("mobile");
                    String email = contacts.getString("email");
                    String skype = contacts.getString("skype");

                    Log.d(LOG_TAG, "phone: " + phone);
                    Log.d(LOG_TAG, "email: " + email);
                    Log.d(LOG_TAG, "skype: " + skype);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_update){
            new ParseTask().execute();
            item.setTitle("privet");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
