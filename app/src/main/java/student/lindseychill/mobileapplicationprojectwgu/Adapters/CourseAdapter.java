package student.lindseychill.mobileapplicationprojectwgu.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Controllers.AllCoursesController;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.CourseWithTermInstructor;
import student.lindseychill.mobileapplicationprojectwgu.Model.Term;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolderC> {
    // Initialize variable
    private List<CourseWithTermInstructor> courseList;
    private Activity context;
    private UniversityDB db;
    private static final String TAG = "POPCORN";

    @SuppressLint("NotifyDataSetChanged")
    public void showFilteredList(List<CourseWithTermInstructor> filteredList){
        this.courseList = filteredList;
        notifyDataSetChanged();
    }

    //create constructor
    @SuppressLint("NotifyDataSetChanged")
    public CourseAdapter(Activity context, List<CourseWithTermInstructor> courseList){
        this.context = context;
        this.courseList = courseList;
        notifyDataSetChanged();
        Log.v(TAG, "adapter constructed");
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolderC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_course,parent,false);
        Log.v(TAG, "adapter created");
        return new CourseAdapter.ViewHolderC(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolderC holder, int position) {
        CourseWithTermInstructor course = courseList.get(position);
        //db = UniversityDB.getInstance(context);
        Log.v(TAG, "holder");
        holder.courseName.setText(course.getCourse().getName());
        Log.v(TAG, "course.getCourse().getName()");
        holder.courseStart.setText(course.getCourse().getStart());
        holder.courseEnd.setText(course.getCourse().getEnd());

        String courseTerm;
        try{courseTerm = course.getTerm().getName();}
        catch (Exception e) {
            courseTerm = "";
        }
        Log.v(TAG, "course.getTerm().getName()");
        holder.courseTermName.setText(courseTerm);

        holder.courseCard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                CourseWithTermInstructor c = courseList.get(holder.getAdapterPosition());
                Long id = c.getCourse().getId();
                if (context instanceof AllCoursesController) {
                    ((AllCoursesController)context).displayFocusedCourse(id);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdater(List<CourseWithTermInstructor> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    public class ViewHolderC extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView courseName, courseStart, courseEnd, courseTermName;
        View courseCard;

        public ViewHolderC(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            courseName = itemView.findViewById(R.id.courseName);
            courseStart = itemView.findViewById(R.id.courseStart);
            courseEnd = itemView.findViewById(R.id.courseEnd);
            courseCard = itemView.findViewById(R.id.courseCard);
            courseTermName = itemView.findViewById(R.id.courseTermName);

        }
    }
}