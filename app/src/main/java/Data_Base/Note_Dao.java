package Data_Base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Note_Dao {
    @Insert
    public  void addPost(Note_Entity note);
    @Query("Select * From Notes_table")
    public List<Note_Entity> getAllNotes();
    @Query("DELETE FROM Notes_table where id =:id")
    public void deleteNoteById(int id);
    @Query("UPDATE Notes_table SET note=:note , description=:dec , date=:date wHERE id=:id")
    public  void updatePost(int id ,String note,String dec ,String date);
//    @Update
//    public  void updatePost(Note_Entity note);
    @Delete
    public void deleteAllNOtes ( List<Note_Entity> notes);


}
