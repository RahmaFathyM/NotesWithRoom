package com.example.postswithroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Data_Base.NoteDataBase;
import Data_Base.Note_Entity;

public class MainActivity extends AppCompatActivity {
    List <Note_Entity> list;
    Recycler_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
initView();
        RecyclerView main_recycler= findViewById(R.id.main_recycler);

         list =NoteDataBase.getInstance(MainActivity.this).noteDao().getAllNotes();
        RecyclerView. LayoutManager layoutManager= new GridLayoutManager(MainActivity.this,2);
        main_recycler.setLayoutManager(layoutManager);
        main_recycler.setHasFixedSize(true);

          adapter =new Recycler_Adapter(list, new Recycler_onClickListener() {
            @Override
            public void recyclerOnClick(int position) {
Note_Entity note =list.get(position);
Intent intent=new Intent (MainActivity.this,AddActivity.class);
intent.putExtra("note_title",note.getNote());
intent.putExtra("note_describtion",note.getDescription());
intent.putExtra("button","update");
                intent.putExtra("id",note.getId());
startActivity(intent);
            }

            @Override
            public void imageOnClick(int position) {
                NoteDataBase.getInstance(MainActivity.this).noteDao().deleteNoteById(list.get(position).getId());
                list.remove(list.get(position));
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
            }

        });
        main_recycler.setAdapter(adapter);

    }


    public void initView(){

        FloatingActionButton fab_add=findViewById(R.id.fab_add);
        FloatingActionButton fab_delete=findViewById(R.id.fab_delete);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,AddActivity.class);
                intent.putExtra("note_title","");
                intent.putExtra("note_describtion","");
                intent.putExtra("button","save");
                intent.putExtra("id","");
                startActivity(intent);
              startActivity(intent);
            }

        });
        fab_delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NoteDataBase.getInstance(MainActivity.this).noteDao().deleteAllNOtes(list);
                        list.clear();

                        adapter.notifyDataSetChanged();

                    }
                }
        );
    }
   }
