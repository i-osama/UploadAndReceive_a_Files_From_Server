package com.example.uploadfilestoserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String targetUrl = "https://zeeshan-has-this.000webhostapp.com/PHP/fileUpload.php";
    EditText name, info;
    AppCompatButton store, addImg;
    ProgressBar progressBar;
    ImageView img;
    TextView goDataPage;
    Uri img_uri;
    String url;
    Bitmap bitmap;
    String encodedImgString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        info = findViewById(R.id.info);
        img = findViewById(R.id.img);
        addImg = findViewById(R.id.addImgBtn);
        store = findViewById(R.id.storeBtn);
        goDataPage = findViewById(R.id.goDataPage);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


//        @ Going to the data getting page
        goDataPage.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DataRecievingActivity.class);
            startActivity(intent);
        });


//        @handling on image upload click button
        addImg.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), 10);
        });

//        @handling data upload method
        store.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String u_name = "", details = "";

            u_name = name.getText().toString().trim();
            details = info.getText().toString().trim();

            if (u_name.isEmpty()){
                u_name = "-Blank";
            }
            if (details.isEmpty())
                details = "-blank";

            storeToServer(u_name, details);
        });

    }

    //    @Setting image to the imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null){
            img_uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img_uri);
                img.setImageBitmap(bitmap);
                //addImg.setText(img_uri.toString());
//                converting image into string
                encodeBitmapImg(bitmap);
            }catch (Exception e){

            }
        }
    }


//    @Encoding bitmap to store in the server
    private void encodeBitmapImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        encodedImgString = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //addImg.setText(encodedImgString);
    }


//    INSERT INTO `user_info` (`SNo`, `name`, `info`, `img`) VALUES
//    (NULL, 'Osama Bin Hashim', 'Lead Developer', 'https://www.facebook.com/photo/?fbid=1165927787573020&set=a.149327302566412');

//    @Storing to the server
    private void storeToServer(String u_name, String details) {
        StringRequest request = new StringRequest(Request.Method.POST, targetUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TAG", "onResponse: SUccess!!");
                name.setText("");
                info.setText("");
                progressBar.setVisibility(View.GONE);
                Log.i("TAG", "SUccess: "+response);
                Toast.makeText(MainActivity.this, "Success <3\n"+response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                Log.i("TAG", "Error!!"+error.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "ERROR!!!\n"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Error");
//                builder.setMessage(error.getLocalizedMessage());
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Handle button click if needed
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();

            }
        }
        )
//         @This Section is for sending image to the server
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                Log.i("TAG", "Name: "+u_name+" info: "+details);
//                map.put("SNO", null);
                map.put("name", u_name);
                map.put("info", details);
                map.put("img", encodedImgString);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

}