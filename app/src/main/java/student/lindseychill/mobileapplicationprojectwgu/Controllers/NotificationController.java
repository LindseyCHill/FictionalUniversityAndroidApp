package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Assessment;
import student.lindseychill.mobileapplicationprojectwgu.Model.Course;
import student.lindseychill.mobileapplicationprojectwgu.Model.Term;
import student.lindseychill.mobileapplicationprojectwgu.R;
import student.lindseychill.mobileapplicationprojectwgu.Utility.AlertReceiver;


/**
 * Reference used for understanding of alarms and converting date to milliseconds:
 * Sher-DeCusatis, C. (n.d.). My Bicycle Shop for Android Arctic Fox.
 */

public class NotificationController extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner termSpinnerAlert, courseSpinnerAlert, assessmentSpinnerAlert;
    Button termStartBTN, termEndBTN, courseStartBTN, courseEndBTN, assessmentStartBTN, assessmentEndBTN;
    Term term;
    Course course;
    Assessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        termSpinnerAlert = findViewById(R.id.termSpinnerAlert);
        courseSpinnerAlert = findViewById(R.id.courseSpinnerAlert);
        assessmentSpinnerAlert = findViewById(R.id.assessmentSpinnerAlert);
        termStartBTN = findViewById(R.id.termStartBTN);
        termEndBTN = findViewById(R.id.termEndBTN);
        courseStartBTN = findViewById(R.id.courseStartBTN);
        courseEndBTN = findViewById(R.id.courseEndBTN);
        assessmentStartBTN = findViewById(R.id.assessmentStartBTN);
        assessmentEndBTN = findViewById(R.id.assessmentEndBTN);

        UniversityDB db = UniversityDB.getInstance(this);

        List<Term> termList = new ArrayList<>();
        term = new Term();
        term.setName("SELECT TERM");
        term.setStart("Start Date");
        term.setEnd("End Date");
        termList.add(term);
        termList.addAll(db.TermDAO().getAll());
        ArrayAdapter<Term> tAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, termList);
        tAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        termSpinnerAlert.setAdapter(tAdapter);
        termSpinnerAlert.setOnItemSelectedListener(this);

        List<Course> courseList = new ArrayList<>();
        course = new Course();
        course.setName("SELECT COURSE");
        course.setStart("Start Date");
        course.setEnd("End Date");
        courseList.add(course);
        courseList.addAll(db.CourseDAO().getAll());
        ArrayAdapter<Course> cAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, courseList);
        cAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        courseSpinnerAlert.setAdapter(cAdapter);
        courseSpinnerAlert.setOnItemSelectedListener(this);

        List<Assessment> assessmentList = new ArrayList<>();
        assessment = new Assessment();
        assessment.setName("SELECT ASSESSMENT");
        assessment.setStart("Start Date");
        assessment.setEnd("End Date");
        assessmentList.add(assessment);
        assessmentList.addAll(db.AssessmentDAO().getAll());
        ArrayAdapter<Assessment> aAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, assessmentList);
        aAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        assessmentSpinnerAlert.setAdapter(aAdapter);
        assessmentSpinnerAlert.setOnItemSelectedListener(this);

    }
    public void menuSelection(View v){
        View backBTN = findViewById(R.id.back_btn);
        View homeBTN = findViewById(R.id.home_btn);
        Intent intent;
        if(v.getId() == backBTN.getId() || v.getId() == homeBTN.getId()){
            //go back a page to the home menu
            intent = new Intent(this, HomeMenuController.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show();}
    }
    @SuppressLint("NonConstantResourceId")
    public void setAlarm(View view){
        Button button = (Button) view;
        switch (button.getId()){
            case R.id.termStartBTN:
                if (!button.getText().equals("Start Date")) {
                    int uniqueID = Integer.parseInt(String.valueOf("31"+term.getId()));
                    createAlert(term.getName(),"Term starts "+term.getStart(),term.getStart(),uniqueID);
                }
                break;
            case R.id.termEndBTN:
                if (!button.getText().equals("End Date")) {
                    int uniqueID = Integer.parseInt(String.valueOf("32"+term.getId()));
                    createAlert(term.getName(),"Term ends "+term.getEnd(),term.getEnd(),uniqueID);
                }
                break;
            case R.id.courseStartBTN:
                if (!button.getText().equals("Start Date")) {
                    int uniqueID = Integer.parseInt(String.valueOf("21"+course.getId()));
                    createAlert(course.getName(),"Course starts "+course.getStart(),course.getStart(),uniqueID);
                }
                break;
            case R.id.courseEndBTN:
                if (!button.getText().equals("End Date")) {
                    int uniqueID = Integer.parseInt(String.valueOf("22"+course.getId()));
                    createAlert(course.getName(),"Course ends "+course.getEnd(),course.getEnd(),uniqueID);
                }
                break;
            case R.id.assessmentStartBTN:
                if (!button.getText().equals("Start Date")) {
                    int uniqueID = Integer.parseInt(String.valueOf("11"+assessment.getId()));
                    createAlert(assessment.getName(),"Assessment starts "+assessment.getStart(),assessment.getStart(),uniqueID);
                }
                break;
            case R.id.assessmentEndBTN:
                if (!button.getText().equals("End Date")) {
                    int uniqueID = Integer.parseInt(String.valueOf("12"+assessment.getId()));
                    createAlert(assessment.getName(),"Assessment ends "+assessment.getEnd(),assessment.getEnd(),uniqueID);
                }
                break;
            default:
                Toast.makeText(this,"Try again!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        term = (Term) termSpinnerAlert.getSelectedItem();
        termStartBTN.setText(term.getStart());
        termEndBTN.setText(term.getEnd());
        course = (Course) courseSpinnerAlert.getSelectedItem();
        courseStartBTN.setText(course.getStart());
        courseEndBTN.setText(course.getEnd());
        assessment = (Assessment) assessmentSpinnerAlert.getSelectedItem();
        assessmentStartBTN.setText(assessment.getStart());
        assessmentEndBTN.setText(assessment.getEnd());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void createAlert(String title, String contentText, String date, int uniqueID){
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("text", contentText);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueID, intent,0);
        Log.v("POPCORN", String.valueOf(uniqueID));

        String myFormat = "MM-dd-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate=null;
        try{
            myDate=simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert myDate != null;
        Long trigger=myDate.getTime();

        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);
        Toast.makeText(this,"Alert set", Toast.LENGTH_SHORT).show();
    }
}