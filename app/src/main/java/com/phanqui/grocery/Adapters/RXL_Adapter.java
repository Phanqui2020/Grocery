package com.phanqui.grocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phanqui.grocery.Models.SanPham_Model;
import com.phanqui.grocery.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RXL_Adapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham_Model> arrRXL;

    public RXL_Adapter(Context context, ArrayList<SanPham_Model> arrRXL) {
        this.context = context;
        this.arrRXL = arrRXL;
    }

    @Override
    public int getCount() {
        return arrRXL.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRXL.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView txtPriceRXL, txtPriceSaleRXL, txtRXL;
        public ImageView imgRXL;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        RXL_Adapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_xl,null);
            viewHolder.txtPriceRXL = view.findViewById(R.id.txtPriceRXL);
            viewHolder.txtPriceSaleRXL = view.findViewById(R.id.txtPriceSaleRXL);
            viewHolder.txtRXL = view.findViewById(R.id.txtRXL);
            viewHolder.imgRXL = view.findViewById(R.id.imgRXL);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham_Model sanPham = (SanPham_Model) getItem(position);
        viewHolder.txtRXL.setText(sanPham.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceRXL.setText("Gia: " +decimalFormat.format(sanPham.getPrice()) + "vnđ/kg");
        viewHolder.txtPriceSaleRXL.setText("Giá khuyến mãi: "+decimalFormat.format(sanPham.getPriceSave()) + "vnđ/kg");
        Picasso.with(context).load(sanPham.getImgUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgRXL);
        return view;
    }
}