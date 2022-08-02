package com.example.roomimplement.NOTES;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.roomimplement.GPA.CgpaActivity;
import com.example.roomimplement.GPA.CgpaManActivity;
import com.example.roomimplement.NOTES.Adapters.NotesListAdapter;
import com.example.roomimplement.NOTES.Database.RoomDB;
import com.example.roomimplement.NOTES.models.Notes;
import com.example.roomimplement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_home;
    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        recyclerView=findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        searchView_home=findViewById(R.id.searchView_home);

        getSupportActionBar().setTitle(Html.fromHtml("Notes Manager"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ROOM
        database = RoomDB.getInstance(this);
        notes=database.mainDAO().getAll();

        updateRecycler(notes);

        //FLoating Action btn
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);//TODO:101- bcoz adding the data, 102- for editing.
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for(Notes singleNote: notes){
            if(singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNote);
            }
        }
        notesListAdapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101){
            if(resultCode== Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");//Sent from notestakeractivity
                //TODO:Add to DB
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll()); // Adds all the new notes

                notesListAdapter.notifyDataSetChanged();
            }
        }

        else if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                //TODO:update notes
                database.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        //populate recycler view.
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)); //GRID
        notesListAdapter=new NotesListAdapter(NotesActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(NotesActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_note", notes);// to Notetakingactivity
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){


            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pin:
                if (selectedNote.isPinned()) {
                    database.mainDAO().pin(selectedNote.getID(), false);
                } else {
                    database.mainDAO().pin(selectedNote.getID(), true);
                }

                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();
                return true;

            default:
                return false;
        }
    }
}