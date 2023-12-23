package com.dchasanidis.simplespringauthentication.model.dtos.responses;

public record ProblemRequest(
        String url,
        String method
) {

}
//    ProblemRequest:
//      type: object
//      properties:
//        url:
//          type: string
//        method:
//          type: string
