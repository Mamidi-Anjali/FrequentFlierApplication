package gmu.project.frequentflier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = findViewById(R.id.loginButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userET = findViewById(R.id.usernameEditText);
                EditText passET = findViewById(R.id.passwordEditText);
                String user = userET.getText().toString();
                String pass = passET.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "http://10.0.2.2:8080/frequentflier/login?user="+user+"&pass="+pass;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String res = s.trim();
                        //String res = "Yes:1";
                        if (res.startsWith("Yes")) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                            String pid = res.split(":")[1];
                            intent.putExtra("pid",pid);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        System.out.println(volleyError.toString());
                    }
                });
                queue.add(request);
            }
        });
    }
}