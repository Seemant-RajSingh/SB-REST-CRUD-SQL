package com.srs.spring_crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 100, message = "Address cannot exceed 100 characters")
    private String address;
}


//// persistance and entity etc. require :
//<dependency>
//    <groupId>org.springframework.boot</groupId>
//    <artifactId>spring-boot-starter-data-jpa</artifactId>
//</dependency>