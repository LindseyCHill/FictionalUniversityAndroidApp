package student.lindseychill.mobileapplicationprojectwgu.Data;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Model.Progress;
import student.lindseychill.mobileapplicationprojectwgu.Model.ProgressWithCourse;


@Dao
public interface ProgressDAO {
    String TABLE_NAME = "progress_table";
    String COURSE_ID = "course_id";

    @Transaction
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+COURSE_ID+" = :tID")
    public List<ProgressWithCourse> getProgressWithCourses(Long tID);

    @Insert(onConflict = REPLACE)
    void insertProgress(Progress progress);
}
