package student.lindseychill.mobileapplicationprojectwgu.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Controllers.NotesController;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.NoteWithCourse;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolderN>{
    // Initialize variable
    private List<NoteWithCourse> noteList;
    private Activity context;
    private UniversityDB db;
    private static final String TAG = "POPCORN";

    //create constructor
    @SuppressLint("NotifyDataSetChanged")
    public NoteAdapter(Activity context, List<NoteWithCourse> noteList){
        this.context = context;
        this.noteList = noteList;
        notifyDataSetChanged();
        Log.v(TAG, "adapter constructed");
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolderN onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_note,parent,false);
        Log.v(TAG, "adapter created");
        return new NoteAdapter.ViewHolderN(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolderN holder, int position) {
        NoteWithCourse note = noteList.get(position);
        //db = UniversityDB.getInstance(context);
        Log.v(TAG, "holder");
        holder.noteName.setText(note.getNote().getName());
        holder.noteCard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                NoteWithCourse n = noteList.get(holder.getAdapterPosition());
                Long id = n.getNote().getId();
                if (context instanceof NotesController) {
                    ((NotesController)context).displayFocusedNote(id);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdater(List<NoteWithCourse> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public class ViewHolderN extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView noteName;
        View noteCard;

        public ViewHolderN(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            noteName = itemView.findViewById(R.id.noteName);
            noteCard = itemView.findViewById(R.id.noteCard);

        }
    }
}