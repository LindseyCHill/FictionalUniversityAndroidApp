package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ProgressWithCourse {
    @Embedded public Progress progress;
    @Relation(
            parentColumn = "course_id",
            entityColumn = "course_id"
    )
    public Course course;

    public Progress getProgress() {
        return progress;
    }

    public Course getCourse() {
        return course;
    }
}
