package pl.project.cooksapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import org.junit.Rule
import org.junit.Test
import pl.project.cooksapp.view.HomeActivity

class AppTests {
    private val testIngredients = listOf("pierwszy", "drugi", "trzeci", "czwarty")
    @get:Rule
    val rule = createAndroidComposeRule<HomeActivity>()

    fun addRecipeTest(title: String, description: String, prepTime: String, ingredientsList: List<String>) {
        rule.onNodeWithTag("ADD_RECIPE").performClick()
        rule.onNodeWithText("Nazwa").performTextInput(title)
        rule.onNodeWithText("Opis").performTextInput(description)
        rule.onNodeWithText("Czas przygotowania").performTextInput(prepTime)
        for(ingredient in ingredientsList) {
            rule.onNodeWithText("Składnik").performTextInput(ingredient)
            rule.onNodeWithTag("ADD_INGREDIENT").performClick()
            Thread.sleep(500)
        }
        rule.onNodeWithTag("SAVE").performClick()
        Thread.sleep(2500)

    }

    @Test
    fun addRecipesTest() {
        addRecipeTest("Tort czekoladowy", LoremIpsum(30).values.joinToString(" "), "2 godz", testIngredients)
        addRecipeTest("Makaron", LoremIpsum(40).values.joinToString(" "), "2 godz", testIngredients)
        addRecipeTest("Kotlet schabowy", LoremIpsum(35).values.joinToString(" "), "2 godz", testIngredients)
        addRecipeTest("Zupa pomidorowa", LoremIpsum(20).values.joinToString(" "), "3 godz", testIngredients)
        addRecipeTest("Sałatka jarzynowa", LoremIpsum(50).values.joinToString(" "), "1 godz", testIngredients)
       // addRecipeTest("Chleb na zakwasie", LoremIpsum(45).values.joinToString(" "), "1 godz", testIngredients)
    }

    @Test
    fun searchTest() {
        rule.onNodeWithTag("SEARCH").performTextInput("zupa")
        Thread.sleep(2000)
        rule.onNodeWithTag("SEARCH").performTextInput("")
        Thread.sleep(2000)
    }

    @Test
    fun goBackFromNewRecipe() {
        rule.onNodeWithTag("ADD_RECIPE").performClick()
        rule.onNodeWithTag("GO_BACK").performClick()
        Thread.sleep(2000)

    }

    @Test
    fun removeRecipeTest() {
        rule.onNodeWithText("Tort czekoladowy").performClick()
        rule.onNodeWithTag("DELETE").performClick()
        Thread.sleep(2000)
    }

}