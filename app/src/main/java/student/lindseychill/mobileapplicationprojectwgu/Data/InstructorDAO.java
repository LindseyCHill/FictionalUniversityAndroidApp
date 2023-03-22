package student.lindseychill.mobileapplicationprojectwgu.Data;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Model.Instructor;

@Dao
public interface InstructorDAO {
    String TABLE_NAME = "instructor_table";
    String ITEM_ID = "instructor_id";
    String ITEM_NAME = "instructor_name";

    @Insert(onConflict = REPLACE)
    void insertInstructor(Instructor instructor);

    @Delete
    void deleteInstructor(Instructor instructor);

    @Update
    void updateInstructor(Instructor instructor);

    //Get all data query
    @Query("SELECT * FROM "+TABLE_NAME)
    List<Instructor> getAll();

    //Get term by ID
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_ID+" = :tID")
    Instructor getInstructorByID(Long tID);

}

