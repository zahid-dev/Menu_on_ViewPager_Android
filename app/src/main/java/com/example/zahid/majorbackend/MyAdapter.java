package com.example.zahid.majorbackend;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ProductItem> productItems;
    private Context context;

    public MyAdapter(List<ProductItem> productItems, Context context) {
        this.productItems = productItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem productItem = productItems.get(position);

        holder.productname.setText(productItem.getProductname());
        holder.productprice.setText(productItem.getProductprice());
        Picasso.get()
                .load(productItem.getProductimageurl())
                .into(holder.productimage);
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productname;
        public TextView productprice;
        public ImageView productimage;
        public ViewHolder(View itemView) {
            super(itemView);
            productname = (TextView) itemView.findViewById(R.id.productname);
            productprice = (TextView) itemView.findViewById(R.id.productprice);
            productimage = (ImageView) itemView.findViewById(R.id.productimage);
        }
    }
}
