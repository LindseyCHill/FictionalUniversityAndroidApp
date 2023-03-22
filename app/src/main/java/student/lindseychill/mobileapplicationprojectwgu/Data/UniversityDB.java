package student.lindseychill.mobileapplicationprojectwgu.Data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import student.lindseychill.mobileapplicationprojectwgu.Model.Assessment;
import student.lindseychill.mobileapplicationprojectwgu.Model.Course;
import student.lindseychill.mobileapplicationprojectwgu.Model.Instructor;
import student.lindseychill.mobileapplicationprojectwgu.Model.Login;
import student.lindseychill.mobileapplicationprojectwgu.Model.Note;
import student.lindseychill.mobileapplicationprojectwgu.Model.Progress;
import student.lindseychill.mobileapplicationprojectwgu.Model.Term;
import student.lindseychill.mobileapplicationprojectwgu.Utility.CurrentUserInfo;


/**
 * Reference used for understanding:
 * Save data in a local database using room  :   android developers. Android Developers. (n.d.).
 * Retrieved April 8, 2022, from https://developer.android.com/training/data-storage/room
 */
@Database(entities = {Term.class, Course.class, Instructor.class, Note.class, Assessment.class, Progress.class, Login.class}, version = 1, exportSchema = false)
public abstract class UniversityDB extends RoomDatabase {
    private static final String TAG = "POPCORN";
    //create instances
    private static UniversityDB db;
    //Define database name
    private static final String DB_NAME = "FHUniversityDB";

