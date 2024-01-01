package com.dchasanidis.simplespringauthentication.model;

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

    IssueCode USERNAME_OR_EMAIL_EXISTS = () -> "Username or email already exists";

    IssueCode INTERNAL_APPLICATION_ERROR = () -> "Uncaught exception resulting in internal application error";

}
