package mo.edu.ipm.siweb.data.database.converter;

import androidx.room.TypeConverter;

public class BooleanArrayConverter {
    @TypeConverter
    public static boolean[] fromLong(String value) {
        boolean[] result = new boolean[value.length()];
        for (int i = 0; i != value.length(); ++i) {
            boolean bool = false;
            char c = value.charAt(i);
            if (c == '1') {
                bool = true;
            }
            result[i] = bool;
        }

        return result;
    }

    @TypeConverter
    public static String boolToString(boolean[] value) {
        String result = "";
        for (boolean bool : value) {
            result += bool ? '1' : '0';
        }
        return result;
    }
}
