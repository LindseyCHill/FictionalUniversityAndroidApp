package student.lindseychill.mobileapplicationprojectwgu.Data;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import student.lindseychill.mobileapplicationprojectwgu.Model.Login;

@Dao
public interface LoginDAO {
    String TABLE_NAME = "login_table";
    String LOGIN_USER = "login_user";
    String LOGIN_PASSWORD = "login_password";

    @Transaction
    @Query("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + LOGIN_USER + " = :tUSER AND " + LOGIN_PASSWORD + " = :tPASSWORD")
    LiveData<Integer> checkLogin(String tUSER, String tPASSWORD);

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + LOGIN_USER + " = :tUSER AND " + LOGIN_PASSWORD + " = :tPASSWORD")
    int validate(String tUSER, String tPASSWORD);

    @Insert(onConflict = REPLACE)
    void insertLogin(Login login);
}
