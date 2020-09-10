package com.example.firstweather.ui.chooseProvince;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstweather.R;
import com.example.firstweather.db.model.Province;

public class ProvinceAdapter extends ListAdapter<Province,ProvinceAdapter.ProvinceViewHolder> {

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    protected ProvinceAdapter() {
        super(new DiffUtil.ItemCallback<Province>() {
            @Override
            public boolean areItemsTheSame(@NonNull Province oldItem, @NonNull Province newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Province oldItem, @NonNull Province newItem) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public ProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_item,parent,false);
        ProvinceViewHolder holder = new ProvinceViewHolder(view);
        holder.itemView.setOnClickListener(view1 -> {
            int position = holder.getAdapterPosition();
            Province province = getItem(position);
            if (onItemClick != null) {
                onItemClick.click(province);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceViewHolder holder, int position) {
        Province province = getItem(position);
        holder.textView.setText(province.getName());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class ProvinceViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        private ProvinceViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
    public interface OnItemClick {
        void click (Province province);
    }

}
