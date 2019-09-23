package com.example.uas2019;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotesList extends ArrayAdapter<Notes> {
    private Activity context;
    List<Notes> notes;

    public NotesList(Activity context, List<Notes> notes) {
        super(context, R.layout.activity_list_notes, notes);
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_list_notes, null, true);


        TextView textViewTanggal = (TextView) listViewItem.findViewById(R.id.tv_tanggal);
        TextView textViewJudul = (TextView) listViewItem.findViewById(R.id.tv_judul);
        TextView textViewDesk = (TextView) listViewItem.findViewById(R.id.tv_deskripsi);


        Notes note = notes.get(position);



        textViewTanggal.setText(note.getTanggal());
        textViewJudul.setText(note.getJudul());
        textViewDesk.setText(note.getDeskripsi());

        return listViewItem;
    }
}
