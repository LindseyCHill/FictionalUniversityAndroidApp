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

import student.lindseychill.mobileapplicationprojectwgu.Controllers.AllAssessmentsController;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Assessment;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolderA> {
    // Initialize variable
    private List<Assessment> assessmentList;
    private Activity context;
    private UniversityDB db;

    @SuppressLint("NotifyDataSetChanged")
    public void showFilteredList(List<Assessment> filteredList){
        this.assessmentList = filteredList;
        notifyDataSetChanged();
    }

    //create constructor
    @SuppressLint("NotifyDataSetChanged")
    public AssessmentAdapter(Activity context, List<Assessment> assessmentList){
        this.context = context;
        this.assessmentList = assessmentList;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateAdater(List<Assessment> assessmentList){
        this.assessmentList = assessmentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssessmentAdapter.ViewHolderA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_assessment,parent,false);
        return new AssessmentAdapter.ViewHolderA(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolderA holder, int position) {
        Assessment assessment = assessmentList.get(position);
        //db = UniversityDB.getInstance(context);
        holder.assessmentName.setText(assessment.getName());
        holder.assessmentStart.setText(assessment.getStart());
        holder.assessmentEnd.setText(assessment.getEnd());

        holder.assessmentCard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Assessment a = assessmentList.get(holder.getAdapterPosition());
                Long id = a.getId();
                if (context instanceof AllAssessmentsController) {
                    ((AllAssessmentsController)context).displayFocusedAssessment(id);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public class ViewHolderA extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView assessmentName, assessmentStart, assessmentEnd;
        View assessmentCard;

        public ViewHolderA(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            assessmentName = itemView.findViewById(R.id.assessmentName);
            assessmentStart = itemView.findViewById(R.id.assessmentStart);
            assessmentEnd = itemView.findViewById(R.id.assessmentEnd);
            assessmentCard = itemView.findViewById(R.id.assessmentCard);
        }
    }
}