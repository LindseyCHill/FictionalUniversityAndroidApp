package student.lindseychill.mobileapplicationprojectwgu.Data;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Model.Note;
import student.lindseychill.mobileapplicationprojectwgu.Model.NoteWithCourse;

@Dao
public interface NoteDAO {
    String TABLE_NAME = "note_table";
    String ITEM_ID = "note_id";
    String COURSE_ID = "course_id";

    @Transaction
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+COURSE_ID+" = :tID")
    public List<NoteWithCourse> getNotesWithCourses(Long tID);

    @Insert(onConflict = REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note... note);

    @Update
    void updateNote(Note... note);

    //Get note by ID
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_ID+" = :tID")
    Note getNoteByID(Long tID);

}
