package mo.edu.ipm.siweb.data.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import mo.edu.ipm.siweb.data.model.ExamTime;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ExamTimeDao{

    @Insert(onConflict = REPLACE)
    void insert(ExamTime examTime);

    @Insert(onConflict = REPLACE)
    void insertMultipleListRecord(List<ExamTime> examTimeList);

    @Query("DELETE FROM ExamTime")
    void deleteAll();

    @Query("SELECT * FROM ExamTime ORDER BY endedAt DESC" )
    LiveData<List<ExamTime>> getExamsTime();
}
