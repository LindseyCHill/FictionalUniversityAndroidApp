package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import student.lindseychill.mobileapplicationprojectwgu.Adapters.DatePicker;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Assessment;
import student.lindseychill.mobileapplicationprojectwgu.Model.Course;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class FocusAssessmentController extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{
    UniversityDB db;
    Long assessmentID;
    Assessment assessment;
    Course course;
    EditText nameText;
    TextView startText, endText;
    Spinner courseSpinner, typeSpinner;
    List<Course> courseList;
    List<String> typeList;
    int dateSelected = 0;
    private static final String TAG = "POPCORN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_assessment);
        //sets up action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        //Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(AllAssessmentsController.EXTRA_MESSAGE);

        //Assign layout views to IDs and get database instance
        assessmentID = Long.parseLong(message);
        assessment = new Assessment();
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
        db = UniversityDB.getInstance(this);
        courseSpinner = findViewById(R.id.courseSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);

        //set course spinner with courseList and adapter
        courseList = db.CourseDAO().getAll();
        ArrayAdapter<Course> cAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, courseList);
        cAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(cAdapter);
        courseSpinner.setOnItemSelectedListener(this);

        //set type spinner with list and adapter
        Resources r = getResources();
        typeList = Arrays.asList(r.getStringArray(R.array.test_type_array));
        ArrayAdapter<String> sAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, typeList);
        sAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(sAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        //check if updating assessment based on intent message and assigning variables according to current record
        if(assessmentID!=-1L){
            assessment = db.AssessmentDAO().getAssessmentByID(assessmentID);
            nameText.setText(assessment.getName());
            startText.setText(assessment.getStart());
            endText.setText(assessment.getEnd());
            if(assessment.getCourseID()!= null){
                course = db.CourseDAO().getCourseByID(assessment.getCourseID());
                int pos = 0;
                for (int i = 0; i < courseList.size(); i++) {
                    if(courseList.get(i).getId().equals(course.getId()))pos = i;
                }
                courseSpinner.setSelection(pos,true);
            }
            if(assessment.getType().equals("Performance Assessment")){
                Log.v("TESTING","Performance");
                typeSpinner.setSelection(1,true);
                Log.v("TESTING","Animation");
            }
        }
    }
    public void menuSelection(View v){
        View backBTN = findViewById(R.id.back_btn);
        View homeBTN = findViewById(R.id.home_btn);
        Intent intent;
        if(v.getId() == backBTN.getId()){
            //go back a page to the home menu
            intent = new Intent(this, AllAssessmentsController.class);
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
    public void saveAssessment(View view){
        assessment.setName(String.valueOf(nameText.getText()));
        assessment.setStart(String.valueOf(startText.getText()));
        assessment.setEnd(String.valueOf(endText.getText()));
        if(assessmentID == -1)db.AssessmentDAO().insertAssessment(assessment);
        else{db.AssessmentDAO().updateAssessment(assessment);}
        Intent intent = new Intent(this, AllAssessmentsController.class);
        startActivity(intent);
    }

    public void deleteAssessment(View view){
        if(assessmentID!=-1L) db.AssessmentDAO().deleteAssessment(assessment);
        Intent intent = new Intent(this, AllAssessmentsController.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Course associatedCourse = (Course) courseSpinner.getSelectedItem();
        assessment.setCourseID(associatedCourse.getId());
        String associatedType = (String) typeSpinner.getSelectedItem();
        assessment.setType(associatedType);
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