package com.springcoursesecurity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Builder
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

}