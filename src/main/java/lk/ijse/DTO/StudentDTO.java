package lk.ijse.DTO;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class StudentDTO {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private int contactNo;
    private String email;
    private String address;
}

