package ch.qos.logback.core.pattern;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.core.pattern.parser.Node;
import ch.qos.logback.core.pattern.parser.Parser;
import ch.qos.logback.core.spi.ScanException;

public class ReplacingAndParsingCompositeConverter<E> extends ReplacingCompositeConverter<E> {

    @Override
    protected String transform(E event, String in) {
        if (!started) {
            return in;
        }

        String parsedReplacement;

        try {
            Parser<E> p = new Parser<E>(replacement);
            p.setContext(getContext());
            Node t = p.parse();
            Converter<E> c = p.compile(t, PatternLayout.defaultConverterMap);
            ConverterUtil.setContextForConverters(getContext(), c);
            ConverterUtil.startConverters(c);
            StringBuilder buf = new StringBuilder();
            while (c != null) {
                c.write(buf, event);
                c = c.getNext();
            }
            parsedReplacement = buf.toString();
        } catch (ScanException e) {
            e.printStackTrace();
            parsedReplacement = in;
        }
        return pattern.matcher(in).replaceAll(parsedReplacement);
    }
}
