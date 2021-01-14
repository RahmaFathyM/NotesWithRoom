package Data_Base;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note_Entity.class},version= 6)
public abstract class NoteDataBase extends RoomDatabase {
    public static  final String DB_NAME = "Note_database" ;

    public abstract Note_Dao noteDao();
    public static NoteDataBase noteDataBase ;



    public static   NoteDataBase getInstance(Context context){
        if( noteDataBase == null)
        {
            //search
            noteDataBase= Room.databaseBuilder(context , NoteDataBase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();

        }
        return  noteDataBase ;
    }


}
