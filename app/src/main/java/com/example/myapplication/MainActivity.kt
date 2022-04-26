package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var gameView  : GameViewModel

   private val wordView :TextView
        get() = findViewById(R.id.Word)
   private val livesView :TextView
        get() = findViewById(R.id.lives)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity","View Model Provide.get called")
        gameView = ViewModelProvider(this).get(GameViewModel::class.java)

        gameView.wordToDisplay.observe(this) {
            wordView.text = it
        }
        gameView.lives.observe(this){
            livesView.text = it.toString()
        }
//        updateLives()
//        updateWord()
    }
//    private fun updateLives(){
//        val livesView :TextView = findViewById(R.id.lives)
//        livesView.text = gameView.lives.toString()
//    }
//
//    private fun updateWord(){
//        val wordView :TextView = findViewById(R.id.Word)
//        wordView.text = gameView.wordToDisplay
//    }
    private fun play(word : Char){
        var response :Int = gameView.play(word)
//        updateWord()
//        updateLives()
        when(response){
            //0 is game lost
            0-> {
                Toast.makeText(this,R.string.loose,Toast.LENGTH_SHORT).show()
                showGameEnd()
            }
            //1 is game won
            1->{
                Toast.makeText(this,R.string.won,Toast.LENGTH_SHORT).show()
                showGameEnd()
            }
            //2 is wrong selection
            2->{
                Toast.makeText(this,R.string.wrongSelection,Toast.LENGTH_SHORT).show()
            }
            else->{
                Toast.makeText(this,R.string.rightSelection,Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun buttonClicked(view: View)  {
        val button : Button  = view as Button
        val letter :String = button.text.toString()
        play(letter[0])
    }


    private  fun showGameEnd(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.gameend_dialog)
        val button :Button = dialog.findViewById(R.id.restart)

        button.setOnClickListener {
            dialog.dismiss()
//            val i :Intent = Intent(baseContext,MainActivity::class.java)
//            startActivity(i)
            gameView.setUp()
//            updateLives()
//            updateWord()
        }
        dialog.show()
    }


    //    private fun setUp(){
//
//        val toDisplay = StringBuffer()
//        for(i in word.indices){
//            val c : Char = word[i]
//            val oldSet :HashSet<Int> = wordMap[c] ?: HashSet()
//            oldSet.add(i)
//            wordMap[c] = oldSet
//            toDisplay.append("_ ")
//        }
//        lives = 5
//        val wordView :TextView = findViewById(R.id.Word)
//        wordView.text = toDisplay.toString()
//        val livesView :TextView = findViewById(R.id.lives)
//        livesView.text = lives.toString()
//    }

}