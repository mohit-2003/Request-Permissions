package com.tgm.getpermission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.viewHolder> {

    private String[] list;
    private Context context;
    private onClick onClick;

    public PermissionAdapter(String[] list, Context context, onClick onClick) {
        this.list = list;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context)
                .inflate(R.layout.permission_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.num.setText(String.valueOf(holder.getAdapterPosition()+1));
        holder.permissionName.setText(list[holder.getAdapterPosition()]
                .substring(list[holder.getAdapterPosition()].lastIndexOf(".")+1));
        holder.itemView.setOnClickListener(view -> onClick.onClick(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView num, permissionName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.permissionNumber);
            permissionName = itemView.findViewById(R.id.permissionName);
        }
    }

    interface onClick {
        void onClick(int position);
    }
}
