package dto;

import lombok.Data;

@Data
public class Response {

    private String message;
    private Boolean operationResult;
    private Object body;

}
