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

public class RR_Adapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham_Model> arrRR;

    public RR_Adapter(Context context, ArrayList<SanPham_Model> arrRR) {
        this.context = context;
        this.arrRR = arrRR;

    }

    @Override
    public int getCount() {
        return arrRR.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRR.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView txtPriceRR, txtRR, txtPriceSaleRR;
        public ImageView imgRR;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        RR_Adapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_rr,null);
            viewHolder.txtPriceRR = view.findViewById(R.id.txtPriceRR);
            viewHolder.txtPriceSaleRR = view.findViewById(R.id.txtPriceSaleRR);
            viewHolder.txtRR = view.findViewById(R.id.txtRR);
            viewHolder.imgRR = view.findViewById(R.id.imgRR);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham_Model sanPham = (SanPham_Model) getItem(position);
        viewHolder.txtRR.setText(sanPham.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceRR.setText("Giá: " + decimalFormat.format(sanPham.getPrice()) + "vnđ/kg");
        viewHolder.txtPriceSaleRR.setText("Giá khuyến mãi: "+decimalFormat.format(sanPham.getPriceSave()) + "vnđ/kg");
        Picasso.with(context).load(sanPham.getImgUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgRR);
        return view;
    }
}

