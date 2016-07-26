package ru.startandroid.koordinator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public String signin_true()  {
        /*
        * Авторизация
        * URL: DomainURL + "api/signin"
        * Тип POST: login, pass
        * Получение: ['valid' = '1' or '0', 'id' = 'int']
        * */
        String out = "";
        http QHttp;
        QHttp = new http();
        HashMap<String, String> postData = new HashMap<String, String>();

        TextView emailtxt = (TextView)findViewById(R.id.input_email);
        TextView passtxt = (TextView)findViewById(R.id.input_password);

        postData.put("login", emailtxt.getText().toString());
        postData.put("pass", passtxt.getText().toString());

        //out = QHttp.http("api/signin", postData);

        out = QHttp.http_query("", postData);

        try {
            if (!out.equals("")){
                JSONObject dataJsonObj = new JSONObject(out);
                String id = (String) dataJsonObj.getString("id");
                String valid = (String) dataJsonObj.getString("valid");
                if (valid.equals("1")) {
                    return id;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void signinclick(View view) {
        new AsyncTask<Void, Void, String>() {
            private Exception exception;

            @Override
            protected String doInBackground(Void... params) {
       return signin_true();

    }

    @Override
    protected void onPostExecute(String ID) {
        super.onPostExecute(ID);
        if (!ID.equals("")) {
            Intent intent = new Intent(ru.startandroid.koordinator.Login.this, ru.startandroid.koordinator.MainActivity.class);
            intent.putExtra("user_id", ID);
            intent.putExtra("login", "1");
            startActivity(intent);
            finish();
        }

    }
}.execute();
    }

}

