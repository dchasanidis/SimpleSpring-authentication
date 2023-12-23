package com.dchasanidis.simplespringauthentication.model;

import java.util.UUID;

public class ApplicationException extends RuntimeException {
    private final UUID id;
    private final IssueCode issueCode;
    private String debugMessage;

    public ApplicationException(
            final UUID id,
            final IssueCode issueCode,
            final String message,
            final String debugMessage
    ) {
        super(message);
        this.id = id;
        this.issueCode = issueCode;
        this.debugMessage = debugMessage;
    }

    public UUID getId() {
        return id;
    }

    public IssueCode getIssueCode() {
        return issueCode;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public ApplicationException setDebugMessage(final String debugMessage) {
        this.debugMessage = debugMessage;
        return this;
    }
}