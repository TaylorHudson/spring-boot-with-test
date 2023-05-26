package com.springcoursesecurity.exception;

import lombok.*;

@Setter @Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private long timeStamp;

}
