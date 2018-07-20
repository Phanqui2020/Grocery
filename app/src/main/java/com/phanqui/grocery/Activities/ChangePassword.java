package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText edtOldPasssword, edtNewPassword;
    Button btnOK, btnBackCP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        addEvents();

    }

    private void addEvents() {
        btnBackCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepass();
            }
        });
    }

    private void changepass() {
        final String mkc = edtOldPasssword.getText().toString().trim();
        final String mkm = edtNewPassword.getText().toString().trim();
        final String user = SharedPrefManager.getInstance(ChangePassword.this).getUsername();
        final String pass = SharedPrefManager.getInstance(ChangePassword.this).getPass();
        if(mkc.length()>0 && mkm.length()>0){
            if(mkc.equals(pass)){
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.changepass, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        // startActivity(intent);
                        JSONObject jObj = null;
                        try {
                            jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if(!error){
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String,String>();
                        hashMap.put("username",user);
                        hashMap.put("pass",mkm);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }else {
                Toast.makeText(getApplicationContext(),"Mật khẩu cũ không trùng khớp",Toast.LENGTH_LONG).show();
            }
            }else {
            Toast.makeText(getApplicationContext(),"Hãy điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        edtNewPassword = findViewById(R.id.edtNewPass);
        edtOldPasssword =findViewById(R.id.edtOldPass);
        btnOK = findViewById(R.id.btnOK);
        btnBackCP = findViewById(R.id.btnBackCP);
    }

}
