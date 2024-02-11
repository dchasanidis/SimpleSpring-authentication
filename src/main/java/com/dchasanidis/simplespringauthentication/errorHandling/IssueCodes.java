package com.dchasanidis.simplespringauthentication.errorHandling;

import com.dchasanidis.simplespringauthentication.model.IssueCode;
import org.springframework.http.HttpStatus;

public interface IssueCodes {

    IssueCode AUTHENTICATION_FAILED = new IssueCode() {
        @Override
        public HttpStatus getResponseStatus() {
            return HttpStatus.FORBIDDEN;
        }

        @Override
        public String getMessage() {
            return "Authentication failed. Check credentials";
        }
    };

    IssueCode USERNAME_OR_EMAIL_EXISTS = new IssueCode() {
        @Override
        public String getMessage() {
            return "Username or email already exists";
        }

        @Override
        public HttpStatus getResponseStatus() {
            return HttpStatus.BAD_REQUEST;
        }
    };

    IssueCode INTERNAL_APPLICATION_ERROR = () -> "Uncaught exception resulting in internal application error";
}
