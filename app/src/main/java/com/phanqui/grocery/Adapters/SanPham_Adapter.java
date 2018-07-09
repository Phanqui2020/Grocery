package com.phanqui.grocery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
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

public class SanPham_Adapter extends RecyclerView.Adapter<SanPham_Adapter.ItemHolder> {

    Context context;
    ArrayList<SanPham_Model> arrsp;

    public SanPham_Adapter(Context context, ArrayList<SanPham_Model> arrsp) {
        this.context = context;
        this.arrsp = arrsp;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sanphammoi,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position){
        SanPham_Model sanPham_model = arrsp.get(position);
        holder.Title.setText(sanPham_model.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.Price.setText(decimalFormat.format(sanPham_model.getPrice()) + " vnđ");
        Picasso.with(context).load(sanPham_model.getImgUrl())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(holder.Img);
        int GiaKMchitiet = sanPham_model.getPriceSave();
        if(GiaKMchitiet<=0){
            holder.imgSaleIco.setVisibility(View.INVISIBLE);
        }else holder.imgSaleIco.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if(arrsp==null)
            return 0;
        return arrsp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView Title, Price;
        public ImageView Img,imgSaleIco;


        public ItemHolder(View itemView) {
            super(itemView);
            Img = itemView.findViewById(R.id.Img);
            Title = itemView.findViewById(R.id.Title);
            Price = itemView.findViewById(R.id.Price);
            imgSaleIco = itemView.findViewById(R.id.imgSaleIco);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(context, Detail_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tt",arrsp.get(getAdapterPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
