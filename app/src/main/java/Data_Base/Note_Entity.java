package Data_Base;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes_table")

public class Note_Entity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String note;
    private String date;
    private String description;
    public void setId(int id) {
        this.id = id;
    }



    public int getId() {
        return id;
    }

    public String getDescription() { return description; }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public Note_Entity( String note,String description,String date) {
        this.date=date;

this.description=description;
        this.note = note;

    }
}
