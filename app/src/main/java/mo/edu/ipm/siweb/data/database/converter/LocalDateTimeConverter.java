package mo.edu.ipm.siweb.data.database.converter;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import androidx.room.TypeConverter;

/**
 * Allows to convert some Joda objects.
 */

public class LocalDateTimeConverter {

    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;

    /**
     * Converts a Long to a DateTime.
     *
     * @param value The Long value.
     * @return The DateTime object.
     */

    @TypeConverter
    public static LocalDateTime toLocalDateTime(final long value) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.systemDefault());
    }

    /**
     * Converts a Long to a LocalDate.
     *
     * @param value The Long value.
     * @return The LocalDate object.
     */

    @TypeConverter
    public static LocalDate toLocalDate(final long value) {
        return Instant.ofEpochSecond(value).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Converts a DateTime to a Long.
     *
     * @param value The DateTime value.
     * @return The Long object.
     */

    @TypeConverter
    public static long toLong(final LocalDateTime value) {
        return value.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    @TypeConverter
    public static long toLong(final LocalDate value) {
        return value.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    @TypeConverter
    public static String toString(final LocalTime value) {
        return formatter.format(value);
    }

    @TypeConverter
    public static LocalTime toLocalTime(final String value) {
        return LocalTime.parse(value, formatter);
    }

}