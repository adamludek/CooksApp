package pl.project.cooksapp.model

import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val prepTime: String,
    var ingredientsList: List<Ingredient> = emptyList(),
    val createdAt: String = LocalDateTime.now(ZoneOffset.UTC).toString(),
) : Serializable

@JsonClass(generateAdapter = true)
data class Ingredient(
    val id: String = UUID.randomUUID().toString(),
    val recipeId: String,
    val name: String,
) : Serializable