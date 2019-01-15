package mo.edu.ipm.siweb.util.converter;

import org.jsoup.nodes.Element;

import pl.droidsonroids.jspoon.ElementConverter;
import pl.droidsonroids.jspoon.annotation.Selector;

public class presentConverter implements ElementConverter<String> {
    @Override
    public String convert(Element node, Selector selector) {
        return node.getElementsByTag("img").isEmpty() ? node.text() : "Presented";
    }
}
