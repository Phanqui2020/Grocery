package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phanqui.grocery.Models.Cart_Model;
import com.phanqui.grocery.Models.SanPham_Model;
import com.phanqui.grocery.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Detail_Activity extends AppCompatActivity {

    ImageView imgDetail;
    TextView txtNameDetail,txtPriceDetail,txtPriceSaleDetail;
    Spinner spnQuantityDetail;
    Button btnAddToCard;

    int id = 0;
    String Tenchitiet= "";
    int Giachitiet = 0;
    int GiaKMchitiet = 0;
    int GiaFinal = 0;
    int quantity=0;
    String Hinhanhchitiet= "";
    String thongtinsanpham="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_Activity.this, Cart_Activity.class);
                startActivity(intent);
            }
        });

        addControls();
        addEvents();
        GetDetails();
        CatchEventSpinner();

    }

    private void CatchEventSpinner() {

        SanPham_Model sp = (SanPham_Model) getIntent().getSerializableExtra("tt");
        quantity = sp.getQuantity();
        Integer [] sl = new Integer[quantity];
        int counter = 0;
        for (int j = 0; j < sl.length; j++) {
            Integer i = sl[j]; // i is null here.
            i = counter++; // Assigns to i. Does not assign to the array.
        }



        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,sl);
        spnQuantityDetail.setAdapter(arrayAdapter);
    }

        private void GetDetails() {
        //
        SanPham_Model sp = (SanPham_Model) getIntent().getSerializableExtra("tt");
        //

        id = sp.getId();
        Tenchitiet = sp.getTitle();
        Giachitiet = sp.getPrice();
        GiaKMchitiet = sp.getPriceSave();
        if(GiaKMchitiet>0){
            GiaFinal = GiaKMchitiet;
        }else{
            GiaFinal = Giachitiet;
        }
        Hinhanhchitiet = sp.getImgUrl();

        txtNameDetail.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPriceDetail.setText(decimalFormat.format(Giachitiet) + " vnđ/kg");
        Picasso.with(getApplicationContext()).load(Hinhanhchitiet).into(imgDetail);
        if(GiaKMchitiet<=0){
            txtPriceSaleDetail.setText("");
        }else
            txtPriceSaleDetail.setText("Khuyến mãi: " + decimalFormat.format(GiaKMchitiet) + " vnđ/kg");


    }

    private void addEvents() {

        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Muahang();
            }
        });
    }

    private void addControls() {

        imgDetail = findViewById(R.id.imgDetail);
        txtNameDetail =  findViewById(R.id.txtNameDetail);
        txtPriceDetail =  findViewById(R.id.txtPriceDetail);
        txtPriceSaleDetail = findViewById(R.id.txtPriceSaleDetail);
        spnQuantityDetail =  findViewById(R.id.spnQuantityDetail);
        btnAddToCard =  findViewById(R.id.btnAddToCart);
    }

    private void Muahang() {
        //mảng >0
        if(MainActivity.arrCart.size()>0){
            int sl = Integer.parseInt(spnQuantityDetail.getSelectedItem().toString());
            boolean bl = false;
            for(int i =0; i<MainActivity.arrCart.size();i++){
                if(MainActivity.arrCart.get(i).getIdsanpham() == id){
                    MainActivity.arrCart.get(i).setSoluong(MainActivity.arrCart.get(i).getSoluong()+sl);
                    if(MainActivity.arrCart.get(i).getSoluong()>=10){
                        MainActivity.arrCart.get(i).setSoluong(10);
                    }
                    MainActivity.arrCart.get(i).setGia(GiaFinal * MainActivity.arrCart.get(i).getSoluong());
                    bl =true;
                }
            }
            if (bl==false){
                int solluong = Integer.parseInt(spnQuantityDetail.getSelectedItem().toString());
                long Giamoi = solluong * GiaFinal;
                MainActivity.arrCart.add(new Cart_Model(id,Tenchitiet,Giamoi,GiaKMchitiet,Hinhanhchitiet,solluong));

            }

        }else {
            int soluong = Integer.parseInt(spnQuantityDetail.getSelectedItem().toString());
            long Giamoi = soluong * GiaFinal;
            MainActivity.arrCart.add(new Cart_Model(id,Tenchitiet,Giamoi,GiaKMchitiet,Hinhanhchitiet,soluong));
        }
        Intent intent = new Intent(getApplicationContext(), Cart_Activity.class);
        startActivity(intent);
    }

}
