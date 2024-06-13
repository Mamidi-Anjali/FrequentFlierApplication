package gmu.project.frequentflier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {
    String spid="";
    String dpid="";
    String points="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        RequestQueue queue = Volley.newRequestQueue(MainActivity6.this);
        spid = getIntent().getStringExtra("pid");
        queue.add(getIDlist());
        Button b = findViewById(R.id.transfer_points_button);
        Spinner sp = findViewById(R.id.passenger_id_spinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pid = adapterView.getSelectedItem().toString();
                if(!pid.equals("Select"))
                    dpid=pid;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.points_to_transfer_edittext);
                points=et.getText().toString();
                if("".equals(dpid)){
                    Toast.makeText(MainActivity6.this,"Please select a passenger to Transfer points.",Toast.LENGTH_LONG).show();
                    return;
                }
                else if("".equals(points)){
                    Toast.makeText(MainActivity6.this,"Please enter points to transfer.",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    queue.add(performTransaction());
                }
            }
        });
    }

    private StringRequest performTransaction() {
        String url = "http://10.0.2.2:8080/frequentflier/TransferPoints.jsp?spid="+spid+"&dpid="+dpid+"&npoints="+points;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity6.this,"Transaction Successfull!",Toast.LENGTH_LONG).show();
            }
        },null);
        return stringRequest;
    }

    private StringRequest getIDlist() {
        String url = "http://10.0.2.2:8080/frequentflier/GetPassengerids.jsp?pid="+spid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String res=s.trim();
                ArrayList<String> list =new ArrayList<String>();
                list.add("Select");
                String[] x = res.split(",");
                for(int i=0;i<x.length;i++){
                    if(!"".equals(x[i])){
                        //String[] p =x[i].split(",");
                        list.add(x[i]);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity6.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                Spinner sp =findViewById(R.id.passenger_id_spinner);
                sp.setAdapter(adapter);
            }
        },null);
        return stringRequest;
    }
}