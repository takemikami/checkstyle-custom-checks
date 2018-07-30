package com.github.takemikami.checkstyle.customcheck.ut;

import com.puppycrawl.tools.checkstyle.AuditEventFormatter;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;

public class AuditEventUtFormatter  implements AuditEventFormatter {
    /** Length of all separators. */
    private static final int LENGTH_OF_ALL_SEPARATORS = 4;

    @Override
    public String format(AuditEvent event) {
        final String fileName = event.getFileName();
        final String message = event.getMessage();

        // avoid StringBuffer.expandCapacity
        final int bufLen = event.getFileName().length() + event.getMessage().length()
                + LENGTH_OF_ALL_SEPARATORS;
        final StringBuilder sb = new StringBuilder(bufLen);

        sb.append(fileName).append(':').append(event.getLine());
        if (event.getColumn() > 0) {
            sb.append(':').append(event.getColumn());
        }
        sb.append(": ").append(message);

        return sb.toString();
    }

}
