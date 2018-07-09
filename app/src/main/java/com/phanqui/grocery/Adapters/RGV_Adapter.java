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

public class RGV_Adapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham_Model> arrRGV;

    public RGV_Adapter(Context context, ArrayList<SanPham_Model> arrRGV) {
        this.context = context;
        this.arrRGV = arrRGV;
    }

    @Override
    public int getCount() {
        return arrRGV.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRGV.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView txtPriceGV, txtGV, txtPriceSaleGV;
        public ImageView imgGV;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        RGV_Adapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_gv,null);
            viewHolder.txtPriceGV = view.findViewById(R.id.txtPriceGV);
            viewHolder.txtPriceSaleGV = view.findViewById(R.id.txtPriceSaleGV);
            viewHolder.txtGV = view.findViewById(R.id.txtGV);
            viewHolder.imgGV = view.findViewById(R.id.imgGV);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham_Model sanPham = (SanPham_Model) getItem(position);
        viewHolder.txtGV.setText(sanPham.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceGV.setText("Giá: " + decimalFormat.format(sanPham.getPrice()) + "vnđ/kg");
        viewHolder.txtPriceSaleGV.setText("Giá khuyến mãi: " + decimalFormat.format(sanPham.getPriceSave()) + "vnđ/kg");
        Picasso.with(context).load(sanPham.getImgUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgGV);
        return view;
    }
}