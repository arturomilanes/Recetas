package com.example.recetas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<RecetasModel> recetasModel;

    OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener= clickListener;
    }
    public RecyclerViewAdapter(Context context, ArrayList<RecetasModel> recetasModel, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.recetasModel = recetasModel;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recetas_cardview, parent,false);

        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(recetasModel.get(position).getRecipe_name());
        holder.imgView.setImageBitmap(recetasModel.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return recetasModel.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView textView;
        ImageView deleteView;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface, OnItemClickListener listener) {
            super(itemView);

            imgView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            deleteView = itemView.findViewById(R.id.delete_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
