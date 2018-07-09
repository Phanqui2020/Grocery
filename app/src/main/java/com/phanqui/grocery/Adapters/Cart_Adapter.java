package com.phanqui.grocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phanqui.grocery.Activities.Cart_Activity;
import com.phanqui.grocery.Activities.MainActivity;
import com.phanqui.grocery.Models.Cart_Model;
import com.phanqui.grocery.Models.Category_Model;
import com.phanqui.grocery.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Cart_Model> arrCart;
   // long giahientai=0;

    public Cart_Adapter(Context context, ArrayList<Cart_Model> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @Override
    public int getCount() {
        return arrCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtGiohang,txtGiagh;
        public ImageView imgGiohang;
        public Button btnMinus,btnPlus,btnValue;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_cart,null);
            viewHolder.txtGiohang = view.findViewById(R.id.txtTitleCart);
            viewHolder.txtGiagh = view.findViewById(R.id.txtPriceCart);
            viewHolder.imgGiohang = view.findViewById(R.id.imgCart);
            viewHolder.btnMinus = view.findViewById(R.id.btnMinus);
            viewHolder.btnPlus = view.findViewById(R.id.btnPlus);
            viewHolder.btnValue = view.findViewById(R.id.btnValue);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart_Model gioHang = (Cart_Model) getItem(position);
        viewHolder.txtGiohang.setText(gioHang.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiagh.setText(decimalFormat.format(gioHang.getGia())+ "vnđ");
        /*long GiaKM= gioHang.getGiaKM();
        if(GiaKM<=0){
            viewHolder.txtGiagh.setText(decimalFormat.format(gioHang.getGia())+ "vnđ");
        }else viewHolder.txtGiagh.setText(decimalFormat.format(GiaKM)+ "vnđ");*/
        Picasso.with(context).load(gioHang.getHinhanh())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgGiohang);
        viewHolder.btnValue.setText(gioHang.getSoluong()+ "");
        int sl = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if(sl>= 10){
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }else if(sl<=1)
        {
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        }else if(sl>=1)
        {
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) + 1;
                int slhientai = MainActivity.arrCart.get(position).getSoluong();
                long GiaKM = MainActivity.arrCart.get(position).getGiaKM();
                long Gia = MainActivity.arrCart.get(position).getGia();
               /* if(giahientai<=0){
                    if(GiaKM>0)
                        giahientai= GiaKM;
                    else
                        giahientai = Gia;
                }else giahientai = MainActivity.arrCart.get(position).getGia();*/

                long giahientai = MainActivity.arrCart.get(position).getGia();
                MainActivity.arrCart.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.arrCart.get(position).setGia(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiagh.setText(decimalFormat.format(giamoinhat)+ "vnđ");
                Cart_Activity.Total();
                if(slmoinhat>9){
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) - 1;
                int slhientai = MainActivity.arrCart.get(position).getSoluong();
                long GiaKM = MainActivity.arrCart.get(position).getGiaKM();
               /* long giahientai=0;
                if(GiaKM<=0)
                    giahientai= MainActivity.arrCart.get(position).getGia();
                else
                    giahientai = MainActivity.arrCart.get(position).getGiaKM();*/
                long giahientai = MainActivity.arrCart.get(position).getGia();
                MainActivity.arrCart.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.arrCart.get(position).setGia(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiagh.setText(decimalFormat.format(giamoinhat)+ "vnđ");
                Cart_Activity.Total();
                if(slmoinhat<2){
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return view;
    }
}