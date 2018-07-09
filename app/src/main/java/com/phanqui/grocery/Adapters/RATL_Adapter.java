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
import java.util.Locale;

public class RATL_Adapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham_Model> arrRATL;

    public RATL_Adapter(Context context, ArrayList<SanPham_Model> arrRATL) {
        this.context = context;
        this.arrRATL = arrRATL;
    }

    @Override
    public int getCount() {
        return arrRATL.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRATL.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView txtPriceRATL, txtRATL, txtPriceSaleRATL;
        public ImageView imgRATL;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_rananthanla,null);
            viewHolder.txtPriceRATL = view.findViewById(R.id.txtPriceRATL);
            viewHolder.txtPriceSaleRATL = view.findViewById(R.id.txtPriceSaleRATL);
            viewHolder.txtRATL = view.findViewById(R.id.txtRATL);
            viewHolder.imgRATL = view.findViewById(R.id.imgRATL);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham_Model sanPham = (SanPham_Model) getItem(position);
        viewHolder.txtRATL.setText(sanPham.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceRATL.setText("Giá: " +decimalFormat.format(sanPham.getPrice()) + "vnđ/kg");
        viewHolder.txtPriceSaleRATL.setText("Giá khuyến mãi:" + decimalFormat.format(sanPham.getPriceSave()) + "vnđ/kg");
        Picasso.with(context).load(sanPham.getImgUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgRATL);
        return view;
    }
}
