package student.lindseychill.mobileapplicationprojectwgu.Model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "note_table",
        foreignKeys = @ForeignKey(
                onDelete = CASCADE,
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id"))
public class Note implements Serializable {
    @ColumnInfo(name = "note_id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "note_name")
    private String name;

    @ColumnInfo(name = "note_body")
    private String body;

    @ColumnInfo(name = "course_id")
    private Long courseID;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }
}