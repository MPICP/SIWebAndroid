package mo.edu.ipm.siweb.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import mo.edu.ipm.siweb.data.database.converter.BooleanArrayConverter;
import mo.edu.ipm.siweb.data.database.converter.DateConverter;
import mo.edu.ipm.siweb.data.database.converter.LocalDateTimeConverter;
import mo.edu.ipm.siweb.data.database.dao.ClassTimeDao;
import mo.edu.ipm.siweb.data.database.dao.ExamTimeDao;
import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.model.ExamTime;

@Database(entities = {ExamTime.class, ClassTime.class}, version = 1)
@TypeConverters({LocalDateTimeConverter.class, DateConverter.class, BooleanArrayConverter.class})
public abstract class SIWebRoomDatabase extends RoomDatabase {

    public abstract ExamTimeDao mExamTimeDao();
    public abstract ClassTimeDao mClassTimeDao();

    private static volatile SIWebRoomDatabase instance;

    public static SIWebRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (SIWebRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            SIWebRoomDatabase.class, "siweb_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                }
            };
}
