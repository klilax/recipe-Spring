package com.klilax.recipe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @Email
    @NotBlank
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    private String role;

    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes;

    public boolean equals(User user) {
        return  id == user.getId();
    }

}
