package com.example.firstweather.ui.chooseArea;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstweather.R;
import com.example.firstweather.db.model.City;

public class CityAdapter extends ListAdapter<City, CityAdapter.CityViewHolder> {

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    protected CityAdapter() {
        super(new DiffUtil.ItemCallback<City>() {
            @Override
            public boolean areItemsTheSame(@NonNull City oldItem, @NonNull City newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull City oldItem, @NonNull City newItem) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_item,parent,false);
        CityViewHolder holder = new CityViewHolder(view);
        holder.itemView.setOnClickListener(view1 -> {
            int position = holder.getAdapterPosition();
            City city =getItem(position);
            if (onItemClick != null) {
                onItemClick.click(city);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = getItem(position);
        holder.textView.setText(city.getName());
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
    public interface OnItemClick {
        void click(City city);
    }
}
