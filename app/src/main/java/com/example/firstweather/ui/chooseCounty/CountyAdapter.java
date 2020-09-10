package com.example.firstweather.ui.chooseCounty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstweather.R;
import com.example.firstweather.db.model.County;
import com.example.firstweather.ui.chooseCity.CityAdapter;


public class CountyAdapter extends ListAdapter<County, CountyAdapter.CountyViewHolder> {

    private OnItemClick onItemClick;

    public void setOnItemClick(CountyAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    protected CountyAdapter() {
        super(new DiffUtil.ItemCallback<County>() {
            @Override
            public boolean areItemsTheSame(@NonNull County oldItem, @NonNull County newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull County oldItem, @NonNull County newItem) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public CountyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_item,parent,false);
        CountyViewHolder holder = new CountyViewHolder(view);
        holder.itemView.setOnClickListener(view1 -> {
            int position = holder.getAdapterPosition();
            County county = getItem(position);
            onItemClick.click(county);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountyViewHolder holder, int position) {
        County county = getItem(position);
        holder.textView.setText(county.getName());

    }

    class CountyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public CountyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
    public interface OnItemClick {
        void click(County county);
    }
}
