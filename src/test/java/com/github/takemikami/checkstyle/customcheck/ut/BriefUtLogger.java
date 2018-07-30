package com.github.takemikami.checkstyle.customcheck.ut;

import java.io.OutputStream;

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;

public class BriefUtLogger extends DefaultLogger {
    /**
     * Creates BriefLogger object.
     * @param out output stream for info messages and errors.
     */
    public BriefUtLogger(OutputStream out) {
        super(out, OutputStreamOptions.CLOSE, out,
                OutputStreamOptions.NONE, new AuditEventUtFormatter());
    }

    @Override
    public void auditStarted(AuditEvent event) {
        //has to NOT log audit started event
    }

}
