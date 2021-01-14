package com.example.postswithroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Data_Base.NoteDataBase;
import Data_Base.Note_Entity;

public class AddActivity extends AppCompatActivity {
    EditText add_title;
    EditText add_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add_title = findViewById(R.id.add_title);
        add_description = findViewById(R.id.add_description);
        Button btn_save = findViewById(R.id.btn_save);

        final Intent data = getIntent();
        String title = data.getExtras().getString("note_title");
        String description = data.getExtras().getString("note_describtion");
        String button = data.getExtras().getString("button");
        final int id = data.getExtras().getInt("id");
        btn_save.setText(button);
        add_title.setText(title);
        add_description.setText(description);


        if (button.equalsIgnoreCase("save")) {
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, dd-MMM-yyyy hh:mm:ss a");
                    String date = simpleDateFormat.format(calendar.getTime());
//                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    String title = add_title.getText().toString();
                    String description = add_description.getText().toString();

                    Note_Entity note = new Note_Entity(title, description, date);
                    NoteDataBase.getInstance(AddActivity.this).noteDao().addPost(note);
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                }
            });
        } else {
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE,dd-MMM-yyyy hh:mm:ss a");
                    String date = simpleDateFormat.format(calendar.getTime());
//                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    String title = add_title.getText().toString();
                    String description = add_description.getText().toString();

//                    Note_Entity note=new Note_Entity(title,description,date);
                    NoteDataBase.getInstance(AddActivity.this).noteDao().updatePost(id, title, description, date);
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                }
            });


        }
//    public void initView(){
//        EditText add_title=findViewById(R.id.add_title);
//        EditText add_description=findViewById(R.id.add_description);
//        Button btn_save =findViewById(R.id.btn_save);
//
//        final Intent data = getIntent();
//        String title = data.getExtras().getString("note_title");
//        String description= data.getExtras().getString("note_describtion");
//        String button =data.getExtras().getString("button");
//        final int id= data.getExtras().getInt("id");
//        btn_save.setText(button);
//        add_title.setText(title);
//        add_description.setText(description);
//
//
//        if(button.equalsIgnoreCase("save")){
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar=Calendar.getInstance();
//                String date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//              String title=  add_title.getText().toString();
//                String description=  add_description.getText().toString();
//
//                Note_Entity note=new Note_Entity(title,description,date);
//                NoteDataBase.getInstance(AddActivity.this).noteDao().addPost(note);
//                startActivity(new Intent(AddActivity.this,MainActivity.class));
//            }
//        });
//    }
//    else {
//            btn_save.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Calendar calendar=Calendar.getInstance();
//                    String date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//                    String title=  add_title.getText().toString();
//                    String description=  add_description.getText().toString();
//
//                   // Note_Entity note=new Note_Entity(title,description,date);
//                    NoteDataBase.getInstance(AddActivity.this).noteDao().updatePost(id,title,description,date);
//                    startActivity(new Intent(AddActivity.this,MainActivity.class));
//    }
//    });}}
    }


}
