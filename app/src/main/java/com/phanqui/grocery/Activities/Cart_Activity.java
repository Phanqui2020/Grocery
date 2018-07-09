package com.phanqui.grocery.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phanqui.grocery.Adapters.Cart_Adapter;
import com.phanqui.grocery.R;

import java.text.DecimalFormat;

public class Cart_Activity extends AppCompatActivity {

    ListView lvCart;
    TextView txtThongbao;
    static TextView txtTotal;
    Button btnPay, btnBuy;
    static long GiaKM=0;
    Cart_Adapter cart_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adđControls();
        addEvents();
        CheckData();
        Total();
        deleteProduct();
    }

    private void addEvents() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrCart.size() > 0) {
                     if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                     Intent intent = new Intent(getApplicationContext(),Bill_Activity.class);
                     startActivity(intent);
                      }else {
                         Toast.makeText(getApplicationContext(),"Hãy đăng nhập để thanh toán",Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(getApplicationContext(),SignIn_Activity.class);
                         startActivity(intent);
                      }

                } else {
                    Toast.makeText(getApplicationContext(), "Giỏ hàng trống", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void deleteProduct() {
        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cart_Activity.this);
                builder.setTitle("Xóa sản phẩm!");
                builder.setMessage("Bạn có chắc chắn xóa sản phẩm này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.arrCart.size() <= 0) {
                            txtThongbao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.arrCart.remove(position);
                            cart_adapter.notifyDataSetChanged();
                            Total();
                            if (MainActivity.arrCart.size() <= 0) {
                                txtThongbao.setVisibility(View.VISIBLE);
                            } else {
                                txtThongbao.setVisibility(View.INVISIBLE);
                                cart_adapter.notifyDataSetChanged();
                                Total();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cart_adapter.notifyDataSetChanged();
                        Total();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void Total() {

        long tongtien = 0;
        for (int i = 0; i < MainActivity.arrCart.size(); i++) {
            GiaKM = MainActivity.arrCart.get(i).getGiaKM();
            tongtien += MainActivity.arrCart.get(i).getGia();
            /*if(GiaKM<=0){
                tongtien += MainActivity.arrCart.get(i).getGia();
            }else tongtien += GiaKM;*/

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotal.setText(decimalFormat.format(tongtien) + " vnđ");
    }

    private void CheckData() {

        if (MainActivity.arrCart.size() <= 0) {
            cart_adapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
        } else {
            cart_adapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);

        }
    }


    private void adđControls() {
        lvCart = findViewById(R.id.lvCart);
        txtThongbao = findViewById(R.id.txtThongbao);
        txtTotal = findViewById(R.id.txtTotal);
        btnPay = findViewById(R.id.btnPay);
        btnBuy = findViewById(R.id.btnBuy);
        cart_adapter = new Cart_Adapter(Cart_Activity.this, MainActivity.arrCart);
        lvCart.setAdapter(cart_adapter);
    }
}