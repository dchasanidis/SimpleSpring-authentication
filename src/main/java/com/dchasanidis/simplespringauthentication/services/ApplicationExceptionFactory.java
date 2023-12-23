package com.dchasanidis.simplespringauthentication.services;

import com.dchasanidis.simplespringauthentication.model.ApplicationException;
import com.dchasanidis.simplespringauthentication.model.IssueCode;

public interface ApplicationExceptionFactory {
    ApplicationException createApplicationException(IssueCode code, Throwable cause);

    default ApplicationException createApplicationException(IssueCode code) {
        return createApplicationException(code, null);
    }
}
