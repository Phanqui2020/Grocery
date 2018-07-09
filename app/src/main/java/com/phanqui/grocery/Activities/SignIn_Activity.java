package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class SignIn_Activity extends AppCompatActivity {

    EditText edtUserName,edtPassWord;
    Button btnSignIn,btnBackSI;
    TextView tvRegister;
    public static final String sodt = "012";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        addControls();
        addEvents();

        
    }

    private void addEvents() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
        btnBackSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    private void Register() {
        Intent intent = new Intent(getApplicationContext(), SignUp_Activity.class);
        startActivity(intent);
    }

    private void SignIn() {
        final String sdt = edtUserName.getText().toString().trim();
        final String pass = edtPassWord.getText().toString().trim();
        if(sdt.length()>0 && pass.length()>0){
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.login, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    // startActivity(intent);
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if(!error){
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(
                                            jObj.getString("username")
                                            //String.valueOf(sdt)
                                    );
                           // Log.d()
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra(sodt,edtUserName.getText().toString());
                            startActivity(intent);
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
                    hashMap.put("username",sdt);
                    hashMap.put("password",pass);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(),"Hãy điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {

        edtUserName =findViewById(R.id.edtUserName);
        edtPassWord =findViewById(R.id.edtPassWord);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnBackSI =findViewById(R.id.btnBackSI);
        tvRegister =findViewById(R.id.tvRegister);

    }

}
