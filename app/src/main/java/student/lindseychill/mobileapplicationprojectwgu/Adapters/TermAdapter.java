package student.lindseychill.mobileapplicationprojectwgu.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Controllers.AllTermsController;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Term;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolderT> {
    // Initialize variable
    private static List<Term> termList;
    private Activity context;
    private UniversityDB db;

    @SuppressLint("NotifyDataSetChanged")
    public void showFilteredList(List<Term> filteredList){
        this.termList = filteredList;
        notifyDataSetChanged();
    }

    //create constructor
    @SuppressLint("NotifyDataSetChanged")
    public TermAdapter(Activity context, List<Term> termList){
        this.context = context;
        this.termList = termList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderT onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_term,parent,false);
        return new ViewHolderT(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderT holder, int position) {
        Term term = termList.get(position);
        holder.termName.setText(term.getName());
        holder.termStart.setText(term.getStart());
        holder.termEnd.setText(term.getEnd());

        holder.termCard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Term t = termList.get(holder.getAdapterPosition());
                Long id = t.getId();
                if (context instanceof AllTermsController) {
                    ((AllTermsController)context).displayFocusedTerm(id);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return termList.size();
    }

    public class ViewHolderT extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView termName, termStart, termEnd;
        View termCard;

        public ViewHolderT(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            termName = itemView.findViewById(R.id.termName);
            termStart = itemView.findViewById(R.id.termStart);
            termEnd = itemView.findViewById(R.id.termEnd);
            termCard = itemView.findViewById(R.id.termCard);
        }
    }
}