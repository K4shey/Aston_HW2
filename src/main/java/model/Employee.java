package model;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends AbstractBaseEntity {
    private String name;
    private String email;
    private int age;
    private String citizenship;
    private Department department;
}