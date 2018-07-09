package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phanqui.grocery.Adapters.Category_Adapter;
import com.phanqui.grocery.Adapters.SanPham_Adapter;
import com.phanqui.grocery.Models.Cart_Model;
import com.phanqui.grocery.Models.Category_Model;
import com.phanqui.grocery.Models.SanPham_Model;
import com.phanqui.grocery.R;
import com.phanqui.grocery.url.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewFlipper vfSaleInf;
    TextView txtName,txtABC;
    RecyclerView rvNewPro;
    ListView lvCat;
    ArrayList<Category_Model> arrCat;
    Category_Adapter categoryAdapter;
    ArrayList<SanPham_Model> arrsp;
    SanPham_Adapter sanPham_adapter;
    public static ArrayList<Cart_Model> arrCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cart_Activity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        arrsp =new ArrayList<SanPham_Model>();
        sanPham_adapter = new SanPham_Adapter(getApplicationContext(),arrsp);
        getSanPham();
        addControl();
        addEvents();
        actionViewFlipper();

        //getCate();

    }

    private void getSanPham() {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jr = new JsonArrayRequest(Server.spm, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
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
                            arrsp.add(new SanPham_Model(Id,Title,IdLoaiSanPham,Quantity,Price,ImgUrl,Status,NhaSX,SoLuotMua,PriceSave));
                            sanPham_adapter.notifyDataSetChanged();
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

    private void actionViewFlipper() {
        ArrayList<Integer> Khuyenmai = new ArrayList<>();
        Khuyenmai.add(R.drawable.sign_in);
        Khuyenmai.add(R.drawable.noimg);
        Khuyenmai.add(R.drawable.login1);

        for (int i =0; i < Khuyenmai.size(); i++){
            ImageView img = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(Khuyenmai.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            vfSaleInf.addView(img);
        }
        vfSaleInf.setFlipInterval(5000);
        vfSaleInf.setAutoStart(true);
        Animation anim_siR = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation anim_soR = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        vfSaleInf.setInAnimation(anim_siR);
        vfSaleInf.setOutAnimation(anim_soR);
    }

    private void addEvents() {
    }

    private void addControl() {

        vfSaleInf = findViewById(R.id.vfSaleInf);
        rvNewPro = findViewById(R.id.rvNewPro);
        //lvCat = findViewById(R.id.lvCat);
       // arrCat = new ArrayList<>();
        //categoryAdapter = new Category_Adapter(arrCat, getApplicationContext());
        //lvCat.setAdapter(categoryAdapter);

        rvNewPro.setHasFixedSize(true);
        rvNewPro.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rvNewPro.setAdapter(sanPham_adapter);
        if(arrCart!=null){

        }else { //nếu chưa thì cấp phát bộ nhớ
            arrCart = new ArrayList<>();
        }

        txtName=findViewById(R.id.txtName);
        txtABC=findViewById(R.id.txtABC);
        txtABC.setText(SharedPrefManager.getInstance(MainActivity.this).getUsername());


   /*     txtName.setText("ABC");
        if(!(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())){
            txtName.setText("ABC");
        }
        else if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
            txtName.setText(SharedPrefManager.getInstance(MainActivity.this).getUsername());
        }*/


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            SharedPrefManager.getInstance(this).logout();
            Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent3);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navMyAccount) {
            if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                Toast.makeText(getApplicationContext(),"Bạn đã đăng nhập", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(MainActivity.this, SignIn_Activity.class);
                startActivity(intent);
            }
        } else if (id == R.id.navOnSale) {
            Intent intent = new Intent(MainActivity.this, OnSale_Activity.class);
            startActivity(intent);

        } else if (id == R.id.navCate) {
            Intent intent = new Intent(MainActivity.this, Category_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Rau BB";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Rau hữu cơ");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.navFeedBack) {
            Intent intent = new Intent(MainActivity.this, FeedBack_Activity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
