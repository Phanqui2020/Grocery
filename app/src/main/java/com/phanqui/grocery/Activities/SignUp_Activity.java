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
import android.widget.TextView;
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

public class SignUp_Activity extends AppCompatActivity {

    EditText edtUser, edtPass, edtEmail, edtAddress, edtPhone;
    Button btnSignUp, btnBackSU;
    TextView tvLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBackSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
    }

    private void Confirm() {
        final String ten = edtUser.getText().toString().trim();
        final String pass = edtPass.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String sdt = edtPhone.getText().toString().trim();
        final String adr = edtAddress.getText().toString().trim();
        if(ten.length()>0 && email.length()>0 && pass.length()>0 && sdt.length()>0 && adr.length()>0){
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.register, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if(email.matches(emailPattern)){
                            if (!error){
                                Toast.makeText(getApplicationContext(),"Đăng ký thành công", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), SignIn_Activity.class);
                                startActivity(intent);
                                finish();

                            }else {
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        }
                        else Toast.makeText(getApplicationContext(),"Địa chỉ email không hợp lệ", Toast.LENGTH_LONG).show();

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
                    hashMap.put("username",ten);
                    hashMap.put("address",adr);
                    hashMap.put("phone",sdt);
                    hashMap.put("password",pass);
                    hashMap.put("email",email);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {

        edtUser =findViewById(R.id.edtUser);
        edtAddress =findViewById(R.id.edtAddress);
        edtPhone =findViewById(R.id.edtPhone);
        edtPass =findViewById(R.id.edtPass);
        edtEmail =findViewById(R.id.edtEmail);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBackSU =findViewById(R.id.btnBackSU);
        tvLogin =findViewById(R.id.tvLogin);

    }
}
