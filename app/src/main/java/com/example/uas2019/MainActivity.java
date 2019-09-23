package com.example.uas2019;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_JUDUL = "noteJudul";
    public static final String ARTIST_ID = "noteID";
    public static final String ARTIST_DESK = "noteDesk";


    EditText editTextJudul, editTextDeskripsi;
    ListView listViewNotes;

    //a list to store all the artist from firebase database
    List<Notes> notes;

    //our database reference object
    DatabaseReference databaseNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        listViewNotes = findViewById(R.id.list_view_notes);

        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Notes note = notes.get(position);

                showUpdateDeleteDialog(note.getId(), note.getJudul());

                return true;
            }
        });

        notes = new ArrayList<>();

//        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //getting the selected artist
//                Notes note = notes.get(i);
//
//                //creating an intent
//                Intent intent = new Intent(getApplicationContext(), Tambah.class);
//
//                //putting artist name and id to intent
//                intent.putExtra(ARTIST_ID, note.getId());
//                intent.putExtra(ARTIST_JUDUL, note.getJudul());
//                intent.putExtra(ARTIST_DESK, note.getDeskripsi());
//
//                //starting the activity with intent
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                notes.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Notes note = postSnapshot.getValue(Notes.class);
                    //adding artist to the list
                    notes.add(note);
                }

                //creating adapter
                NotesList notesAdapter = new NotesList(MainActivity.this, notes);
                //attaching adapter to the listview
                listViewNotes.setAdapter(notesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
//
    }

    public void tambahNotes(View view) {
        Intent intent = new Intent(MainActivity.this, Tambah.class);
        startActivity(intent);

    }
    private boolean updateArtist(String id, String tanggal, String judul, String desk) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(id);

        //updating artist
        Notes note = new Notes(id,tanggal, judul,desk);
        dR.setValue(note);
        Toast.makeText(getApplicationContext(), "Notes Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(id);

        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Notes Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        editTextJudul = dialogView.findViewById(R.id.et_judul);
        editTextDeskripsi = dialogView.findViewById(R.id.et_desk);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        final String currentDateandTime = sdf.format(new Date());


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, Edit.class);
                    startActivity(intent);
                }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(artistId);
                b.dismiss();

            }
        });
    }
}
