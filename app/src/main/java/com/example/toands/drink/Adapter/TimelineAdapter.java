package com.example.toands.drink.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toands.drink.Model.Timeline;
import com.example.toands.drink.R;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineAdapterHolder> {

    private List<Timeline> data = new ArrayList<Timeline>();

    public TimelineAdapter(List<Timeline> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TimelineAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new TimelineAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapterHolder holder, int position) {
        holder.txtvDay.setText(data.get(position).day);
        holder.txtvTime.setText(data.get(position).time);
        holder.txtvFullDay.setText(data.get(position).fullDay);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TimelineAdapterHolder extends RecyclerView.ViewHolder {

        TextView txtvDay,txtvTime,txtvFullDay;

        public TimelineAdapterHolder(View itemView) {
            super(itemView);
            txtvDay = (TextView) itemView.findViewById(R.id.txtvDay);
            txtvTime = (TextView) itemView.findViewById(R.id.txtvTime);
            txtvFullDay = (TextView) itemView.findViewById(R.id.txtvDayTime);
        }
    }
}
