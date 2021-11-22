package com.klilax.recipe;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.klilax.recipe.repositories.RecipeRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class RecipeController {

    final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<RecipeId> postRecipe(@Valid @RequestBody Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        Recipe newRecipe = recipeRepository.save(recipe);
        return ResponseEntity.ok(new RecipeId(newRecipe.getId()));
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe) {
        Optional<Recipe> oldRecipe = recipeRepository.findById(id);

        if(oldRecipe.isPresent()) {
            Recipe newRecipe = oldRecipe.get();
            newRecipe.update(recipe);
            recipeRepository.save(newRecipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {

        Optional<Recipe> recipe = recipeRepository.findById(id);

        if(recipe.isPresent()) {
            return ResponseEntity.ok(recipe.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<?> searchRecipe(@RequestParam Optional<String> category,@RequestParam Optional<String> name) {
        String searchParm;
        if (category.isPresent() && name.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (category.isPresent()) {
            searchParm = category.get();
            return ResponseEntity.ok(recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(searchParm));
        } else if (name.isPresent()) {
            searchParm = name.get();
            return ResponseEntity.ok(recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(searchParm));
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()) {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
