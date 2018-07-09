package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phanqui.grocery.Adapters.RATL_Adapter;
import com.phanqui.grocery.Adapters.RGV_Adapter;
import com.phanqui.grocery.Models.SanPham_Model;
import com.phanqui.grocery.R;
import com.phanqui.grocery.url.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RauGiaVi_Activity extends AppCompatActivity {


    ListView lvRGV;
    ArrayList<SanPham_Model> arrRGV;
    RGV_Adapter rgv_adapter;
    int idrl =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rau_gia_vi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RauGiaVi_Activity.this, Cart_Activity.class);
                startActivity(intent);
            }
        });addControls();
        addEvents();
        GetIdLoaiSP();
        getData();
        lvItemClick();

    }

    private void GetIdLoaiSP() {
        idrl = getIntent().getIntExtra("IdLoaiSP",-1);
        Log.d("IdLoaiSP", idrl + "");
    }

    private void lvItemClick() {
        lvRGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Detail_Activity.class);
                intent.putExtra("tt", arrRGV.get(position));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id = 0;
                String Title = "";
                int IdLoaiSanPham = 0;
                int Quantity = 0;
                int Price = 0;
                String ImgUrl = "";
                int Status = 0;
                String NhaSX = "";
                int SoLuotMua = 0;
                int PriceSave = 0;
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("Id");
                            Title = jsonObject.getString("TenSanPham");
                            IdLoaiSanPham = jsonObject.getInt("IdLoaiSP");
                            Quantity = jsonObject.getInt("SoLuongTrongKho");
                            Price = jsonObject.getInt("Gia");
                            ImgUrl = jsonObject.getString("HinhAnh");
                            Status = jsonObject.getInt("Status");
                            NhaSX = jsonObject.getString("NhaSX");
                            SoLuotMua = jsonObject.getInt("SoLuotMua");
                            PriceSave = jsonObject.getInt("GiaKhuyenMai");
                            arrRGV.add(new SanPham_Model(Id,Title,IdLoaiSanPham,Quantity,Price,ImgUrl,Status,NhaSX,SoLuotMua,PriceSave));
                            rgv_adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("id",String.valueOf(idrl));
                Log.d("idratl", String.valueOf(param));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void addEvents() {
    }

    private void addControls() {

        lvRGV = findViewById(R.id.lvRGV);
        arrRGV = new ArrayList<>();
        rgv_adapter = new RGV_Adapter(getApplicationContext(), arrRGV);
        lvRGV.setAdapter(rgv_adapter);
    }


}
