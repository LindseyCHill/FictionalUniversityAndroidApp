package student.lindseychill.mobileapplicationprojectwgu.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login_table")
public class Login {
    @ColumnInfo(name = "instructor_id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "login_user")
    private String user;

    @ColumnInfo(name = "login_password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
