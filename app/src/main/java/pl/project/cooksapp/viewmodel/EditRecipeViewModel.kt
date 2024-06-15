package pl.project.cooksapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import pl.project.cooksapp.model.Ingredient
import pl.project.cooksapp.model.Recipe

class EditRecipeViewModel : ViewModel() {
    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var prepTime by mutableStateOf("")
        private set

    var newIngredient by mutableStateOf("")
        private set

    var ingredientsList = mutableStateListOf<String>()
        private set

    fun setValuesToEdit(recipe: Recipe) {
        changeTitle(recipe.title)
        changeDescription(recipe.description)
        changePrepTime(recipe.prepTime)
        setIngredientsList(recipe.ingredientsList)
    }
    fun setIngredientsList(list: List<Ingredient>) {
        list.forEach{ingredient -> ingredientsList.add(ingredient.name) }
    }
    fun changeTitle(value: String) {
        title = value
    }

    fun changeDescription(value: String) {
        description = value
    }

    fun changePrepTime(value: String) {
        prepTime = value
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
            prepTime = prepTime
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