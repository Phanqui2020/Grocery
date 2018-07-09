package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Bill_Activity extends AppCompatActivity {


    EditText edtDiachi,edtsdt;
    Button btnXacnhan,btnBack;
    TextView txttotal,txttenkhachhang;
    long tongtien = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
    }


    private void Confirm() {
        final String ten = txttenkhachhang.getText().toString().trim();
        //final String id = txtiduser.getText().toString().trim();
        final String dc = edtDiachi.getText().toString().trim();
        final String sdt = edtsdt.getText().toString().trim();
        final String total = txttotal.getText().toString().trim();
        if(ten.length()>0 && dc.length()>0 && sdt.length()>0){
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.dh, new Response.Listener<String>() {
                @Override
                public void onResponse(final String orderid) {
                    Log.d("orderid", orderid);

                    JSONObject jObj = null;
                    int id = 0;
                    try {
                        jObj = new JSONObject(orderid);
                        boolean error = jObj.getBoolean("error");
                        id = jObj.getInt("orderid");
                        Log.d("orderid: ", String.valueOf(id));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0;i < MainActivity.arrCart.size();i++){
                    if (id > 0) {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        final int finalId = id;
                        final int finalI = i;
                        StringRequest request = new StringRequest(Request.Method.POST, Server.ctdh, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                MainActivity.arrCart.clear();
                                Toast.makeText(getApplicationContext(), "mua hàng thành công", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "vui lòng đợi chúng tôi sẽ gọi lại xác nhận đơn hàng!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                //JSONArray jsonArray = new JSONArray();
                                //   for(int i = 0;i < MainActivity.arrCart.size();i++){

                                //JSONObject jsonObject = new JSONObject();
                                hashMap.put("orderid", String.valueOf(finalId));//
                                hashMap.put("productid", String.valueOf(MainActivity.arrCart.get(finalI).getIdsanpham()));
                                hashMap.put("gia", String.valueOf(MainActivity.arrCart.get(finalI).getGia()));
                                hashMap.put("soluong", String.valueOf(MainActivity.arrCart.get(finalI).getSoluong()));
                                // try {
                                //jsonObject.put("tensanpham",MainActivity.arrCart.get(i).getTensanpham());
                                // jsonObject.put("orderid",finalId);//
                                //jsonObject.put("productid",MainActivity.arrCart.get(i).getIdsanpham());
                                //jsonObject.put("tensanpham",MainActivity.arrCart.get(i).getTensanpham());
                                // jsonObject.put("gia",MainActivity.arrCart.get(i).getGia());
                                //jsonObject.put("soluong",MainActivity.arrCart.get(i).getSoluong());

                                //  } catch (JSONException e) {
                                //      e.printStackTrace();
                                //  }
                                // jsonArray.put(jsonObject);
                                //     }
                                // hashMap.put("dt",jsonArray.toString());
                                Log.d(String.valueOf(hashMap), "HMMM");
                                return hashMap;
                            }
                        };
                        queue.add(request);
                    }
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
                    hashMap.put("phone",sdt);
                    hashMap.put("address",dc);
                   // hashMap.put("tongtien",total);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        edtDiachi = findViewById(R.id.edtDiachi);
        edtsdt = findViewById(R.id.edtsdt);
        btnBack = findViewById(R.id.btnBack);
        btnXacnhan = findViewById(R.id.btnXacnhan);
        txttenkhachhang = findViewById(R.id.txttenkhachhang);
        txttenkhachhang.setText(SharedPrefManager.getInstance(Bill_Activity.this).getUsername());
       //txtiduser.setText(String.valueOf(SharedPrefManager.getInstance(Bill_Activity.this).getIduser()));
        txttotal = findViewById(R.id.txttotal);
        for(int i = 0; i<MainActivity.arrCart.size();i++){
            tongtien += MainActivity.arrCart.get(i).getGia();
        }
        txttotal.setText(tongtien + "");
    }
}
