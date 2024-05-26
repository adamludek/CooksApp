package pl.project.cooksapp.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import pl.project.cooksapp.model.Recipe

object JsonConverter {
    private val moshi = Moshi.Builder().build()

    fun recipeToJson(recipe: Recipe) : String {
        val type = Recipe::class.java

        return moshi.adapter<Recipe>(type).toJson(recipe)
    }

    fun recipeListToJson(recipeList: List<Recipe>) : String {
        val type = Types.newParameterizedType(List::class.java, Recipe::class.java)
        
        return moshi.adapter<List<Recipe>>(type).toJson(recipeList)
    }

    fun recipeFromJson(json: String) : Recipe? {
        val type = Recipe::class.java

        return moshi.adapter<Recipe>(type).fromJson(json)
    }

    fun recipeListFromJson(recipeList: String) : List<Recipe> {
        val type = Types.newParameterizedType(List::class.java, Recipe::class.java)

        return moshi.adapter<List<Recipe>>(type).fromJson(recipeList) ?: emptyList()
    }
}