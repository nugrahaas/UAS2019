package com.example.uas2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tambah extends AppCompatActivity {

    EditText editTextJudul, editTextDesk;
    Button btnTambah;
    ListView listViewNotes;

    //a list to store all the artist from firebase database
    List<Notes> notes;

    //our database reference object
    DatabaseReference databaseNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        databaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        editTextJudul = findViewById(R.id.et_judul);
        editTextDesk = findViewById(R.id.et_desk);
        btnTambah = findViewById(R.id.btn_tambah);

        notes = new ArrayList<>();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotes();
            }
        });


    }

    private void addNotes() {
        //getting the values to save
        String judul = editTextJudul.getText().toString().trim();
        String desk = editTextDesk.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());


        //checking if the value is provided
        if (!TextUtils.isEmpty(judul)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseNotes.push().getKey();

            //creating an Artist Object
            Notes notes= new Notes(id, currentDateandTime, judul, desk);

            //Saving the Artist
            databaseNotes.child(id).setValue(notes);

            //setting edittext to blank again
            editTextJudul.setText("");
            editTextDesk.setText("");

            Intent intent = new Intent(Tambah.this, MainActivity.class);
            startActivity(intent);

            //displaying a success toast
            Toast.makeText(this, "Notes added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }




}
