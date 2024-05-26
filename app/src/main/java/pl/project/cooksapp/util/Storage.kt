package pl.project.cooksapp.util

import android.content.Context
import pl.project.cooksapp.model.Recipe

object Storage {
    private const val SHARED_PREFS_NAME = "COOKS_APP"
    private const val RECIPE_LIST_KEY = "RECIPE_LIST"

    fun readRecipeList(context: Context) : List<Recipe> {
        val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        val jsonList = sharedPrefs.getString(RECIPE_LIST_KEY, null)

        return if (jsonList != null) JsonConverter.recipeListFromJson(jsonList) else emptyList()
    }

    fun writeRecipeList(context: Context, recipeList: List<Recipe>) {
        val jsonList = JsonConverter.recipeListToJson(recipeList)

        val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit()
        sharedPrefs.putString(RECIPE_LIST_KEY, jsonList)
        sharedPrefs.apply()
    }
}