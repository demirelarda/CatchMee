package com.mycompany.catchme.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.mycompany.catchme.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
    }


    fun registerUser(view: View){

        val email = registerEmailText.text.toString()
        val username = registerUnameText.text.toString()
        val password = registerPasswordText.text.toString()
        var notificationTokenFromFM : String = ""
        //get notification token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            notificationTokenFromFM = token.toString()

        })

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task->
            if(task.isSuccessful){

                val currentuser = Firebase.auth.currentUser
                val profileUpdates = userProfileChangeRequest {
                    displayName = username
                }

                currentuser!!.updateProfile(profileUpdates).addOnCompleteListener{
                    if(it.isSuccessful){
                        println("username = ${username}")
                        val userId = auth.currentUser!!.uid
                        val username = auth.currentUser!!.displayName.toString()
                        val boughtProductArray = ArrayList<Long>()
                        val coins : Double = 0.0
                        val date = com.google.firebase.Timestamp.now()
                        val gamevalues = db.collection("gamevalues")
                        val values = hashMapOf(
                            "username" to username,
                            "boughtItems" to boughtProductArray,
                            "coins" to coins,
                            "easyScore" to 0,
                            "mediumScore" to 0,
                            "hardScore" to 0,
                            "userId" to userId,
                            "date" to date,
                            "notiToken" to notificationTokenFromFM

                        )

                        gamevalues.document(userId).set(values)
                    }
                }

                Toast.makeText(applicationContext,"Registration Succesful!",Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }

        }.addOnFailureListener{exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()

        }



    }


}