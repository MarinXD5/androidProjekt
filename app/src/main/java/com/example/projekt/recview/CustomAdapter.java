package com.example.projekt.recview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.DAO.UserData;
import com.example.projekt.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    List<UserData> userDataList;

    public CustomAdapter(List<UserData> list){
        this.userDataList = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_holder,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.email.setText(String.valueOf(userDataList.get(position).getEmail()));
        holder.score.setText(String.valueOf(userDataList.get(position).getPoints()));
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }
}
