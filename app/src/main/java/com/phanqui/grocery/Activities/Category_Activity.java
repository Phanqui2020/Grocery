package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phanqui.grocery.Adapters.Category_Adapter;
import com.phanqui.grocery.Models.Category_Model;
import com.phanqui.grocery.R;
import com.phanqui.grocery.url.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category_Activity extends AppCompatActivity {

    ListView lvCate;
    ArrayList<Category_Model> arrCat;
    int idrc = 0;
    Category_Adapter category_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrCat =new ArrayList<Category_Model>();
        category_adapter = new Category_Adapter(arrCat, getApplicationContext());

        getData();
        addControls();
        addEvents();

    }

    private void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.cate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id=0;
                String TenLoaiSanPham = "";
                if (response != null)
                {
                    String bbb = String.valueOf(response.length());
                    Log.d("BBBB",bbb);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("Id");
                            TenLoaiSanPham = jsonObject.getString("TenLoaiSanPham");
                            arrCat.add(new Category_Model(Id, TenLoaiSanPham));
                            category_adapter.notifyDataSetChanged();
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
        }) {
            //post lên
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("Id", String.valueOf(idrc));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addEvents() {

        lvCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent0 = new Intent(getApplicationContext(), RauAnThanLa_Activity.class);
                        intent0.putExtra("IdLoaiSP",arrCat.get(position).getId());
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), RauLayCu_Activity.class);
                        intent1.putExtra("IdLoaiSP",arrCat.get(position).getId());
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), RauXaLach_Activity.class);
                        intent2.putExtra("IdLoaiSP",arrCat.get(position).getId());
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), RauRung_Activity.class);
                        intent3.putExtra("IdLoaiSP",arrCat.get(position).getId());
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(getApplicationContext(), RauGiaVi_Activity.class);
                        intent4.putExtra("IdLoaiSP",arrCat.get(position).getId());
                        startActivity(intent4);
                        break;
                }
            }
        });
    }

    private void addControls() {
        lvCate = findViewById(R.id.lvCate);
        lvCate.setAdapter(category_adapter);
    }

}
