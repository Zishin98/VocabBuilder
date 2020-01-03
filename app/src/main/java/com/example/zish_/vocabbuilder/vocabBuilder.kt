package com.example.zish_.vocabbuilder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_vocab_builder.*
import java.io.BufferedReader
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class vocabBuilder : AppCompatActivity() {

    private val wordToDefn = HashMap<String,String>()
    private val words = ArrayList<String>()
    private val dfns = ArrayList<String>()
    private lateinit var myAdapter : ArrayAdapter<String>
    private lateinit var word:String
    private var points = 0
    private val STUPID_CODE = 123 //1-65535 so that your program knows which result to return from

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocab_builder)

        val reader = Scanner(resources.openRawResource(R.raw.grewords))
        readDictionaryFile(reader) //read dictionary and display random definitions
        val reader2 = Scanner(openFileInput("extraWords.txt")) //read in from the extra words file
        readDictionaryFile(reader2)
        setupList()

        definitions.setOnItemClickListener{ _, _, index, _ ->

            val keys = wordToDefn.filterValues { it == dfns[index] }.keys //get the key of the selected answer
            val value = wordToDefn[word] //value of the actual answer
            for(key in keys) {
                if(key == word ) {
                    points++
                    answer.text =""
                    Toast.makeText(this, "Good job! +1", Toast.LENGTH_SHORT).show()

                } else {
                    points--
                    answer.text = "The correct answer is $value."
                    Toast.makeText(this, "Oops, that's not correct! -1", Toast.LENGTH_SHORT).show()
                }
            }

            findViewById<TextView>(R.id.points).text = "Points: $points"
            dfns.removeAll(dfns)
            myAdapter.notifyDataSetChanged()
            setupList()
            myAdapter.notifyDataSetChanged()
        }

    }

    private fun readDictionaryFile(reader:Scanner) {
        while(reader.hasNextLine()) {
            val line = reader.nextLine()
            Log.d("LOL","the line is $line")
            val pieces = line.split("-")
            words.add(pieces[0])
            var defn = pieces[1]
            if(pieces.size>1) {
                for(piece in pieces.indices) {
                    if(piece>1) {
                        defn = defn + "-" + pieces[piece]
                    }
                }
            }
            wordToDefn.put(pieces[0],defn)
        }
    }
    fun setupList() {

        //pick a random word
        val rand = Random()
        val index = rand.nextInt(words.size)
        word = words[index]
        the_word.text = word

        //pick random definitions for the word
        //two !! to indicate I insist the String will not be null
        dfns.add(wordToDefn[word]!!)

        for (otherWord in words.subList(0,5)) {
            if(otherWord == word || dfns.size == 5) {
                continue
            }
            dfns.add(wordToDefn[otherWord]!!)
        }
        dfns.shuffle()

        myAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dfns)
        definitions.adapter = myAdapter

    }

    fun addWordClick(view:View) {
        val myIntent = Intent(this, addNewWord::class.java)
        //myIntent.putExtra("name","BTS") to show how to pass result to second activity
        startActivityForResult(myIntent,STUPID_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, myIntent: Intent?) {
        if(requestCode == STUPID_CODE) {
            //unpack the word and definition send back from addNewWord
            if(myIntent != null) {
                val word = myIntent.getStringExtra("word")
                val defn = myIntent.getStringExtra("defn")
                wordToDefn.put(word,defn)
                // or wordToDefn[word] = defn
                words.add(word)
            }

        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("zishin","onStart was called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("zishin","onRestoreInstanceState was called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("zishin","onResume was called")
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i("zishin","onSaveInstanceState was called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("zishin","onPause was called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("zishin","onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("zishin","onDestroy was called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("zishin","onRestart was called")
    }
}

/* val list = ArrayList<String>()
        list.add("Biology")
        list.add("Chemistry")
        list.add("Compute Science")
        list.add("Economics")
        list.add("Music")
        list.add("Physics")
        list.add("Mathematics")
       dfns.add("Haemoglobin")
        dfns.add("Hydrochloric acid")
        dfns.add("Boolean = true")
        dfns.add("Aggregate demand")
        dfns.add("C# major")
        dfns.add("Gravitational force")
        dfns.add("Euler")*/
