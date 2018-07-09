package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phanqui.grocery.R;
import com.phanqui.grocery.url.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedBack_Activity extends AppCompatActivity {

    Button btnSubmit,btnBackFB;
    EditText edtComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_);

        adđControls();
        addEvents();
    }

    private void addEvents() {
        btnBackFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                    FeedBack();
                }else {
                    Toast.makeText(getApplicationContext(),"Hãy đăng nhập để gửi phản hồi",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),SignIn_Activity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void FeedBack() {

        final String content = edtComment.getText().toString().trim();
        final Date date = Calendar.getInstance().getTime();
        final String username = SharedPrefManager.getInstance(FeedBack_Activity.this).getUsername();
        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){

        }
        if(content.length()>0){
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.fb, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //http://raubb.somee.com/api/feedback?username=&&datetimepost=&&phone=
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("username",username);
                    hashMap.put("content",content);
                    hashMap.put("datetimepost", String.valueOf(date));
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(),"Hãy điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }

    }

    private void adđControls() {

        btnSubmit = findViewById(R.id.btnSubmit);
        btnBackFB = findViewById(R.id.btnBackFB);
        edtComment =findViewById(R.id.edtComment);
    }

}
