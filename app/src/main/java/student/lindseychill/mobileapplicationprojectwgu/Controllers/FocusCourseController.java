package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import student.lindseychill.mobileapplicationprojectwgu.Adapters.AssessmentAdapter;
import student.lindseychill.mobileapplicationprojectwgu.Adapters.DatePicker;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Assessment;
import student.lindseychill.mobileapplicationprojectwgu.Model.Course;
import student.lindseychill.mobileapplicationprojectwgu.Model.Instructor;
import student.lindseychill.mobileapplicationprojectwgu.Model.Term;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class FocusCourseController extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    UniversityDB db;
    Long courseID;
    Course course;
    Instructor instructor;
    EditText nameText, instructorNameLabel, instructorPhoneLabel, instructorEmailLabel;
    TextView startText, endText;
    Spinner termSpinner, instructorSpinner, statusSpinner, assessmentSpinner;
    RecyclerView recyclerView;
    AssessmentAdapter adapter;
    Assessment changeAssessment;
    LinearLayoutManager linearLayoutManager;
    List<Assessment> assessmentList, allAssessmentList;
    List<Term> termList;
    List<Instructor> instructorList;
    List<String> statusList;
    Dialog dialog;
    int dateSelected = 0;
    public static final String EXTRA_MESSAGE = "student.lindseychill.mobileapplicationprojectwgu.COURSE_ID";
    private static final String TAG = "POPCORN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_course);
        //sets up action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        //Display information for this course
        courseID = Long.parseLong(message);
        course = new Course();
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
        termSpinner = findViewById(R.id.termSpinner);
        instructorSpinner = findViewById(R.id.instructorSpinner);
        statusSpinner = findViewById(R.id.statusSpinner);
        assessmentSpinner = findViewById(R.id.attributeSpinner);

        //database
        db = UniversityDB.getInstance(this);

        //set term spinner with list and adapter
        termList = new ArrayList<>();
        Term term = new Term();
        term.setId(-1L);
        term.setName("NO TERM");
        termList.add(term);
        termList.addAll(db.TermDAO().getAll());

        ArrayAdapter<Term> tAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, termList);
        tAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(tAdapter);
        termSpinner.setOnItemSelectedListener(this);

        //set instructor spinner with list and adapter
        instructorList = db.InstructorDAO().getAll();
        ArrayAdapter<Instructor> iAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, instructorList);
        iAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        instructorSpinner.setAdapter(iAdapter);
        instructorSpinner.setOnItemSelectedListener(this);

        //set status spinner with list and adapter
        Resources r = getResources();
        statusList = Arrays.asList(r.getStringArray(R.array.status_array));
        ArrayAdapter<String> sAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, statusList);
        sAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(sAdapter);
        statusSpinner.setOnItemSelectedListener(this);
        //set assessment spinner with list and adapter
        allAssessmentList = db.AssessmentDAO().getAll();
        ArrayAdapter<Assessment> aAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, allAssessmentList);
        sAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        assessmentSpinner.setAdapter(aAdapter);
        assessmentSpinner.setOnItemSelectedListener(this);

        if(courseID!=-1L){
            course = db.CourseDAO().getCourseByID(courseID);
            nameText.setText(course.getName());
            startText.setText(course.getStart());
            endText.setText(course.getEnd());
            int pos = 0;
            if(course.getTermID() != null) {
                int i = 0;
                while (i < termList.size()) {
                    if (termList.get(i).getId().equals(course.getTermID())) {
                        pos = i;
                    }
                    i++;
                }
            }
            termSpinner.setSelection(pos,true);
            if(course.getInstructorID() != null) {
                pos = 0;
                for (int i = 0; i < instructorList.size(); i++) {
                    if(Objects.equals(instructorList.get(i).getId(), course.getInstructorID()))pos = i;
                }
                instructorSpinner.setSelection(pos,true);
            }
            pos = 0;
            for (int i = 0; i < statusList.size(); i++) {
                if(statusList.get(i).equals(course.getStatus()))pos = i;
            }
            statusSpinner.setSelection(pos,true);
        }
        //Set up recycler view for displaying assessments
        recyclerView = findViewById(R.id.attributeRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        assessmentList = db.AssessmentDAO().getAssessmentsForSpecificCourse(courseID);
        adapter = new AssessmentAdapter(FocusCourseController.this,assessmentList);
        recyclerView.setAdapter(adapter);
    }
    public void menuSelection(View v){
        View backBTN = findViewById(R.id.back_btn);
        View homeBTN = findViewById(R.id.home_btn);
        Intent intent;
        if(v.getId() == backBTN.getId()){
            //go back a page to the home menu
            intent = new Intent(this, AllCoursesController.class);
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
    public void saveCourse(View view){
        course.setName(String.valueOf(nameText.getText()));
        course.setStart(String.valueOf(startText.getText()));
        course.setEnd(String.valueOf(endText.getText()));
        if(courseID == -1L)course.setTermID(null);
        int i = 0;
        if(courseID == -1L){
            db.CourseDAO().insertCourse(course);
            courseID = db.CourseDAO().getCourseByName(course.getName()).getId();
        }
        else{
            //course.setId(courseID);
            db.CourseDAO().updateCourse(course);
            while(i<allAssessmentList.size()){
                if(Objects.equals(allAssessmentList.get(i).getCourseID(), course.getId())){
                    Assessment assessment = allAssessmentList.get(i);
                    assessment.setCourseID(null);
                    db.AssessmentDAO().updateAssessment(assessment);
                }
                i++;
            }
        }
        i = 0;
        while(i<assessmentList.size()){
            Assessment assessment = assessmentList.get(i);
            assessment.setCourseID(courseID);
            db.AssessmentDAO().updateAssessment(assessment);
            i++;
        }
        Intent intent = new Intent(this, AllCoursesController.class);
        startActivity(intent);
    }
    public void deleteCourse(View view){
        if(courseID!=-1L){
            db.CourseDAO().deleteCourse(course);
        }
        Intent intent = new Intent(this, AllCoursesController.class);
        startActivity(intent);
    }
    public void goToNotes(View view) {
        if (courseID != -1L) {
        Intent intent = new Intent(this, NotesController.class);
        String message = String.valueOf(courseID);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        }
        else{Toast.makeText(this, "Please save first!", Toast.LENGTH_SHORT).show();
        }
    }
    public void addAssessment(View view){
        int i = 0;
        boolean noConflict = true;
        while(i<assessmentList.size()){
            if(changeAssessment.getId().equals(assessmentList.get(i).getId())){ noConflict = false; }
            i++;
        }
        if(noConflict){
            assessmentList.add(changeAssessment);
            adapter.updateAdater(assessmentList);
        }
    }
    public void removeAssessment(View view){
        int i = 0;
        while(i<assessmentList.size()){
            if(assessmentList.get(i).getId().equals(changeAssessment.getId()))assessmentList.remove(i);
            i++;
        }
        adapter.updateAdater(assessmentList);
    }
    public void viewInstructor(View view){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_instructor);
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        // Initialize and assign variable
        instructorNameLabel = dialog.findViewById(R.id.instructorNameLabel);
        instructorPhoneLabel = dialog.findViewById(R.id.instructorPhoneLabel);
        instructorEmailLabel = dialog.findViewById(R.id.instructorEmailLabel);

        Long instructorID = course.getInstructorID();
        instructor = db.InstructorDAO().getInstructorByID(instructorID);
        instructorNameLabel.setText(instructor.getName());
        instructorPhoneLabel.setText(instructor.getPhone());
        instructorEmailLabel.setText(instructor.getEmail());
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Term associatedTerm = (Term) termSpinner.getSelectedItem();
        if(associatedTerm.getId()>0) course.setTermID(associatedTerm.getId());
        Instructor associatedInstructor = (Instructor) instructorSpinner.getSelectedItem();
        course.setInstructorID(associatedInstructor.getId());
        String associatedStatus = (String) statusSpinner.getSelectedItem();
        course.setStatus(associatedStatus);
        changeAssessment = (Assessment) assessmentSpinner.getSelectedItem();
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

    public void addInstructor(View view){
        instructor = new Instructor();
        instructor.setId(-1L);
        instructorNameLabel.setText("");
        instructorPhoneLabel.setText("");
        instructorEmailLabel.setText("");
    }
    public void deleteInstructor(View view){
        if(!instructor.getId().equals(-1L)){
            if(db.CourseDAO().getCoursesWithInstructor(instructor.getId()).size()>0){
                Toast.makeText(this,"Instructor must be removed from all courses first", Toast.LENGTH_SHORT).show();
            }
            else{db.InstructorDAO().deleteInstructor(instructor);
                instructorList = db.InstructorDAO().getAll();
                ArrayAdapter<Instructor> iAdapter = new ArrayAdapter(
                        this,android.R.layout.simple_spinner_item, instructorList);
                iAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                instructorSpinner.setAdapter(iAdapter);}
        }
        dialog.dismiss();
    }
    public void saveInstructor(View view){
        instructor.setName(String.valueOf(instructorNameLabel.getText()));
        instructor.setPhone(String.valueOf(instructorPhoneLabel.getText()));
        instructor.setEmail(String.valueOf(instructorEmailLabel.getText()));
        if(instructor.getId()==-1L){
            instructor.setId(null);
            db.InstructorDAO().insertInstructor(instructor);
        }
        else{
            db.InstructorDAO().updateInstructor(instructor);
        }
        instructorList = db.InstructorDAO().getAll();
        ArrayAdapter<Instructor> iAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, instructorList);
        iAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        instructorSpinner.setAdapter(iAdapter);
        dialog.dismiss();
    }
}