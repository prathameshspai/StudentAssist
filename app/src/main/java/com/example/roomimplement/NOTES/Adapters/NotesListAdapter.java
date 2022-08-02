package com.example.roomimplement.NOTES.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomimplement.NOTES.NotesClickListener;
import com.example.roomimplement.R;
import com.example.roomimplement.NOTES.models.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder>{
    Context context;
    List<Notes> list;
    NotesClickListener listener;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textview_title.setText(list.get(position).getTitle());
        holder.textview_title.setSelected(true);            //WHATS this?

        holder.textview_notes.setText(list.get(position).getNotes());

        holder.textview_date.setText(list.get(position).getDate());
        holder.textview_date.setSelected(true);

        if(list.get(position).isPinned()){                       //NOTE this pinning
            holder.imageView_pin.setImageResource(R.drawable.ic_pin);
        }
        else{
            holder.imageView_pin.setImageResource(0); //SEE this
        }

        int color_code =  getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition())); //Dont pass direct pos
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });
    }

    //To cause cahnge of random color on refreshing
    private int getRandomColor(){
        List<Integer> colorcode = new ArrayList<>();

        colorcode.add(R.color.col1);
        colorcode.add(R.color.col2);
        colorcode.add(R.color.col3);
        colorcode.add(R.color.col4);
        colorcode.add(R.color.col5);

        Random random = new Random();
        int random_color = random.nextInt(colorcode.size());
        return colorcode.get(random_color);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder{

    //Include all objects present in Recycler view layout resource file
    CardView notes_container;
    TextView textview_title,textview_notes,textview_date;
    ImageView imageView_pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        textview_title = itemView.findViewById(R.id.textview_title);
        textview_notes = itemView.findViewById(R.id.textview_notes);
        textview_date = itemView.findViewById(R.id.textview_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);



    }
}
