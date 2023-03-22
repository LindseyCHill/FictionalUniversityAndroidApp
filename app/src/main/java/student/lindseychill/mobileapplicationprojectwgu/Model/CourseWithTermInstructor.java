package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CourseWithTermInstructor {
    @Embedded
    public Course course;
    @Relation(
            parentColumn = "term_id",
            entityColumn = "term_id"
    )
    public Term term;

    @Relation(
            parentColumn = "instructor_id",
            entityColumn = "instructor_id"
    )
    public Instructor instructor;

    public Course getCourse() {
        return course;
    }

    public Term getTerm() {
        return term;
    }

    public Instructor getInstructor() {
        return instructor;
    }
}