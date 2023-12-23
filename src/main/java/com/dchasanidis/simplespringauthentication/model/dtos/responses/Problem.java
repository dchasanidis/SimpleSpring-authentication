package com.dchasanidis.simplespringauthentication.model.dtos.responses;

import java.time.LocalDateTime;

public record Problem(
        String id,
        String identifier,
        Integer status,
        LocalDateTime timestamp,
        String message,
        ProblemRequest problemRequest
) {
}
//    Problem:
//      type: object
//      description: |
//        Structure that is returned in case of detected problems. |
//        See https://confluence.ag.ch/pages/viewpage.action?spaceKey=WP&title=Error+Handling
//      properties:
//        id:
//          type: string
//        identifier:
//          type: string
//        status:
//          type: integer
//        timestamp:
//          type: string
//          format: date-time
//        message:
//          type: string
//        request:
//          $ref: '#/components/schemas/ProblemRequest'
