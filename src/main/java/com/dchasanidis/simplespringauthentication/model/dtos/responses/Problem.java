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
