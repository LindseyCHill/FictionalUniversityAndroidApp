package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Term.class,
                        parentColumns = "term_id",
                        childColumns = "term_id"),
                @ForeignKey (
                        entity = Instructor.class,
                        parentColumns = "instructor_id",
                        childColumns = "instructor_id")})
public class Course {
    @ColumnInfo(name = "course_id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "course_name")
    private String name;

    @ColumnInfo(name = "course_start")
    private String start;

    @ColumnInfo(name = "course_end")
    private String end;

    @ColumnInfo(name = "course_status")
    private String status;

    @ColumnInfo(name = "term_id")
    private Long termID;

    @ColumnInfo(name = "instructor_id")
    private Long instructorID;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTermID() {
        return termID;
    }

    public void setTermID(Long termID) {
        this.termID = termID;
    }

    public Long getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(Long instructorID) {
        this.instructorID = instructorID;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
