package com.example.postswithroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Data_Base.NoteDataBase;
import Data_Base.Note_Entity;

public class MainActivity extends AppCompatActivity {
    List<Note_Entity> list;
    Recycler_Adapter adapter;
    Note_Entity note_entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    public void initView() {

        FloatingActionButton fab_add = findViewById(R.id.fab_add);
        FloatingActionButton fab_delete = findViewById(R.id.fab_delete);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAndUpdateActivity.class);
                intent.putExtra("note_title", "");
                intent.putExtra("note_describtion", "");
                intent.putExtra("button", "save");
                intent.putExtra("id", "");
            startActivityForResult(intent,1);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                final RecyclerView main_recycler = findViewById(R.id.main_recycler);

                list = NoteDataBase.getInstance(MainActivity.this).noteDao().getAllNotes();
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                main_recycler.setLayoutManager(layoutManager);
                main_recycler.setHasFixedSize(true);

                adapter = new Recycler_Adapter(list, new Recycler_onClickListener() {
                    @Override
                    public void recyclerOnClick(int position) {
                        Note_Entity note = list.get(position);
                        Intent intent = new Intent(MainActivity.this, AddAndUpdateActivity.class);
                        intent.putExtra("note_title", note.getNote());
                        intent.putExtra("note_describtion", note.getDescription());
                        intent.putExtra("button", "update");
                        intent.putExtra("id", note.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void imageOnClick(final int position) {

                        final int itemid=list.get(position).getId();
//                note_entity=new Note_Entity(NoteDataBase.getInstance(MainActivity.this).noteDao().getTitleById(itemid),
//                        NoteDataBase.getInstance(MainActivity.this).noteDao().getDescriptionById(itemid),
//                        NoteDataBase.getInstance(MainActivity.this).noteDao().getDateById(itemid));
//                NoteDataBase.getInstance(MainActivity.this).noteDao().deleteNoteById(list.get(position).getId());
//                list.remove(list.get(position));
//                adapter.notifyItemRemoved(position);
//                adapter.notifyDataSetChanged();
//                Snackbar snackbar=   Snackbar.make(main_recycler,"Are you sure to delete note",Snackbar.LENGTH_LONG).setAction("Delete", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        list.remove(list.get(position));
//                        adapter.notifyItemRemoved(position);
//                        adapter.notifyDataSetChanged();
//                        NoteDataBase.getInstance(MainActivity.this).noteDao().deleteNoteById(itemid);
////                                         list.add(position,note_entity);
////                                         adapter.notifyItemInserted(position);
////                                         adapter.notifyDataSetChanged();
////                                Toast.makeText(MainActivity.this,NoteDataBase.getInstance(MainActivity.this).noteDao().getTitleById(itemid)+"fed" , Toast.LENGTH_SHORT).show();
////                                NoteDataBase.getInstance(MainActivity.this).noteDao().addPost(note_entity);
//                    }}).setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                snackbar.show();
//                if (!clickAction){ list.add(position,note_entity);
//                    adapter.notifyItemInserted(position);
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(MainActivity.this,NoteDataBase.getInstance(MainActivity.this).noteDao().getTitleById(itemid)+"fed" , Toast.LENGTH_SHORT).show();}
//                else{ list.remove(list.get(position));
//                    adapter.notifyItemRemoved(position);
//                    adapter.notifyDataSetChanged();
//                    NoteDataBase.getInstance(MainActivity.this).noteDao().deleteNoteById(itemid);
//                    }


                        //undelete by AlertDialog (ok)
                        // to make sure the user want delete note or not
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Delete")
                                .setMessage("Are you sure to delete this note?")
                                //default colorAccent
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positive_btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positive_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                NoteDataBase.getInstance(MainActivity.this).noteDao().deleteNoteById(list.get(position).getId());
                                list.remove(list.get(position));
                                adapter.notifyItemRemoved(position);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });

                        Button negative_btn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        negative_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // to exit alertDialog
                                dialog.dismiss();
                            }
                        });
//
//            }
                    }
                });
                main_recycler.setAdapter(adapter);


            }
        }
    }
}

