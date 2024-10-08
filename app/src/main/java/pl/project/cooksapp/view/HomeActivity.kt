package pl.project.cooksapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.project.cooksapp.model.Recipe
import pl.project.cooksapp.viewmodel.HomeViewModel

class HomeActivity : ComponentActivity() {
    val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadRecipeList(context)
        val recipe = intent.getSerializableExtra("Recipe") as? Recipe
        val recipeRemove = intent.getSerializableExtra("ToRemove") as? Recipe

        recipe?.let {
            viewModel.writeRecipe(context = context, recipe = recipe)
        }

        recipeRemove?.let {
            viewModel.removeRecipe(context = context, recipe = recipeRemove)
        }

        setContent {
            HomeView(viewModel)
        }
    }
    @Composable
    fun HomeView(viewModel: HomeViewModel) {
        val context = LocalContext.current
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.inputSearch,
                    onValueChange = {viewModel.changeSearch(it)},
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.White, focusedBorderColor = Color.White),
                    placeholder = { Text(text = "Wyszukaj ...", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())},
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth().testTag("SEARCH"))
                Text(text = "Przepisy:")
                Spacer(modifier = Modifier.height(20.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(if (viewModel.inputSearch == "") viewModel.recipesList else viewModel.recipesList.filter { recipe -> recipe.title.lowercase().contains(viewModel.inputSearch.lowercase()) }) {recipe ->
                        Card(modifier = Modifier.clickable {
                            val intent = Intent(context, DetailsActivity::class.java)
                            intent.putExtra("recipe", recipe)
                            startActivity(intent)
                            finish()
                        }) {
                            Text(
                                text = recipe.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                                )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = recipe.description,
                                fontSize = 16.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                                )
                        }
                    }
                }
            }
            FloatingActionButton(onClick = { val intent = Intent(context, NewRecipeActivity::class.java)
                startActivity(intent)
                finish()
                                           },
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp).testTag("ADD_RECIPE")) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "New recipe" )
            }
        }
    }
}
