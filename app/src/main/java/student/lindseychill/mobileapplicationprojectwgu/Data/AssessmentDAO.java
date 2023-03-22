package student.lindseychill.mobileapplicationprojectwgu.Data;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Model.Assessment;
import student.lindseychill.mobileapplicationprojectwgu.Model.AssessmentWithCourse;

@Dao
public interface AssessmentDAO {
    String TABLE_NAME = "assessment_table";
    String ITEM_ID = "assessment_id";
    String COURSE_ID = "course_id";

    @Transaction
    @Query("SELECT * FROM "+TABLE_NAME)
    public List<AssessmentWithCourse> getAssessmentsWithCourses();

    @Insert(onConflict = REPLACE)
    void insertAssessment (Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);

    //Get all data query
    @Query("SELECT * FROM "+TABLE_NAME)
    List<Assessment> getAll();

    //Get assessment by ID
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_ID+" = :tID")
    Assessment getAssessmentByID(Long tID);

    //Get assessment by Course ID
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+COURSE_ID+" = :tID")
    List<Assessment> getAssessmentsForSpecificCourse(Long tID);
}
