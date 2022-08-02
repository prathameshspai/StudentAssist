package com.example.roomimplement.NOTES;

import androidx.cardview.widget.CardView;

import com.example.roomimplement.NOTES.models.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
