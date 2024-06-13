package gmu.project.frequentflier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {
    String pid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        RequestQueue queue = Volley.newRequestQueue(MainActivity5.this);
        pid = getIntent().getStringExtra("pid");
        queue.add(getAwardDet());
        Spinner sp = findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String fid = adapterView.getSelectedItem().toString();
                if(!fid.equals("Select"))
                    queue.add(getRedempHist(fid));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private StringRequest getRedempHist(String aid) {
        String url = "http://10.0.2.2:8080/frequentflier/RedemptionDetails.jsp?awardid="+aid+"&pid="+pid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String res=s.trim();
                String[] x =res.split("#");
                if(x.length>0){
                    TextView dept = findViewById(R.id.prizedesc);
                    dept.setText("Prize Description: \n"+x[0].split(",")[0]);
                    TextView arr = findViewById(R.id.points);
                    arr.setText("Points Needed: \n"+x[0].split(",")[1]);
                }
                populateTable(x);
            }
        },null);
        return stringRequest;
    }

    private void populateTable(String[] x){
        TableLayout flightsTable = findViewById(R.id.awards_info_table);
        TableRow headersRow = new TableRow(this);
        TextView flightIdHeader = new TextView(this);
        flightIdHeader.setText("Redemption Date");
        flightIdHeader.setGravity(Gravity.CENTER);
        headersRow.addView(flightIdHeader);
        TextView flightMilesHeader = new TextView(this);
        flightMilesHeader.setText("Exchange Center");
        flightMilesHeader.setGravity(Gravity.CENTER);
        headersRow.addView(flightMilesHeader);
        flightsTable.addView(headersRow);

        for(int i=0;i<x.length;i++){
            if(!"".equals(x[i])) {
                String[] flightsData = x[i].split(",");
                TableRow row = new TableRow(this);
                TextView flightId = new TextView(this);
                flightId.setText(flightsData[2]);
                flightId.setGravity(Gravity.CENTER);
                row.addView(flightId);
                TextView flightMiles = new TextView(this);
                flightMiles.setText(flightsData[3]);
                flightMiles.setGravity(Gravity.CENTER);
                row.addView(flightMiles);
                flightsTable.addView(row);
            }
        }
    }

    private StringRequest getAwardDet() {
        String url = "http://10.0.2.2:8080/frequentflier/AwardIds.jsp?pid="+pid;
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                Spinner sp =findViewById(R.id.spinner);
                sp.setAdapter(adapter);
            }
        },null);
        return stringRequest;
    }
}