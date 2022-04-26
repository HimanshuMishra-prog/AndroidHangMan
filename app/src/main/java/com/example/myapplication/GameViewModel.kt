package com.example.myapplication

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){
    private val word : String = "ELEPHANT"
    private val wordMap : HashMap<Char,HashSet<Int>> = HashMap()
//    var lives : Int = 0
//    var wordToDisplay :String = ""

    private val _lives = MutableLiveData<Int>(0)
    val lives :LiveData<Int>
        get() = _lives

    private val _wordToDisplay = MutableLiveData<String>(" ")
    val wordToDisplay : LiveData<String>
        get() = _wordToDisplay

    init{
        Log.i("GameViewModel","GameViewModel Created")
        Log.i("GameViewModel","Setup called")
        setUp()
    }
    fun setUp(){
         val toDisplay = StringBuffer()
         for(i in word.indices){
            val c : Char = word[i]
            val oldSet :HashSet<Int> = wordMap[c] ?: HashSet()
            oldSet.add(i)
            wordMap[c] = oldSet
            toDisplay.append("_ ")
         }
         _lives.value = 5
         _wordToDisplay.value = toDisplay.toString()
    }
    fun play(word : Char) : Int{
        if(!wordMap.containsKey(word)){
            _lives.value  = _lives.value!! - 1
            if(_lives.value == 0){
                //0 is game lost
                return 0
            }
            return 2
        }
        val indexes: HashSet<Int>  = wordMap[word] ?: HashSet<Int>()
        val currentText = _wordToDisplay.value?.let { StringBuffer(it) }
        for(i in indexes){
            val j :Int= 2*i
            currentText?.setCharAt(j,word)

        }
        _wordToDisplay.value = currentText.toString()
        if(!currentText!!.contains('_')){
            return 1
        }
        return 3
    }
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","Game View Model Destroyed")
    }
}