package student.lindseychill.mobileapplicationprojectwgu.Data;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import student.lindseychill.mobileapplicationprojectwgu.Model.Term;

@Dao
public interface TermDAO {
    String TABLE_NAME = "term_table";
    String ITEM_ID = "term_id";
    String ITEM_NAME = "term_name";

    @Insert(onConflict = REPLACE)
    void insertTerm(Term term);

    @Delete
    void deleteTerm(Term... term);

    @Update
    void updateTerm(Term... term);

    //Get all data query
    @Query("SELECT * FROM "+TABLE_NAME)
    List<Term> getAll();

    //Get term by ID
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_ID+" = :tID")
    Term getTermByID(Long tID);

    //Get term by name
    @Query("SELECT * FROM "+TABLE_NAME+" WHERE "+ITEM_NAME+" = :tName")
    Term getTermByName(String tName);
}
