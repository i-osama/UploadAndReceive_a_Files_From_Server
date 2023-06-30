package com.example.uploadfilestoserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.uploadfilestoserver.ServerModel.ServerModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataRecievingActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    List<ServerModel> modelList;
    RecyclerView recyclerView;

    String url = "https://zeeshan-has-this.000webhostapp.com/PHP/takeData.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_recieving);

        recyclerView = findViewById(R.id.recycler);
        modelList = new ArrayList<>();

        JsonArrayRequest arrayRequest  = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.i("TAG", "Response from server: "+ response.getString());

                        if (response.length()>0){
                            for (int i = 0; i< response.length(); i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    String name = jsonObject.getString("name");
                                    String info = jsonObject.getString("info");
                                    String img = jsonObject.getString("img");
                                    Log.i("TAG", "Works till (1) ");

                                    ServerModel model = new ServerModel(name, info, img);
                                    modelList.add(model);

                                }catch (Exception e){
                                    Toast.makeText(DataRecievingActivity.this, "Error!!\n"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        }


//        @ Sending data to adapter
                        ServerAdapter adapter = new ServerAdapter(modelList, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataRecievingActivity.this, "Error!!\n"+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(arrayRequest);


    }

}