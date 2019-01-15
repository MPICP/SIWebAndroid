package mo.edu.ipm.siweb.util.converter;

import android.os.Build;

import org.jsoup.nodes.Element;

import java.time.LocalDate;

import pl.droidsonroids.jspoon.ElementConverter;
import pl.droidsonroids.jspoon.annotation.Selector;

public class LocalDateConverter implements ElementConverter<LocalDate> {

    @Override
    public LocalDate convert(Element node, Selector selector) {
        String[] date = node.text().split("/");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.of(year, month, day);
        }

        // TODO: not compatible with api < 26
        return null;
    }
}
