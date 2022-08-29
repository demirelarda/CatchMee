package com.mycompany.catchme.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mycompany.catchme.R
import kotlinx.android.synthetic.main.activity_select_dif.*

class SelectDifActivity : AppCompatActivity() {

    var dif : Int = 0
    var difString = ""
    var savedDif : Int = 0
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_dif)

        sharedPreferences = this.getSharedPreferences("com.mycompany.catchkennyv3", MODE_PRIVATE)
        val difcFromSP = sharedPreferences.getInt("dif",0)


        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if(R.id.easyButton == i){
                dif = 0
            }
            if(R.id.mediumButton == i){
                dif = 1
            }
            if(R.id.hardButton == i){
                dif = 2
            }
            //println("Dif = ${dif}")
        }





        /*
        val intent = intent
        val savedDif = intent.getIntExtra("difficulty",0)
        */


        savedDif = difcFromSP
        if(savedDif == 0){
            difString = "Easy"
        }
        if(savedDif == 1){
            difString = "Medium"
        }
        if(savedDif == 2){
            difString = "Hard"
        }
        savedDif = difcFromSP
        difText.text = "Current Difficulty: "+difString


    }


    fun saveDif(view: View){
        sharedPreferences.edit().putInt("dif",dif).apply()
        val intent = Intent(applicationContext, MainMenuActivity::class.java)
        //println("Dif = ${dif}")
        //intent.putExtra("difficulty",dif)
        println("DifcFromSp = $savedDif")
        intent.putExtra("difficulty",savedDif)
        startActivity(intent)

    }
}