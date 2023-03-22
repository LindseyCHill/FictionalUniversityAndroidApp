package student.lindseychill.mobileapplicationprojectwgu.Data;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Model.Course;
import student.lindseychill.mobileapplicationprojectwgu.Model.CourseWithTermInstructor;

@Dao
public interface CourseDAO {
    String TABLE_NAME = "course_table";
    String ITEM_ID = "course_id";
    String ITEM_NAME = "course_name";
    String TERM_ID = "term_id";
    String INSTRUCTOR_ID = "instructor_id";
    @Transaction
    @Query("SELECT * FROM "+TABLE_NAME)
    List<CourseWithTermInstructor> getCourseWithTermInstructor();

    @Transaction
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+TERM_ID+" = :tID")
    List<CourseWithTermInstructor> getCourseWithTermInstructorForSpecificTerm(Long tID);

    @Transaction
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_ID+" = :tID")
    CourseWithTermInstructor getCourseWithTermInstructorForSpecificCourse(Long tID);

    @Insert(onConflict = REPLACE)
    void insertCourse (Course course);

    @Delete
    void deleteCourse(Course course);

    @Update
    void updateCourse(Course course);

    //Get all data query
    @Query("SELECT * FROM "+TABLE_NAME)
    List<Course> getAll();

    //Get course by ID
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_ID+" = :tID")
    Course getCourseByID(Long tID);

    //Get course by name
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_NAME+" = :tName")
    Course getCourseByName(String tName);

    //Get courses by instructor name
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+INSTRUCTOR_ID+" = :tInstructorID")
    List<Course> getCoursesWithInstructor(Long tInstructorID);

}
