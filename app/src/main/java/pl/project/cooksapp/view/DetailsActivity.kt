package pl.project.cooksapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.project.cooksapp.model.Recipe
import pl.project.cooksapp.view.ui.theme.CooksAppTheme

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipe = intent.getSerializableExtra("recipe") as Recipe
        setContent {
            DetailsView(recipe)
        }
    }
    @Composable
    fun DetailsView(recipe: Recipe) {
        val context = LocalContext.current
        Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
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
                Text(text = recipe.title, fontWeight = FontWeight.Bold, fontSize = 24.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.putExtra("ToRemove", recipe)
                        startActivity(intent)
                        finish()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Usuń przepis"
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = recipe.description, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Składniki:")
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(recipe.ingredientsList) {
                    Text(text = it.name)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Kalorie: ${recipe.calories}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(10.dp))
                }
            }

}