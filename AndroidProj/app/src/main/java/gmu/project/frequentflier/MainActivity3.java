package gmu.project.frequentflier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {
    String pid="";
    private StringRequest getFlightDt(){
        String url = "http://10.0.2.2:8080/frequentflier/Flights.jsp?pid="+pid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String res=s.trim();
                populateTable(res);
            }
        },null);
        return stringRequest;
    }
    private void populateTable(String s){
        TableLayout flightsTable = findViewById(R.id.flights_table);
        TableRow headersRow = new TableRow(this);
        TextView flightIdHeader = new TextView(this);
        flightIdHeader.setText("Flight ID");
        flightIdHeader.setGravity(Gravity.CENTER);
        headersRow.addView(flightIdHeader);
        TextView flightMilesHeader = new TextView(this);
        flightMilesHeader.setText("Flight Miles");
        flightMilesHeader.setGravity(Gravity.CENTER);
        headersRow.addView(flightMilesHeader);
        TextView flightDestinationHeader = new TextView(this);
        flightDestinationHeader.setText("Flight Destination");
        flightDestinationHeader.setGravity(Gravity.CENTER);
        headersRow.addView(flightDestinationHeader);
        flightsTable.addView(headersRow);

        String[] x=s.split("#");
        for(int i=0;i<x.length;i++){
            if(!"".equals(x[i])) {
                String[] flightsData = x[i].split(",");
                TableRow row = new TableRow(this);
                TextView flightId = new TextView(this);
                flightId.setText(flightsData[0]);
                flightId.setGravity(Gravity.CENTER);
                row.addView(flightId);
                TextView flightMiles = new TextView(this);
                flightMiles.setText(flightsData[1]);
                flightMiles.setGravity(Gravity.CENTER);
                row.addView(flightMiles);
                TextView flightDestination = new TextView(this);
                flightDestination.setText(flightsData[2]);
                flightDestination.setGravity(Gravity.CENTER);
                row.addView(flightDestination);
                flightsTable.addView(row);
            }
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        pid = getIntent().getStringExtra("pid");
        StringRequest stringRequest = getFlightDt();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity3.this);
        requestQueue.add(stringRequest);
    }
}