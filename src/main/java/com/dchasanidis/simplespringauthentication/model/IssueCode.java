package com.dchasanidis.simplespringauthentication.model;

import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Arrays;

public interface IssueCode {
    default String getKey() {
        IssueCode thisIssue = this;
        Field fieldForThis = Arrays.stream(getClass().getEnclosingClass().getDeclaredFields())
                .filter(field -> IssueCode.class.isAssignableFrom(field.getType()))
                .filter(field -> {
                    try {
                        return thisIssue == field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst()
                .orElseThrow();


        return fieldForThis.getName();
    }

    default HttpStatus getResponseStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    String getMessage();
}
