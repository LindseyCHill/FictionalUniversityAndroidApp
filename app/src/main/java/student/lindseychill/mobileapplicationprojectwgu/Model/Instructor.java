package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructor_table")
public class Instructor {
    @ColumnInfo(name = "instructor_id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "instructor_name")
    private String name;

    @ColumnInfo(name = "instructor_phone")
    private String phone;

    @ColumnInfo(name = "instructor_email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
