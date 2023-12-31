package com.example.projekt.recview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView email, score;

    public CustomViewHolder(@NonNull View itemView){
        super(itemView);

        this.email = itemView.findViewById(R.id.emailViewHolder);
        this.score = itemView.findViewById(R.id.scoreViewHolder);
    }
}
