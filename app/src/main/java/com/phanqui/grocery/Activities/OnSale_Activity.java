package com.phanqui.grocery.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.phanqui.grocery.Adapters.Sale_Adapter;
import com.phanqui.grocery.Adapters.SanPham_Adapter;
import com.phanqui.grocery.Models.SanPham_Model;
import com.phanqui.grocery.R;
import com.phanqui.grocery.url.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OnSale_Activity extends AppCompatActivity {

    RecyclerView rvSaleProduct;
    ArrayList<SanPham_Model> arrspsale;
    Sale_Adapter sale_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_sale);

        arrspsale =new ArrayList<SanPham_Model>();
        sale_adapter = new Sale_Adapter(getApplicationContext(),arrspsale);
        addControls();
        addEvents();
        getSanPhamSale();

    }

    private void getSanPhamSale() {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jr = new JsonArrayRequest(Server.sale, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    String bbb = String.valueOf(response.length());
                    Log.d("BBB",bbb);
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
                    for (int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
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
                            arrspsale.add(new SanPham_Model(Id,Title,IdLoaiSanPham,Quantity,Price,ImgUrl,Status,NhaSX,SoLuotMua,PriceSave));
                            sale_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        rq.add(jr);
    }


    private void addEvents() {
    }

    private void addControls() {
        rvSaleProduct=findViewById(R.id.rvSaleProduct);
        rvSaleProduct.setHasFixedSize(true);
        rvSaleProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rvSaleProduct.setAdapter(sale_adapter);
    }
}
