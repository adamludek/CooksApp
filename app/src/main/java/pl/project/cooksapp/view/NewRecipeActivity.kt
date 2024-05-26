package pl.project.cooksapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.project.cooksapp.model.Ingredient
import pl.project.cooksapp.model.Recipe
import pl.project.cooksapp.view.ui.theme.CooksAppTheme
import pl.project.cooksapp.viewmodel.NewRecipeViewModel

class NewRecipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: NewRecipeViewModel = viewModel()
            NewRecipeView(viewModel)
        }
    }
    @Composable
    fun NewRecipeView(viewModel: NewRecipeViewModel) {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
            Row {
                Button(
                    onClick = {
                        val intent = Intent(context, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Powrót do listy przepisów"
                    )
                }
                Text(
                    text = "Nowy przepis",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = viewModel.title, 
                onValueChange = { viewModel.changeTitle(it)},
                label = { Text(text = "Nazwa")},
                maxLines = 1,
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
            OutlinedTextField(
                value = viewModel.description,
                onValueChange = {viewModel.changeDescription(it)},
                label = { Text(text = "Opis")},
                minLines = 3,
                maxLines = 3,
                textStyle = (TextStyle(fontSize = 18.sp)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = viewModel.calories,
                onValueChange = {viewModel.changeCalories(it)},
                label = { Text(text = "Kalorie:")},
                maxLines = 1,
                textStyle = (TextStyle(fontSize = 18.sp)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Składniki:",)
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = viewModel.newIngredient,
                    onValueChange = {viewModel.changeNewIngredient(it)},
                    maxLines = 1,
                    placeholder = { Text(text = "Składnik")},
                    textStyle = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .width(200.dp)
                        .padding(8.dp)
                )
                Button(onClick = { viewModel.addIngredient() }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Dodaj składnik")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(viewModel.ingredientsList) { ingredient ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = { viewModel.removeIngredient(ingredient = ingredient) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Usuń")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "- $ingredient")
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {

                val recipe = viewModel.saveRecipe()

                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra("Recipe", recipe)
                startActivity(intent)

                finish()
                             },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Zapisz")
                
            }
        }
    }
}
