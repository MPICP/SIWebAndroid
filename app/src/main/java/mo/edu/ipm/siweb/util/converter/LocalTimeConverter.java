package mo.edu.ipm.siweb.util.converter;

import android.os.Build;

import org.jsoup.nodes.Element;

import java.time.LocalTime;

import pl.droidsonroids.jspoon.ElementConverter;
import pl.droidsonroids.jspoon.annotation.Selector;

public class LocalTimeConverter implements ElementConverter<LocalTime> {
    @Override
    public LocalTime convert(Element node, Selector selector) {
        String[] time = node.text().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalTime.of(hour, minute);
        }

        // TODO: incompatible with sdk version < 26
        return null;
    }
}
