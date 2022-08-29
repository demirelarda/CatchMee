package com.mycompany.catchme.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.mycompany.catchme.FirebaseService
import com.mycompany.catchme.NewStoreActivity
import com.mycompany.catchme.R
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {




    private lateinit var auth: FirebaseAuth


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)

    }


    var dif : Int = 0
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {


        var notificationTokenFromFM : String = ""

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            notificationTokenFromFM = token.toString()

        })




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        auth = Firebase.auth
        sharedPreferences = this.getSharedPreferences("com.mycompany.catchkennyv3", MODE_PRIVATE)
        val difFromSp = sharedPreferences.getInt("dif",0)
        val intent = intent
        val difFromAct = intent.getIntExtra("difficulty",difFromSp) //if there is no intent the value will be coming from SP
        dif = difFromAct
        val currentUser = auth.currentUser
        val username = currentUser!!.displayName.toString()
        mainMenuUserText.text = "@$username"







    }


    fun playGame(view: View){

        val intent = Intent(applicationContext, MainActivity::class.java)
        println(dif)
        intent.putExtra("difficulty",dif)
        startActivity(intent)
        finish()
    }

    fun selectDif(view: View){

        val intent = Intent(applicationContext, SelectDifActivity::class.java)
        intent.putExtra("difficulty",dif)
        startActivity(intent)
        //finish()

    }

    fun store(view: View){
        val intent = Intent(applicationContext, NewStoreActivity::class.java)
        startActivity(intent)
        //finish()
    }

    fun topPlayersClicked(view: View){
        val intent = Intent(applicationContext, TopPlayersActivity::class.java)
        startActivity(intent)

    }

    fun inventoryClicked(view: View){
        val intent = Intent(applicationContext, PlayerInventoryActivity::class.java )
        startActivity(intent)
    }










}