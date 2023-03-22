package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import student.lindseychill.mobileapplicationprojectwgu.Adapters.CourseAdapter;
import student.lindseychill.mobileapplicationprojectwgu.Adapters.DatePicker;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Assessment;
import student.lindseychill.mobileapplicationprojectwgu.Model.Course;
import student.lindseychill.mobileapplicationprojectwgu.Model.CourseWithTermInstructor;
import student.lindseychill.mobileapplicationprojectwgu.Model.Instructor;
import student.lindseychill.mobileapplicationprojectwgu.Model.Term;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class FocusTermController extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{
    UniversityDB db;
    Long termID;
    Term term;
    EditText nameText;
    TextView startText, endText;
    Spinner courseSpinner;
    RecyclerView recyclerView;
    CourseAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<CourseWithTermInstructor> courseList = new ArrayList<>();
    List<Course> allCourseList;
    Course changeCourse;
    int dateSelected = 0;
    private static final String TAG = "POPCORN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_term);

        //sets up action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(AllTermsController.EXTRA_MESSAGE);


        // Capture the layout's views
        termID = Long.parseLong(message);
        term = new Term();
        nameText = findViewById(R.id.name);
        startText = findViewById(R.id.startDate);
        startText.setOnClickListener(v -> {
            DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            dateSelected = 0;
        });
        endText = findViewById(R.id.endDate);
        endText.setOnClickListener(v -> {
            DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            dateSelected = 1;
        });
        recyclerView = findViewById(R.id.attributeRecycler);
        courseSpinner=findViewById(R.id.attributeSpinner);
        db = UniversityDB.getInstance(this);


        //if term is not a new term, get values from term and set values on views
        if(termID!=-1L){
            term = db.TermDAO().getTermByID(termID);
            nameText.setText(term.getName());
            startText.setText(term.getStart());
            endText.setText(term.getEnd());
        }
        //Set up recycler view for displaying courses
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        courseList = db.CourseDAO().getCourseWithTermInstructorForSpecificTerm(termID);
        adapter = new CourseAdapter(FocusTermController.this,courseList);
        recyclerView.setAdapter(adapter);
        //set up course spinner
        allCourseList=db.CourseDAO().getAll();
        ArrayAdapter<Course> cAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, allCourseList);
        cAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(cAdapter);
        courseSpinner.setOnItemSelectedListener(this);

    }
    public void menuSelection(View v){
        View backBTN = findViewById(R.id.back_btn);
        View homeBTN = findViewById(R.id.home_btn);
        Intent intent;
        if(v.getId() == backBTN.getId()){
            //go back a page to the home menu
            intent = new Intent(this, AllTermsController.class);
            startActivity(intent);
        }
        else if(v.getId() == homeBTN.getId()){
            //go back a page to the home menu
            intent = new Intent(this, HomeMenuController.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show();}
    }
    public void saveTerm(View view){
        //set term name
        term.setName(String.valueOf(nameText.getText()));
        //set term start
        term.setStart(String.valueOf(startText.getText()));
        //set term end
        term.setEnd(String.valueOf(endText.getText()));
        int i = 0;
        //if term is new, insert term and get new id
        if(termID == -1L){
            db.TermDAO().insertTerm(term);
            termID = db.TermDAO().getTermByName(term.getName()).getId();
        }
        //if term is not new, update term and remove all courses from term
        else{
            db.TermDAO().updateTerm(term);
            while(i<allCourseList.size()){
                if(Objects.equals(allCourseList.get(i).getTermID(), termID)){
                    Course course = allCourseList.get(i);
                    course.setTermID(null);
                    db.CourseDAO().updateCourse(course);
                }
                i++;
            }
        }
        i = 0;
        //add courses to term
        while(i<courseList.size()){
            Course course = courseList.get(i).getCourse();
            course.setTermID(termID);
            db.CourseDAO().updateCourse(course);
            i++;
        }
        Intent intent = new Intent(this, AllTermsController.class);
        startActivity(intent);
    }
    public void deleteTerm(View view){
        //delete called before term created, term not created
        if(termID == null){
            Intent intent = new Intent(this, AllTermsController.class);
            startActivity(intent);
        }
        else if(courseList.size()>0){
            Toast.makeText(this,"Please remove courses first!", Toast.LENGTH_LONG).show();
        }
        else{
            int i = 0;
            List<CourseWithTermInstructor> removalList = db.CourseDAO().getCourseWithTermInstructorForSpecificTerm(termID);
            while(i<removalList.size()){
                Course course = removalList.get(i).getCourse();
                course.setTermID(null);
                db.CourseDAO().updateCourse(course);
                i++;
            }
            db.TermDAO().deleteTerm(term);
            Intent intent = new Intent(this, AllTermsController.class);
            startActivity(intent);
        }
    }
    public void addCourse(View view){
        int i = 0;
        boolean noConflict = true;
        while(i<courseList.size()){
            if(changeCourse.getId().equals(courseList.get(i).getCourse().getId())){ noConflict = false; }
            i++;
        }
        if(noConflict){
            courseList.add(db.CourseDAO().getCourseWithTermInstructorForSpecificCourse(changeCourse.getId()));
            adapter.updateAdater(courseList);
        }
    }
    public void removeCourse(View view){
        int i = 0;
        while(i<courseList.size()){
            if(courseList.get(i).getCourse().getId().equals(changeCourse.getId()))courseList.remove(i);
            i++;
        }
        adapter.updateAdater(courseList);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        changeCourse = (Course) courseSpinner.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String strDate = format.format(mCalendar.getTime());
        if(dateSelected==0){startText.setText(strDate);}
        else if(dateSelected==1){endText.setText(strDate);}
        else{Toast.makeText(this,"something went wrong",Toast.LENGTH_SHORT).show();}
    }
}