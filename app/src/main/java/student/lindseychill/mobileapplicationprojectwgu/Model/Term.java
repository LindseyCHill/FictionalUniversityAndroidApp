package student.lindseychill.mobileapplicationprojectwgu.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "term_table")
public class Term {
    @ColumnInfo(name = "term_id")
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "term_name")
    private String name;

    @ColumnInfo(name = "term_start")
    private String start;

    @ColumnInfo(name = "term_end")
    private String end;

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

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}