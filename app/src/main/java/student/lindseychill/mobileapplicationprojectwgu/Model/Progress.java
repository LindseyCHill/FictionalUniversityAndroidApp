package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "progress_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id"))
public class Progress implements Serializable {
    @ColumnInfo(name = "progress_id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "progress_user")
    private String user;

    @ColumnInfo(name = "progress_time")
    private String time;

    @ColumnInfo(name = "progress_status")
    private String status;

    @ColumnInfo(name = "course_id")
    private Long courseID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }
}