    public synchronized static UniversityDB getInstance(Context c) {
        // check condition
        if (db == null) {
            // when database is null, initialize database
            db = Room.databaseBuilder(c.getApplicationContext(), UniversityDB.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
            Log.v(TAG, String.valueOf(db));
            if (db.InstructorDAO().getAll().size() < 1) setUpTestData();
            Log.v(TAG, "test data set up");
        }
        // return database
        Log.v(TAG, "returning database instance");
        return db;
    }

    private static void setUpTestData() {
        Term term = new Term();
        term.setName("Fall 2022");//Term 1L
        term.setStart("06-01-2022");
        term.setEnd("12-31-2022");
        db.TermDAO().insertTerm(term);

        term.setName("Spring 2023");//Term 2L
        term.setStart("01-01-2023");
        term.setEnd("6-30-2023");
        db.TermDAO().insertTerm(term);

        Instructor instructor = new Instructor();
        instructor.setName("Albert Einstein");//Instructor 1L
        instructor.setPhone("314-1592");
        instructor.setEmail("einstein@fhu.edu");
        db.InstructorDAO().insertInstructor(instructor);

        instructor.setName("Pablo Neruda");//Instructor 2L
        instructor.setPhone("923-1973");
        instructor.setEmail("neruda@fhu.edu");
        db.InstructorDAO().insertInstructor(instructor);

        instructor.setName("Ada Lovelace");//Instructor 3L
        instructor.setPhone("011-1852");
        instructor.setEmail("lovelace@fhu.edu");
        db.InstructorDAO().insertInstructor(instructor);

        Course course = new Course();
        course.setName("World History 101");//Course 1L
        course.setStart("06-01-2022");
        course.setEnd("07-01-2022");
        course.setStatus("COMPLETED");
        course.setTermID(1L); //Fall 2022
        course.setInstructorID(2L); //Pablo Neruda
        db.CourseDAO().insertCourse(course);

        Progress progress = new Progress();
        progress.setUser("setup");
        progress.setTime(CurrentUserInfo.getCurrentDateTime());
        progress.setCourseID(1L);
        progress.setStatus("COMPLETED");
        db.ProgressDAO().insertProgress(progress);

        course.setName("World History 102");//Course 2L
        course.setStart("07-01-2022");
        course.setEnd("08-01-2022");
        course.setStatus("COMPLETED");
        course.setTermID(1L); //Fall 2022
        course.setInstructorID(2L); //Pablo Neruda
        db.CourseDAO().insertCourse(course);

        progress.setCourseID(2L);
        db.ProgressDAO().insertProgress(progress);

        course.setName("Algebra");//Course 3L
        course.setStart("08-01-2022");
        course.setEnd("09-01-2022");
        course.setStatus("COMPLETED");
        course.setTermID(1L); //Fall 2022
        course.setInstructorID(1L); //Albert Einstein
        db.CourseDAO().insertCourse(course);

        progress.setCourseID(3L);
        db.ProgressDAO().insertProgress(progress);

        course.setName("Pre-Calculus");//Course 4L
        course.setStart("09-01-2022");
        course.setEnd("10-01-2022");
        course.setStatus("COMPLETED");
        course.setTermID(1L); //Fall 2022
        course.setInstructorID(1L); //Albert Einstein
        db.CourseDAO().insertCourse(course);

        progress.setCourseID(4L);
        db.ProgressDAO().insertProgress(progress);

        course.setName("Calculus");//Course 5L
        course.setStart("10-01-2022");
        course.setEnd("12-31-2022");
        course.setStatus("IN PROGRESS");
        course.setTermID(1L); //Fall 2022
        course.setInstructorID(1L); //Albert Einstein
        db.CourseDAO().insertCourse(course);

        progress.setCourseID(5L);
        progress.setStatus("IN PROGRESS");
        db.ProgressDAO().insertProgress(progress);

        course.setName("Programming 101");//Course 6L
        course.setStart("01-01-2023");
        course.setEnd("02-01-2023");
        course.setStatus("PLAN TO TAKE");
        course.setTermID(1L); //Spring 2023
        course.setInstructorID(3L); //Ada Lovelace
        db.CourseDAO().insertCourse(course);

        progress.setCourseID(6L);
        progress.setStatus("PLAN TO TAKE");
        db.ProgressDAO().insertProgress(progress);

        course.setName("Programming 102");//Course 7L
        course.setStart("02-01-2023");
        course.setEnd("03-01-2023");
        course.setStatus("PLAN TO TAKE");
        course.setInstructorID(3L); //Ada Lovelace
        db.CourseDAO().insertCourse(course);

        progress.setCourseID(7L);
        db.ProgressDAO().insertProgress(progress);

        Assessment assessment = new Assessment();
        assessment.setName("World History 101 Test");//Assessment 1L
        assessment.setType("Objective Assessment");
        assessment.setStart("06-08-2022");
        assessment.setEnd("06-08-2022");
        assessment.setCourseID(1L); //World History 101
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("World History 101 Project");//Assessment 2L
        assessment.setType("Performance Assessment");
        assessment.setStart("06-01-2022");
        assessment.setEnd("07-01-2022");
        assessment.setCourseID(1L); //World History 101
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("World History 102 Project");//Assessment 3L
        assessment.setType("Performance Assessment");
        assessment.setStart("07-01-2022");
        assessment.setEnd("08-01-2022");
        assessment.setCourseID(2L); //World History 101
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("Algebra Quiz");//Assessment 4L
        assessment.setType("Objective Assessment");
        assessment.setStart("08-05-2022");
        assessment.setEnd("08-05-2022");
        assessment.setCourseID(3L);//Algebra
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("Algebra Test");//Assessment 5L
        assessment.setType("Objective Assessment");
        assessment.setStart("09-01-2022");
        assessment.setEnd("09-01-2022");
        assessment.setCourseID(3L);//Algebra
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("Pre-Calc Test");//Assessment 6L
        assessment.setType("Objective Assessment");
        assessment.setStart("10-01-2022");
        assessment.setEnd("10-01-2022");
        assessment.setCourseID(4L);//Pre-Calculus
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("Calc Test 1");//Assessment 7L
        assessment.setType("Objective Assessment");
        assessment.setStart("11-01-2022");
        assessment.setEnd("11-01-2022");
        assessment.setCourseID(5L);//Calculus
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("Calc Test 2");//Assessment 8L
        assessment.setType("Objective Assessment");
        assessment.setStart("12-30-2022");
        assessment.setEnd("12-30-2022");
        assessment.setCourseID(5L);//Calculus
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("Hello World Project");//Assessment 9L
        assessment.setType("Performance Assessment");
        assessment.setStart("01-01-2023");
        assessment.setEnd("02-01-2023");
        assessment.setCourseID(6L);// Programming 101
        db.AssessmentDAO().insertAssessment(assessment);

        assessment.setName("Application Project");//Assessment 9L
        assessment.setType("Performance Assessment");
        assessment.setStart("02-01-2023");
        assessment.setEnd("03-01-2023");
        assessment.setCourseID(7L);// Programming 102
        db.AssessmentDAO().insertAssessment(assessment);

        Note note = new Note();
        note.setName("7 Wonders of Ancient World");
        note.setBody("The Great Pyramid, the Hanging Gardens, the Mausoleum of Maussollos," +
                " the Statue of Zeus, the Lighthouse of Alexandria, the Temple of Artemis," +
                " and the Colossus of Rhodes.");
        note.setCourseID(2L);
        db.NoteDAO().insertNote(note);

        Login login = new Login();
        login.setUser("test");
        login.setPassword("test");
        db.LoginDAO().insertLogin(login);
    }

    // Create DAO
    public abstract TermDAO TermDAO();

    public abstract CourseDAO CourseDAO();

    public abstract AssessmentDAO AssessmentDAO();

    public abstract InstructorDAO InstructorDAO();

    public abstract NoteDAO NoteDAO();

    public abstract ProgressDAO ProgressDAO();

    public abstract LoginDAO LoginDAO();
}
