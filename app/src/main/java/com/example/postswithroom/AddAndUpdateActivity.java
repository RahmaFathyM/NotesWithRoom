package com.example.postswithroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Data_Base.NoteDataBase;
import Data_Base.Note_Entity;

public class AddAndUpdateActivity extends AppCompatActivity {
    EditText add_title;
    EditText add_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();

    }

    public void initView() {
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
                    //date
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy hh:mm:ss a");
                    String date = simpleDateFormat.format(calendar.getTime());
//                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                    String title = add_title.getText().toString();
                    String description = add_description.getText().toString();
                    //validation to make sure the note is not null
                    if (description.isEmpty()) {
                        // Toast.makeText(AddAndUpdateActivity.this,"Please Enter your note",Toast.LENGTH_SHORT).show();
                        StyleableToast.makeText(AddAndUpdateActivity.this, "Make sure you enter your note", Toast.LENGTH_LONG, R.style.mytoast).show();
                        add_description.requestFocus();
                        add_description.setError("Enter your note");
                    } else {
                        Intent intent=new Intent();
                        Note_Entity note = new Note_Entity(title, description, date);
                        NoteDataBase.getInstance(AddAndUpdateActivity.this).noteDao().addPost(note);
                   // startActivity(new Intent(AddAndUpdateActivity.this, MainActivity.class));

                       setResult(RESULT_OK,intent);
                       finish();
                    }
                }
            });
        } else {
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    //date by format
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE,dd-MMM-yyyy hh:mm:ss a");
                    String date = simpleDateFormat.format(calendar.getTime());
//                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    String title = add_title.getText().toString();
                    String description = add_description.getText().toString();
                    //validation
                    if (description.isEmpty()) {
                        add_description.setError("Enter your new note");
                        add_description.requestFocus();
                        StyleableToast.makeText(AddAndUpdateActivity.this, "Make sure you enter your note after edit it", Toast.LENGTH_LONG, R.style.mytoast).show();
                        //  Toast.makeText(AddAndUpdateActivity.this,"Please Enter your note after your Edit",Toast.LENGTH_SHORT).show();
                    } else {
Intent intent=new Intent();
                        NoteDataBase.getInstance(AddAndUpdateActivity.this).noteDao().updatePost(id, title, description, date);
                        //startActivity(new Intent(AddAndUpdateActivity.this, MainActivity.class));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            });


        }
    }
//ممكن اعملها بميثود واستدعيها فوق
//    public boolean validationNote(EditText description){
//        String inputText=description.getText().toString();
//        if(inputText.isEmpty()){
//
//            return false;
//        }else {
//            return true;
//        }}
}




