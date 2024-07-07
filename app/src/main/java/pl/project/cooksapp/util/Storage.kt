package pl.project.cooksapp.util

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.project.cooksapp.model.Recipe
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStreamReader


object Storage {
    private const val DATASTORE_NAME = "COOKS_APP"
    private val RECIPE_LIST_KEY = stringPreferencesKey("RECIPE_LIST")
    private val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/" + "CooksApp/"

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    suspend fun readRecipeList(context: Context) : List<Recipe> {
        val jsonList = context.dataStore.data.map { prefs -> prefs[RECIPE_LIST_KEY] ?: null }.first()

        return if (jsonList != null) JsonConverter.recipeListFromJson(jsonList) else emptyList()
    }

    suspend fun writeRecipeList(context: Context, recipeList: List<Recipe>) {
        val jsonList = JsonConverter.recipeListToJson(recipeList)

        context.dataStore.edit { prefs -> prefs[RECIPE_LIST_KEY] = jsonList }
    }

    fun writeRecipeListToFile(recipeList: List<Recipe>) {
        val jsonList = JsonConverter.recipeListToJson(recipeList)

        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val file = File(path + "data.json")
        if (!file.createNewFile()) {
            file.delete();
            file.createNewFile();
        }
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)

        try {
            bufferedWriter.write(jsonList)
            bufferedWriter.close()
        } catch (e: Exception) {
            Log.d("CooksAppError", e.message ?: "")
        }
    }

    fun readRecipeListFromFile() : List<Recipe> {
        if (File(path + "data.json").exists()) {
            val fileReader = FileReader(path + "data.json")
            val bufferedReader = BufferedReader(fileReader)
            val sb = StringBuilder()
            var s: String?
            while ((bufferedReader.readLine().also { s = it }) != null) {
                sb.append(s)
            }
            val json = sb.toString()
            val list = JsonConverter.recipeListFromJson(json)

            return list
        } else return emptyList()
    }
}