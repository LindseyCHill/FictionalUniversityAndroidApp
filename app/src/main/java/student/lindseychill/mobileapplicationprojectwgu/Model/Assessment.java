package student.lindseychill.mobileapplicationprojectwgu.Model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table",
        foreignKeys = @ForeignKey(onDelete = CASCADE,
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id"))
public class Assessment {
    @ColumnInfo(name = "assessment_id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "assessment_name")
    private String name;

    @ColumnInfo(name = "assessment_type")
    private String type;

    @ColumnInfo(name = "assessment_start")
    private String start;

    @ColumnInfo(name = "assessment_end")
    private String end;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}