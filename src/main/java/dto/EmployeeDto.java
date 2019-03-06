package dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String emailId;

}