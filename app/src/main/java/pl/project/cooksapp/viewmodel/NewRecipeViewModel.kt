package pl.project.cooksapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.project.cooksapp.model.Ingredient
import pl.project.cooksapp.model.Recipe

class NewRecipeViewModel : ViewModel() {
    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var calories by mutableStateOf("")
        private set

    var newIngredient by mutableStateOf("")
        private set

    var ingredientsList = mutableStateListOf<String>()
        private set

    fun changeTitle(value: String) {
        title = value
    }

    fun changeDescription(value: String) {
        description = value
    }

    fun changeCalories(value: String) {
        calories = value
    }

    fun changeNewIngredient(value: String) {
        newIngredient = value
    }

    fun addIngredient() {
        if (newIngredient.length >2) {
            ingredientsList.add(newIngredient)

            newIngredient = ""
        }
    }

    fun removeIngredient(ingredient: String) {
        ingredientsList.remove(ingredient)
    }

    fun saveRecipe() : Recipe {
        val recipe = Recipe(
            title = title,
            description = description,
            calories = calories
        )
        ingredientsList.forEach { item ->
            recipe.ingredientsList += Ingredient(
                recipeId = recipe.id,
                name = item
            )
        }
        title = ""
        description = ""
        ingredientsList.clear()
        return recipe
    }
}