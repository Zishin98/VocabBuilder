package com.example.zish_.vocabbuilder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_new_word.*
import java.io.PrintStream

class addNewWord : AppCompatActivity() {

    private val WORDS_FILE_NAME = "extraWords.txt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_word)
        //val name = intent.getStringExtra("name")
        //word_to_add.setText(name)
    }

    fun addWord(view: View) {
        val word = word_to_add.text.toString()

        val defn = word_defn.text.toString()

        val line = "$word - $defn"

        val outStream = PrintStream(openFileOutput(WORDS_FILE_NAME, Context.MODE_PRIVATE))
        outStream.println(line)
        outStream.close()

        //go back to main activity
        val myIntent = Intent()
        myIntent.putExtra("word",word)
        myIntent.putExtra("defn",defn)
        setResult(Activity.RESULT_OK,myIntent)
        finish()
    }
}
