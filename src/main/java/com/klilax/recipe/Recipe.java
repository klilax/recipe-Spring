package com.klilax.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    LocalDateTime date;
    @NotBlank
    private String description;
    @NotEmpty
    @ElementCollection
    private List<@NotBlank String> directions;
    @NotEmpty
    @ElementCollection
    private List<@NotBlank String> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    public void update(Recipe recipe) {
        name = recipe.getName();
        category = recipe.getCategory();
        date = LocalDateTime.now();
        description = recipe.getDescription();
        directions = recipe.getDirections();
        ingredients = recipe.getIngredients();
    }
}

@Getter
@Setter
class RecipeId {
    private long id;
    public RecipeId(long id) {
        this.id = id;
    }
}
