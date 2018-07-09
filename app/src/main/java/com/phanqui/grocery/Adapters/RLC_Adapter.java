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

public class RLC_Adapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham_Model> arrRLC;

    public RLC_Adapter(Context context, ArrayList<SanPham_Model> arrRLC) {
        this.context = context;
        this.arrRLC = arrRLC;

    }

    @Override
    public int getCount() {
        return arrRLC.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRLC.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView txtPriceRLC, txtRLC, txtPriceSaleRLC;
        public ImageView imgRLC;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_rlc,null);
            viewHolder.txtPriceRLC = view.findViewById(R.id.txtPriceRLC);
            viewHolder.txtPriceSaleRLC = view.findViewById(R.id.txtPriceSaleRLC);
            viewHolder.txtRLC = view.findViewById(R.id.txtRLC);
            viewHolder.imgRLC = view.findViewById(R.id.imgRLC);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham_Model sanPham = (SanPham_Model) getItem(position);
        viewHolder.txtRLC.setText(sanPham.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceRLC.setText("Giá: "+ decimalFormat.format(sanPham.getPrice()) + "vnđ/kg");
        viewHolder.txtPriceSaleRLC.setText("Khuyến mãi: " + decimalFormat.format(sanPham.getPriceSave()) + "vnđ/kg");
        Picasso.with(context).load(sanPham.getImgUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgRLC);
        return view;
    }
}
