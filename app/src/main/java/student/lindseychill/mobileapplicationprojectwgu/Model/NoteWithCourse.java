package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class NoteWithCourse {
    @Embedded public Note note;
    @Relation(
            parentColumn = "course_id",
            entityColumn = "course_id"
    )
    public Course course;

    public Note getNote() {
        return note;
    }

    public Course getCourse() {
        return course;
    }
}