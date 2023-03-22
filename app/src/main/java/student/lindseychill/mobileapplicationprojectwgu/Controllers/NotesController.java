package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import student.lindseychill.mobileapplicationprojectwgu.Adapters.NoteAdapter;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Note;
import student.lindseychill.mobileapplicationprojectwgu.Model.NoteWithCourse;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class NotesController extends AppCompatActivity {
    UniversityDB db;
    Long courseID;
    List<NoteWithCourse> noteList;
    Note currentNote;
    EditText noteName, noteBody;
    TextView courseLabel;
    RecyclerView recyclerView;
    NoteAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    public static final String EXTRA_MESSAGE = "student.lindseychill.mobileapplicationprojectwgu.COURSE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        //sets up action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        //get course information and notes for course
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        courseID = Long.parseLong(message);
        currentNote = new Note();
        currentNote.setCourseID(courseID);
        db = UniversityDB.getInstance(this);
        noteList = db.NoteDAO().getNotesWithCourses(courseID);
        //set up views
        courseLabel = findViewById(R.id.courseLabel);
        courseLabel.setText(db.CourseDAO().getCourseByID(courseID).getName());
        noteName = findViewById(R.id.noteName);
        noteBody = findViewById(R.id.noteBody);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NoteAdapter(NotesController.this,noteList);
        recyclerView.setAdapter(adapter);
    }
    public void menuSelection(View v){
        View backBTN = findViewById(R.id.back_btn);
        View homeBTN = findViewById(R.id.home_btn);
        Intent intent;
        if(v.getId() == backBTN.getId()){
            //go back a page to the home menu
            intent = new Intent(this, FocusCourseController.class);
            String message = String.valueOf(courseID);
            intent.putExtra(EXTRA_MESSAGE, message);
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
    public void displayFocusedNote(Long id) {
        currentNote=db.NoteDAO().getNoteByID(id);
        noteName.setText(currentNote.getName());
        noteBody.setText(currentNote.getBody());
    }
    public void sendEmail(View view){
        String subject = String.valueOf(noteName.getText());
        String body = String.valueOf(noteBody.getText());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,body);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Select email client:"));
    }
    public void sendSMS(View view){
        String body = noteName.getText()+"\n"+noteBody.getText();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms","",null));
        intent.putExtra("sms_body",body);
        startActivity(intent);
    }
    public void newNote(View view){
        currentNote = new Note();
        noteName.setText("");
        noteBody.setText("");
        currentNote.setCourseID(courseID);
    }
    public void deleteNote(View view){
        if(currentNote.getId() != null) db.NoteDAO().deleteNote(currentNote);
        currentNote = new Note();
        noteName.setText("");
        noteBody.setText("");
        currentNote.setCourseID(courseID);
        noteList=db.NoteDAO().getNotesWithCourses(courseID);
        adapter.updateAdater(noteList);

    }
    public void saveNote(View view){
        currentNote.setName(String.valueOf(noteName.getText()));
        currentNote.setBody(String.valueOf(noteBody.getText()));
        if(currentNote.getId() == null) db.NoteDAO().insertNote(currentNote);
        else{db.NoteDAO().updateNote(currentNote);}
        currentNote = new Note();
        noteName.setText("");
        noteBody.setText("");
        currentNote.setCourseID(courseID);
        noteList=db.NoteDAO().getNotesWithCourses(courseID);
        adapter.updateAdater(noteList);
    }
}