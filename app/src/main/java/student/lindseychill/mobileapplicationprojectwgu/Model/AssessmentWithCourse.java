package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class AssessmentWithCourse {
    @Embedded
    public Assessment assessment;
    @Relation(
            parentColumn = "course_id",
            entityColumn = "course_id"
    )
    public Course course;

    public Assessment getAssessment() {
        return assessment;
    }

    public Course getCourse() {
        return course;
    }
}

