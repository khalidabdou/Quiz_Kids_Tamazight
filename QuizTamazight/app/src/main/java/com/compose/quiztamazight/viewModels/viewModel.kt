package com.compose.quiztamazight.viewModels

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.compose.quiztamazight.Question
import com.compose.quiztamazight.Utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class viewModel(context: Context) : ViewModel() {
    val questionFromJson = Utils.getJsonDataFromAsset(context = context, "questions.json")
    val gson = Gson()
    val listPersonType = object : TypeToken<List<Question>>() {}.type
    var questions: List<Question> = gson.fromJson(questionFromJson, listPersonType)
    var index by mutableStateOf(0)
    var mediaPlayer = MediaPlayer()
    var folder = "body"
    var endList = 10
    var results by mutableStateOf(0)

    fun next(): Boolean {
        if (index < endList - 1) {
            index++
            Log.d("index s", results.toString())
            return true
        }
        return false
    }

    fun playSound(context: Context, sound: Int) {
        mediaPlayer = MediaPlayer.create(context, sound)
        mediaPlayer.start()
    }


}