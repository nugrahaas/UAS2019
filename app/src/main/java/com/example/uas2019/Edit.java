package com.example.uas2019;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Edit extends AppCompatActivity {

    EditText editTextJudul, editTextDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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

        editTextJudul = findViewById(R.id.et_judul);
        editTextDeskripsi = findViewById(R.id.et_desk);
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
                String judul = editTextJudul.getText().toString().trim();
                String desk = editTextDeskripsi.getText().toString().trim();
                if (!TextUtils.isEmpty(judul)) {
                    updateArtist(artistId, currentDateandTime,  judul, desk);
                    b.dismiss();
                }
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
