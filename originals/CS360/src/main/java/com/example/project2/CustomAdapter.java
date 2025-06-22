package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//creates an adapter that is used to input multiple lines on the recycleView
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList weight_id, weight_date, weight_current, weight_goal;


    CustomAdapter(Context context, ArrayList weight_id, ArrayList weight_date, ArrayList weight_current, ArrayList weight_goal){
        this.context = context;
        this.weight_id = weight_id;
        this.weight_date = weight_date;
        this.weight_current = weight_current;
        this.weight_goal = weight_goal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // used to hold layout for table lines
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_weights, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) { // used to hold data from each line to fill in update form
        holder.weight_id_text.setText(String.valueOf(weight_id.get(position)));
        holder.weight_date_text.setText(String.valueOf(weight_date.get(position)));
        holder.weight_current_text.setText(String.valueOf(weight_current.get(position)));
        holder.weight_goal_text.setText(String.valueOf(weight_goal.get(position)));
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(weight_id.get(position)));
            intent.putExtra("date", String.valueOf(weight_date.get(position)));
            intent.putExtra("weight", String.valueOf(weight_current.get(position)));
            intent.putExtra("goal", String.valueOf(weight_goal.get(position)));
            context.startActivity(intent);
        });
    }

    //gets number of data lines to display each one
    @Override
    public int getItemCount() {
        return weight_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView weight_id_text, weight_date_text, weight_current_text, weight_goal_text ;
        LinearLayout mainLayout;

        // used to layout the items
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            weight_id_text = itemView.findViewById(R.id.weight_id_text);
            weight_date_text = itemView.findViewById(R.id.weight_date_text);
            weight_current_text = itemView.findViewById(R.id.weight_current_text);
            weight_goal_text = itemView.findViewById(R.id.weight_goal_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
