package com.phanqui.grocery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phanqui.grocery.Activities.Detail_Activity;
import com.phanqui.grocery.Models.SanPham_Model;
import com.phanqui.grocery.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Sale_Adapter extends RecyclerView.Adapter<Sale_Adapter.ItemHolder> {

    Context context;
    ArrayList<SanPham_Model> arrspsale;

    public Sale_Adapter(Context context, ArrayList<SanPham_Model> arrspsale) {
        this.context = context;
        this.arrspsale = arrspsale;
    }

    @Override
    public Sale_Adapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_saleproduct,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(Sale_Adapter.ItemHolder holder, int position){
        SanPham_Model sanPham_model = arrspsale.get(position);
        holder.TitleSale.setText(sanPham_model.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.PriceSale.setText("Giá sale: "+ decimalFormat.format(sanPham_model.getPriceSave()) + "vnđ");
        DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
        holder.PriceOld.setText("Giá gốc: " + decimalFormat.format(sanPham_model.getPrice()) + "vnđ");
        Picasso.with(context).load(sanPham_model.getImgUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(holder.ImgSale);
    }

    @Override
    public int getItemCount() {
        if(arrspsale ==null)
            return 0;
        return arrspsale.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView TitleSale, PriceSale, PriceOld;
        public ImageView ImgSale;

        public ItemHolder(View itemView) {
            super(itemView);
            ImgSale = itemView.findViewById(R.id.ImgSale);
            TitleSale = itemView.findViewById(R.id.TitleSale);
            PriceSale = itemView.findViewById(R.id.PriceSale);
            PriceOld = itemView.findViewById(R.id.PriceOld);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(context, Detail_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tt",arrspsale.get(getAdapterPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
