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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    String pid="";
    private StringRequest getFlightDt(){
        String url = "http://10.0.2.2:8080/frequentflier/Flights.jsp?pid="+pid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String res=s.trim();
                ArrayList<String> list =new ArrayList<String>();
                list.add("Select");
                String[] x = res.split("#");
                for(int i=0;i<x.length;i++){
                    if(!"".equals(x[i])){
                        String[] p =x[i].split(",");
                        list.add(p[0]);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                Spinner sp =findViewById(R.id.spinner);
                sp.setAdapter(adapter);
            }
        },null);
        return stringRequest;
    }

    private void populateTable(String s){
        TableLayout flightsTable = findViewById(R.id.flight_info_table);
        TableRow headersRow = new TableRow(this);
        TextView flightIdHeader = new TextView(this);
        flightIdHeader.setText("Trip ID");
        flightIdHeader.setGravity(Gravity.CENTER);
        headersRow.addView(flightIdHeader);
        TextView flightMilesHeader = new TextView(this);
        flightMilesHeader.setText("Trip Miles");
        flightMilesHeader.setGravity(Gravity.CENTER);
        headersRow.addView(flightMilesHeader);
        flightsTable.addView(headersRow);

        String[] x=s.split("#");
        for(int i=0;i<x.length;i++){
            if(!"".equals(x[i])) {
                String[] flightsData = x[i].split(",");
                TableRow row = new TableRow(this);
                TextView flightId = new TextView(this);
                flightId.setText(flightsData[3]);
                flightId.setGravity(Gravity.CENTER);
                row.addView(flightId);
                TextView flightMiles = new TextView(this);
                flightMiles.setText(flightsData[4]);
                flightMiles.setGravity(Gravity.CENTER);
                row.addView(flightMiles);
                flightsTable.addView(row);
            }
        }
    }
    private StringRequest getTripDet(String fid){
        String url = "http://10.0.2.2:8080/frequentflier/FlightDetails.jsp?flightid="+fid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String res=s.trim();
                TextView dept = findViewById(R.id.departure_time_label);
                dept.setText("Departure Time: "+res.split(",")[0]);
                TextView arr = findViewById(R.id.arrival_time_label);
                arr.setText("Arrival Time: "+res.split(",")[1]);
                TextView miles = findViewById(R.id.miles_label);
                miles.setText("Miles: "+res.split(",")[2]);
                populateTable(res);
            }
        },null);
        return stringRequest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        RequestQueue queue = Volley.newRequestQueue(MainActivity4.this);
        pid = getIntent().getStringExtra("pid");
        queue.add(getFlightDt());
        Spinner sp = findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String fid = adapterView.getSelectedItem().toString();
                if(!fid.equals("Select"))
                    queue.add(getTripDet(fid));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}