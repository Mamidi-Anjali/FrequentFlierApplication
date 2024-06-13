package gmu.project.frequentflier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {
    String pid="";
    static int countImg=1;
    String name="";
    String points="";
    private ImageRequest getImageRequest(){
        if(countImg==5)
            countImg=1;
        countImg=pid.equals("1")?1:countImg;
        ImageView imageView = findViewById(R.id.sample_picture);
        String url = "http://10.0.2.2:8080/frequentflier/images/"+countImg+".jpeg";
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        },0,0,null,null);
        countImg++;
        return imageRequest;
    }

    private StringRequest getPassDetails(){
        String url = "http://10.0.2.2:8080/frequentflier/Info.jsp?pid="+pid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String res=s.trim();
                name=res.split(",")[0];
                points=res.split(",")[1];
                TextView nameTV = findViewById(R.id.name);
                nameTV.setText(name);
                TextView pointsTV = findViewById(R.id.reward_points);
                pointsTV.setText(points);
            }
        },null);
        return stringRequest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        pid = getIntent().getStringExtra("pid");
        ImageRequest imageRequest = getImageRequest();
        StringRequest stringRequest = getPassDetails();
        queue.add(imageRequest);
        queue.add(stringRequest);
        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });
        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity4.class);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });
        Button b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity5.class);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });
        Button b4 = findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity6.class);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });
        Button b5 = findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}