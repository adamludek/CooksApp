package pl.project.cooksapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.project.cooksapp.R
import pl.project.cooksapp.model.Recipe
import pl.project.cooksapp.viewmodel.EditRecipeViewModel
import pl.project.cooksapp.viewmodel.HomeViewModel
import pl.project.cooksapp.viewmodel.NewRecipeViewModel


class EditRecipeActivity : ComponentActivity() {
    val viewModel: EditRecipeViewModel by viewModels<EditRecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val recipeOld = intent.getSerializableExtra("RecipeToEdit") as? Recipe

        recipeOld?.let {
            viewModel.setValuesToEdit(recipeOld)
        }
        super.onCreate(savedInstanceState)
        setContent {
            EditRecipeView(viewModel, recipeOld)
        }
    }
    @Composable
    fun EditRecipeView(viewModel: EditRecipeViewModel, recipeOld: Recipe?) {
        val context = LocalContext.current
        var titleLength = viewModel.title.length > 3
        var descriptionLength = viewModel.description.length > 15
        var prepTimeLength = viewModel.prepTime.length > 1
        var ingredientLength = viewModel.newIngredient.length > 2
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.fix_100))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.fix_300),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .testTag("GO_BACK")
                            .padding(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Powrót do listy przepisów"
                        )
                    }
                    Text(
                        text = "Zmień przepis",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.changeTitle(it) },
                    label = { Text(text = "Nazwa") },
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                if (!titleLength) {
                    Text(text = "Nazwa musi być dłuższa niż 3 znaki.", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                }
                OutlinedTextField(
                    value = viewModel.description,
                    onValueChange = { viewModel.changeDescription(it) },
                    label = { Text(text = "Opis") },
                    minLines = 3,
                    maxLines = 3,
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                if (!descriptionLength) {
                    Text(text = "Opis musi być dłuższa niż 15 znaków.", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = viewModel.prepTime,
                    onValueChange = { viewModel.changePrepTime(it) },
                    label = { Text(text = "Czas przygotowania") },
                    maxLines = 1,
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                if (!prepTimeLength) {
                    Text(text = "Czas przygotowania musi być dłuższy niż 1 znak.", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Składniki:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = viewModel.newIngredient,
                        onValueChange = { viewModel.changeNewIngredient(it) },
                        maxLines = 1,
                        placeholder = { Text(text = "Składnik (min 3 zn)") },
                        textStyle = TextStyle(fontSize = 14.sp),
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp)
                    )
                    Button(
                        onClick = { viewModel.addIngredient() },
                        modifier = Modifier.testTag("ADD_INGREDIENT"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.fix_300),
                            contentColor = Color.White
                        ),
                        enabled = ingredientLength
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Dodaj składnik",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyColumn {
                    items(viewModel.ingredientsList) { ingredient ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { viewModel.removeIngredient(ingredient = ingredient) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.fix_300),
                                    contentColor = Color.White

                                )

                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Usuń")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "- $ingredient")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val recipe = viewModel.saveRecipe()
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.putExtra("Recipe", recipe)
                        intent.putExtra("ToRemove", recipeOld)
                        startActivity(intent)
                        finish()
                    },
                    modifier = Modifier.fillMaxWidth().testTag("SAVE"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.fix_300)
                    ),
                    enabled = titleLength && descriptionLength && prepTimeLength
                ) {
                    Text(text = "Zapisz", fontSize = 20.sp)
                }
            }
        }
    }
}