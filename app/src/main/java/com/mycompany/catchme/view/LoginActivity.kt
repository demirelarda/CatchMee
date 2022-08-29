package com.mycompany.catchme.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        textView.paint.isUnderlineText = true

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }



    }


    fun registerClicked(view: View){
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun loginClicked(view: View){
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if(email != "" && password!=""){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
                if(task.isSuccessful){
                    val currentUser = auth.currentUser
                    val username = currentUser!!.displayName
                    Toast.makeText(this,"Welcome $username !",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        else{
            Toast.makeText(this,"Don't leave the entries blank!",Toast.LENGTH_LONG).show()
        }


    }

    fun forgotPassword(view:View){
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }


}