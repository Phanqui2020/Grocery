package com.phanqui.grocery.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phanqui.grocery.Models.Category_Model;
import com.phanqui.grocery.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by falcon on 07/03/2018.
 */

public class Category_Adapter  extends BaseAdapter{

    ArrayList<Category_Model> arrlsp;
    Context context;

    public Category_Adapter(ArrayList<Category_Model> arrLsp, Context context) {
        this.arrlsp =arrLsp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrlsp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrlsp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {

        TextView txtCateName;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_category, null);
            viewHolder.txtCateName = view.findViewById(R.id.txtCateName);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Category_Model category_model = (Category_Model) getItem(i);
        viewHolder.txtCateName.setText(category_model.getTenLoaiSanPham());
        return view;

    }
}