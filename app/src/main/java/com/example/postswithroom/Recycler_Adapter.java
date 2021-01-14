package com.example.postswithroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Data_Base.Note_Entity;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.Note_ViewHolder> {
    List<Note_Entity> notes;
    Recycler_onClickListener recycler_onClickListener;

    public Recycler_Adapter(List<Note_Entity> notes, Recycler_onClickListener recycler_onClickListener) {
        this.notes = notes;
        this.recycler_onClickListener = recycler_onClickListener;
    }

    @NonNull
    @Override
    public Note_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, null, false);
        Note_ViewHolder viewHolder = new Note_ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_Adapter.Note_ViewHolder holder, int position) {
        Note_Entity note = notes.get(position);
        holder.note.setText(note.getNote());
        holder.date.setText(note.getDate());
        holder.description.setText(note.getDescription());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class Note_ViewHolder extends RecyclerView.ViewHolder {
        TextView note;
        TextView date;
        TextView description;
        ImageView img_delete;

        public Note_ViewHolder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.describtion);
            date = itemView.findViewById(R.id.date);
            img_delete = itemView.findViewById(R.id.img_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycler_onClickListener.recyclerOnClick(getAdapterPosition());
                }
            });
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycler_onClickListener.imageOnClick(getAdapterPosition());
                }
            });
        }
    }
}
