package com.mycompany.catchme.view

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.ChangeCharacterActivity
import com.mycompany.catchme.R
import com.mycompany.catchme.adapter.InventoryAdapter
import com.mycompany.catchme.adapter.SelectCharacterAdapter
import com.mycompany.catchme.databinding.ActivityMainBinding
import com.mycompany.catchme.model.StoreObjects
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var score = 0
    var simpsonArray = ArrayList<ImageView>()
    var bmbArray = ArrayList<ImageView>()
    var runnable = Runnable{ }
    var handler : Handler = Handler(Looper.getMainLooper())
    var diff : Int = 0
    var delayMs : Int = 0
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var auth : FirebaseAuth
    var coinMultiply : Double = 0.0
    val db = Firebase.firestore
    var coins : Double = 0.0
    var difString : String = ""
    var easyScore : Int = 0
    var mediumScore : Int = 0
    var hardScore : Int = 0
    var coinsFromFB : Double = 0.0
    var lastCoins : Double = 0.0
    var boughtProductArray = ArrayList<Long>()
    var boughtItemsArray = ArrayList<Long>()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //get selected character id
        val changeCharacterId = intent.getIntExtra("selectedId",0)
        println("id from change: $changeCharacterId")
        //set new character images
        if(changeCharacterId == 3){
            imageView.setImageResource(R.drawable.bartsimpson)
            imageView2.setImageResource(R.drawable.bartsimpson)
            imageView3.setImageResource(R.drawable.bartsimpson)
            imageView4.setImageResource(R.drawable.bartsimpson)
            imageView5.setImageResource(R.drawable.bartsimpson)
            imageView6.setImageResource(R.drawable.bartsimpson)
            imageView7.setImageResource(R.drawable.bartsimpson)
            imageView8.setImageResource(R.drawable.bartsimpson)
        }

        if(changeCharacterId == 5){
            imageView.setImageResource(R.drawable.minnion)
            imageView2.setImageResource(R.drawable.minnion)
            imageView3.setImageResource(R.drawable.minnion)
            imageView4.setImageResource(R.drawable.minnion)
            imageView5.setImageResource(R.drawable.minnion)
            imageView6.setImageResource(R.drawable.minnion)
            imageView7.setImageResource(R.drawable.minnion)
            imageView8.setImageResource(R.drawable.minnion)
        }

        if(changeCharacterId == 4){
            imageView.setImageResource(R.drawable.kenny)
            imageView2.setImageResource(R.drawable.kenny)
            imageView3.setImageResource(R.drawable.kenny)
            imageView4.setImageResource(R.drawable.kenny)
            imageView5.setImageResource(R.drawable.kenny)
            imageView6.setImageResource(R.drawable.kenny)
            imageView7.setImageResource(R.drawable.kenny)
            imageView8.setImageResource(R.drawable.kenny)
        }





        auth = Firebase.auth
        val userId = auth.uid


        //Get values from Firebase
        db.collection("gamevalues").document(userId!!).addSnapshotListener{snapshot, error->
            if(error!=null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if(snapshot!=null && snapshot.exists()){

                    easyScore = snapshot.get("easyScore").toString().toInt()
                    mediumScore = snapshot.get("mediumScore").toString().toInt()
                    hardScore = snapshot.get("hardScore").toString().toInt()
                    coinsFromFB = snapshot.get("coins").toString().toDouble()

                }
                else{
                    println("Error")
                }

            }

        }



        auth = Firebase.auth
        val intent = intent
        diff = intent.getIntExtra("difficulty",0)
        //println("Diff: ${diff}")
        simpsonArray.add(imageView)
        simpsonArray.add(imageView2)
        simpsonArray.add(imageView3)
        simpsonArray.add(imageView4)
        simpsonArray.add(imageView5)
        simpsonArray.add(imageView6)
        simpsonArray.add(imageView7)
        simpsonArray.add(imageView8)
        simpsonArray.add(imageView9)

        bmbArray.add(imageView11)
        bmbArray.add(imageView12)
        bmbArray.add(imageView13)
        bmbArray.add(imageView14)
        bmbArray.add(imageView15)
        bmbArray.add(imageView16)
        bmbArray.add(imageView17)
        bmbArray.add(imageView18)
        bmbArray.add(imageView19)

        hideImages()


    }

    fun changeCharacter(view:View){

        val intent = Intent(this,ChangeCharacterActivity::class.java)
        startActivity(intent)

    }





    fun incScore(view: View){
        val mp = MediaPlayer()
        score++
        scoreText.text = "Score: $score"
        mp.setDataSource(this@MainActivity, Uri.parse("android.resource://"+this.packageName+"/"+ R.raw.hitsound))
        mp.prepare()
        mp.start()


    }

    fun decScore(view:View){
        //add bmb sound
        val mp2 = MediaPlayer()
        mp2.setDataSource(this@MainActivity, Uri.parse("android.resource://"+this.packageName+"/"+ R.raw.bmbeffect))
        mp2.prepare()
        mp2.start()
        score--
        scoreText.text = "Score: $score"
    }


    fun startGame(view: View){
        timer()
        startButton.visibility = View.INVISIBLE
        changeCharacterButton.visibility = View.INVISIBLE
        hideImages()
        showRandomSimpson()


    }

    fun hideImages(){

        for (simpson in simpsonArray){
            simpson.visibility = View.INVISIBLE
        }
        for(bmb in bmbArray){
            bmb.visibility = View.INVISIBLE
        }

    }



    fun playDif(diff: Int): Long{
        if (diff == 0){
            delayMs = 1000
            coinMultiply = 0.5
            difString = "Easy"
            highScore.text = "High Score: $easyScore"


        }
        if(diff == 1){
            delayMs = 600
            coinMultiply = 1.5
            difString = "Medium"
            highScore.text = "High Score: $mediumScore"
        }
        if(diff == 2){
            delayMs = 300
            coinMultiply = 2.0
            difString = "Hard"
            highScore.text = "High Score: $hardScore"
        }
        return delayMs.toLong()
    }



    fun showRandomSimpson(){

        runnable = object : Runnable{
            override fun run() {
                hideImages()
                val random = Random
                var randomIndex = random.nextInt(9)
                var randomIndex2 = random.nextInt(9)
                simpsonArray[randomIndex].visibility = View.VISIBLE
                bmbArray[randomIndex2].visibility = View.VISIBLE
                handler.postDelayed(runnable,playDif(diff))
            }
        }
        handler.post(runnable)
    }



    /*


    fun showRandomBmb(){
        runnable = object : Runnable{
            override fun run() {

            }

        }
    }

     */




    fun timer(){
        object: CountDownTimer(20000,1000){
            override fun onTick(p0: Long) {
                timeText.text = "Time Left: ${p0/1000}"
            }

            override fun onFinish() {
                timeText.text = "Time Left: 0"

                val username = auth.currentUser!!.displayName.toString()
                val date = com.google.firebase.Timestamp.now()
                val score = score
                val userID = auth.currentUser!!.uid
                //val coins = coins from firebase
                val earnedCoins = score*coinMultiply
                lastCoins = coinsFromFB+earnedCoins
                highScore.text = "High Score"



                if(diff == 0 && score>easyScore){
                    //new easy high score
                    easyScore = score
                }
                if(diff == 1 && score>mediumScore){
                    mediumScore = score
                }
                if(diff == 2 && score>hardScore){
                    hardScore = score
                }

                db.collection("gamevalues").document(userID)
                    .update(mapOf(
                        "coins" to lastCoins,
                        "easyScore" to easyScore,
                        "mediumScore" to mediumScore,
                        "hardScore" to hardScore,
                        "date" to date

                    ))




                val statsAlert = AlertDialog.Builder(this@MainActivity)
                statsAlert.setCancelable(false)
                statsAlert.setTitle("Your Stats")
                statsAlert.setMessage("Score: $score\nEarned Coins: $earnedCoins\nTotal Coins: $lastCoins")
                statsAlert.setPositiveButton("Play Again"){dialog, which->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                statsAlert.setPositiveButton("Main Menu") { dialog, which ->
                    Toast.makeText(this@MainActivity,"Game Over!",Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish()


                }

                statsAlert.setNegativeButton("Play Again"){dialog, which ->
                    //Restart
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                statsAlert.show()


                handler.removeCallbacks(runnable)
                hideImages()

            }

        }.start()

    }


}