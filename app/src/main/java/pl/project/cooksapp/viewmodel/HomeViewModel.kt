package pl.project.cooksapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.project.cooksapp.model.Recipe
import pl.project.cooksapp.util.Storage

class HomeViewModel : ViewModel() {
    var recipesList = mutableListOf<Recipe>()
        private set

    var inputSearch by mutableStateOf("")
        private set

    fun loadRecipeList(context: Context) {
        recipesList = Storage.readRecipeList(context = context).toMutableList()
    }

    fun writeRecipe(context: Context, recipe: Recipe) {
        recipesList.add(recipe)
        Storage.writeRecipeList(context = context, recipeList = recipesList)
    }

    fun removeRecipe(context: Context, recipe: Recipe) {
        recipesList.remove(recipe)
        Storage.writeRecipeList(context = context, recipeList = recipesList)
    }

    fun changeSearch(value: String) {
        inputSearch = value
    }
}