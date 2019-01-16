package mo.edu.ipm.siweb.data.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import mo.edu.ipm.siweb.data.model.ClassTime;
import mo.edu.ipm.siweb.data.model.ExamTime;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ClassTimeDao {

    @Insert(onConflict = REPLACE)
    void insert(ClassTime classTime);

    @Insert(onConflict = REPLACE)
    void insertMultipleListRecord(List<ClassTime> classTimeList);

    @Query("SELECT * FROM classtime")
    LiveData<List<ClassTime>> getClassTime();

    @Query("DELETE FROM classtime")
    void deleteAll();
}
