package com.formaxit.kmrecharge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.formaxit.kmrecharge.Model.Prepaid;
import com.formaxit.kmrecharge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.OperatorViewHolder> {

    private Context context;
    private List<Prepaid> operatorList = new ArrayList<>();
    OnOperatorItemClickListener itemClickListener;

    public OperatorAdapter(Context context, OnOperatorItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public interface OnOperatorItemClickListener {
        void onOperatorItemClick(int position);
    }

    @NonNull
    @Override
    public OperatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OperatorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.operator_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OperatorViewHolder holder, int position) {

        Picasso.get().load(operatorList.get(position).getImage()).error(R.drawable.frp_icon).into(holder.imageView);
        holder.textView.setText(operatorList.get(position).getOperatorName());
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void setOperatorList(List<Prepaid> operatorList) {
        this.operatorList = operatorList;
        notifyDataSetChanged();
    }

    //Get operator item
    public Prepaid getPrepaidItem(int position) {
        return operatorList.get(position);
    }

    public class OperatorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        public OperatorViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.operator_item_image);
            textView = itemView.findViewById(R.id.operator_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            itemClickListener.onOperatorItemClick(position);
        }
    }
}
