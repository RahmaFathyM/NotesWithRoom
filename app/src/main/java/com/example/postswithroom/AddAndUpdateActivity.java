package com.example.postswithroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.muddzdev.styleabletoast.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Data_Base.NoteDataBase;
import Data_Base.Note_Entity;

public class AddAndUpdateActivity extends AppCompatActivity {
    EditText add_title;
    EditText add_description;
    Intent data;
    String button;
    Toolbar toolbar;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        sp = getSharedPreferences("notes", Context.MODE_PRIVATE);
        editor = sp.edit();
        initView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        button = data.getExtras().getString("button");
        editor.putString("button", button);
        editor.putString("title", add_title.getText().toString());
        editor.putString("description", add_description.getText().toString());
       editor.putInt("id", data.getExtras().getInt("id"));
        editor.commit();
        boolean save = sp.getBoolean("save", true);
        if (save == true) {
            StyleableToast.makeText(this, "Your note is saved", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else {
            StyleableToast.makeText(this, "Your note saved in draft", Toast.LENGTH_SHORT, R.style.mytoast).show();
        }
    }


    public void initView() {
        toolbar = findViewById(R.id.includeToolbar);
        add_title = findViewById(R.id.add_title);
        add_description = findViewById(R.id.add_description);
        Button btn_save = findViewById(R.id.btn_save);
        setSupportActionBar(toolbar);
        data = getIntent();
        button = data.getExtras().getString("button");
        final int id = data.getExtras().getInt("id");
        btn_save.setText(button);
        add_title.setText(data.getExtras().getString("note_title"));
        add_description.setText(data.getExtras().getString("note_describtion"));


        if (button.equalsIgnoreCase("save")) {
            editor.putBoolean("save", false);
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean("save", true);
                    //date
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy hh:mm:ss a");
                    String date = simpleDateFormat.format(calendar.getTime());
//                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                    //validation to make sure the note is not null
                    if (add_description.getText().toString().isEmpty()) {
                        // Toast.makeText(AddAndUpdateActivity.this,"Please Enter your note",Toast.LENGTH_SHORT).show();
                        StyleableToast.makeText(AddAndUpdateActivity.this, "Make sure you enter your note", Toast.LENGTH_LONG, R.style.mytoast).show();
                        add_description.requestFocus();
                        add_description.setError("Enter your note");
                    } else {
                        Intent intent = new Intent();
                        Note_Entity note = new Note_Entity(add_title.getText().toString(), add_description.getText().toString(), date);
                        NoteDataBase.getInstance(AddAndUpdateActivity.this).noteDao().addPost(note);
//                   startActivity(new Intent(AddAndUpdateActivity.this, MainActivity.class));
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                }
            });

        } else {
            editor.putBoolean("save", false);
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean("save", true);
                    Calendar calendar = Calendar.getInstance();
                    //date by format
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE,dd-MMM-yyyy hh:mm:ss a");
                    String date = simpleDateFormat.format(calendar.getTime());
//                    String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    //validation
                    if (add_description.getText().toString().isEmpty()) {
                        add_description.setError("Enter your new note");
                        add_description.requestFocus();
                        StyleableToast.makeText(AddAndUpdateActivity.this, "Make sure you enter your note after edit it", Toast.LENGTH_LONG, R.style.mytoast).show();
                        //  Toast.makeText(AddAndUpdateActivity.this,"Please Enter your note after your Edit",Toast.LENGTH_SHORT).show();
                    } else {

                        NoteDataBase.getInstance(AddAndUpdateActivity.this).noteDao().updatePost(id, add_title.getText().toString(), add_description.getText().toString(), date);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
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


//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        String title_re =sp.getString("title"," hg");
//        String description_re = sp.getString("description","jh");
//        add_title.setText(title_re);
//        add_description.setText(description_re);
//        Toast.makeText(this,"ghfh",Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        sp= PreferenceManager.getDefaultSharedPreferences(this);
//        editor=sp.edit();
//        editor.putString("title",add_title.getText().toString());
//        editor.putString("description",add_description.getText().toString());
//        editor.apply();
//        String title_re =sp.getString("title"," hg");
//       String description_re = sp.getString("description","jh");
//       add_title.setText(title_re);
//       add_description.setText(description_re);
//Toast.makeText(this,"ghfh",Toast.LENGTH_SHORT).show();
//    }
}




