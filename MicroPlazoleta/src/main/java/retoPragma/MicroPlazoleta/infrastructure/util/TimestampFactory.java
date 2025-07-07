package retoPragma.MicroPlazoleta.infrastructure.util;

import retoPragma.MicroPlazoleta.domain.model.TraceabilityTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampFactory {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public static TraceabilityTimestamp now() {
        String isoTimestamp = LocalDateTime.now().format(FORMATTER);
        return new TraceabilityTimestamp(isoTimestamp);
    }
}
