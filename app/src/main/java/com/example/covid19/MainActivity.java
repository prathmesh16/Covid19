package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    HashMap<String,String> myMap;
    Spinner states;
    Spinner districts;
    TextView confirmed;
    TextView deaths;
    TextView recovered;
    TextView active;
    TextView confirmeds;
    TextView deathss;
    TextView recovereds;
    TextView actives;
    TextView confirmedsi;
    TextView deathssi;
    TextView recoveredsi;
    TextView activesi;
    TextView state;
    ProgressDialog progress;

    JSONObject data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress= new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Fetching Data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

// To dismiss the dialog

        confirmed=(TextView)findViewById(R.id.textViewConfirmed);
        active=(TextView)findViewById(R.id.textViewActive);
        recovered=(TextView)findViewById(R.id.textViewRecovered);
        deaths=(TextView)findViewById(R.id.textViewDeaths);

        confirmeds=(TextView)findViewById(R.id.textViewConfirmeds);
        actives=(TextView)findViewById(R.id.textViewActives);
        recovereds=(TextView)findViewById(R.id.textViewRecovereds);
        deathss=(TextView)findViewById(R.id.textViewDeathss);

        confirmedsi=(TextView)findViewById(R.id.textViewConfirmedsi);
        activesi=(TextView)findViewById(R.id.textViewActivesi);
        recoveredsi=(TextView)findViewById(R.id.textViewRecoveredsi);
        deathssi=(TextView)findViewById(R.id.textViewDeathssi);


        states=(Spinner)findViewById(R.id.spinner);
        districts=(Spinner)findViewById(R.id.spinner2);
        //state=(TextView)findViewById(R.id.state);
        states.setSelection(20);
        myMap=new HashMap<String,String>();
        myMap.put("Andaman and Nicobar Islands","AN");
        myMap.put("Andhra Pradesh","AP");
        myMap.put("Telangana","TS");
        myMap.put("Arunachal Pradesh","AR");
        myMap.put("Assam","AS");
        myMap.put("Bihar","BR");
        myMap.put("Chandigarh","CH");
        myMap.put("Chattisgarh","CT");
        myMap.put("Dadra and Nagar Haveli","DN");
        myMap.put("Daman and Diu","DD");
        myMap.put("Delhi","DL");
        myMap.put("Goa","GA");
        myMap.put("Gujarat","GJ");
        myMap.put("Haryana","HR");
        myMap.put("Himachal Pradesh","HP");
        myMap.put("Jammu and Kashmir","JK");
        myMap.put("Jharkhand","JH");
        myMap.put("Karnataka","KA");
        myMap.put("Kerala","KL");
        myMap.put("Lakshadweep Islands","LD");
        myMap.put("Madhya Pradesh","MP");
        myMap.put("Maharashtra","MH");
        myMap.put("Manipur","MN");
        myMap.put("Meghalaya","ML");
        myMap.put("Mizoram","MZ");
        myMap.put("Nagaland","NL");
        myMap.put("Odisha","OR");
        myMap.put("Pondicherry","PY");
        myMap.put("Punjab","PB");
        myMap.put("Rajasthan","RJ");
        myMap.put("Sikkim","SK");
        myMap.put("Tamil Nadu","TN");
        myMap.put("Telangana","TS");
        myMap.put("Tripura","TR");
        myMap.put("Uttar Pradesh","UP");
        myMap.put("Uttarakhand","UK");
        myMap.put("West Bengal","WB");
        districts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    progress.show();
                    confirmed.setText(data.getJSONObject("district").getJSONObject(String.valueOf(districts.getSelectedItem())).getString("confirmed"));
                    active.setText(data.getJSONObject("district").getJSONObject(String.valueOf(districts.getSelectedItem())).getString("active"));
                    recovered.setText(data.getJSONObject("district").getJSONObject(String.valueOf(districts.getSelectedItem())).getString("recovered"));
                    deaths.setText(data.getJSONObject("district").getJSONObject(String.valueOf(districts.getSelectedItem())).getString("deceased"));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                progress.dismiss();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                progress.show();
                confirmed.setText("...");
                active.setText("...");
                recovered.setText("...");
                deaths.setText("...");
                sendReq(i);
                //Toast.makeText(MainActivity.this,"pkj",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }
    public void sendReq(int i)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        // state.setText(states.getSelectedItem().toString());
        String url ="https://corona-virus-world-and-india-data.p.rapidapi.com/api_india";//+myMap.get(states.getSelectedItem());

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                        JSONObject data1 = null;
                        try {

                            data1=response.getJSONObject("state_wise").getJSONObject(String.valueOf(states.getSelectedItem())).getJSONObject("district");//String.valueOf(states.getSelectedItem())
                            //Toast.makeText(MainActivity.this,data.length(),Toast.LENGTH_LONG).show();
                            Log.i("cnt",String.valueOf(data1.length()));
                            ArrayList<String> Dnames = new ArrayList<>();
                            for (String name:getNames(data1)
                                 ) {
                                   Log.i("name",name);
                                   Dnames.add(name);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,Dnames);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            districts.setAdapter(arrayAdapter);
                            data=response.getJSONObject("state_wise").getJSONObject(String.valueOf(states.getSelectedItem()));

                            confirmeds.setText(data.getString("confirmed"));
                            actives.setText(data.getString("active"));
                            recovereds.setText(data.getString("recovered"));
                            deathss.setText(data.getString("deaths"));

                            confirmedsi.setText(response.getJSONObject("total_values").getString("confirmed"));
                            activesi.setText(response.getJSONObject("total_values").getString("active"));
                            recoveredsi.setText(response.getJSONObject("total_values").getString("recovered"));
                            deathssi.setText(response.getJSONObject("total_values").getString("deaths"));

                            progress.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-rapidapi-host", "corona-virus-world-and-india-data.p.rapidapi.com");
                params.put("x-rapidapi-key", "e29748ae57msh1b2c87207917c00p1bd043jsn1cd850205d0c");

                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
    public static String[] getNames(JSONObject jo) {
        int length = jo.length();
        if (length == 0) {
            return null;
        }
        Iterator<String> iterator = jo.keys();
        String[] names = new String[length];
        int i = 0;
        while (iterator.hasNext()) {
            names[i] = iterator.next();
            i += 1;
        }
        return names;
    }
}
