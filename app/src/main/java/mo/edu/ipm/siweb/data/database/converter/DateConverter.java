package mo.edu.ipm.siweb.data.database.converter;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Date fromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static long DateToTimestamp(Date value) {
        return value.getTime();
    }
}
