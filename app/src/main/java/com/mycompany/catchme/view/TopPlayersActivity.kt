package com.mycompany.catchme.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.adapter.TopEasyPlayersAdapter
import com.mycompany.catchme.adapter.TopHardPlayersAdapter
import com.mycompany.catchme.adapter.TopMediumPlayersAdapter
import com.mycompany.catchme.databinding.ActivityTopPlayersBinding
import com.mycompany.catchme.model.Player

class TopPlayersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopPlayersBinding
    private lateinit var topEasyPlayersAdapter: TopEasyPlayersAdapter
    private lateinit var topMediumPlayersAdapter: TopMediumPlayersAdapter
    private lateinit var topHardPlayersAdapter: TopHardPlayersAdapter
    private lateinit var auth : FirebaseAuth

    var easyScore : Int = 0
    var mediumScore : Int = 0
    var hardScore : Int = 0
    var userIdfromFB : String = ""
    private var username : String = ""
    val db = Firebase.firestore
    var easyArrayList = ArrayList<Pair<String,Int>>()
    var mediumArrayList = ArrayList<Pair<String,Int>>()
    var hardArrayList = ArrayList<Pair<String,Int>>()
    var playerList = ArrayList<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopPlayersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        val userId = auth.uid




        binding.easyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mediumRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.hardRecyclerView.layoutManager = LinearLayoutManager(this)





        db.collection("gamevalues")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    userIdfromFB = document.id
                    username = document.get("username").toString()
                    easyScore = document.get("easyScore").toString().toInt()
                    mediumScore = document.get("mediumScore").toString().toInt()
                    hardScore = document.get("hardScore").toString().toInt()
                    easyArrayList.add(Pair(username,easyScore))
                    mediumArrayList.add(Pair(username,mediumScore))
                    hardArrayList.add(Pair(username,hardScore))


                    var pairListEasy = easyArrayList.sortedWith(compareBy({ it.second })).asReversed()
                    var pairListMedium = mediumArrayList.sortedWith(compareBy({it.second})).asReversed()
                    var pairListHard = hardArrayList.sortedWith(compareBy({it.second})).asReversed()




                    topEasyPlayersAdapter = TopEasyPlayersAdapter(pairListEasy)
                    topMediumPlayersAdapter = TopMediumPlayersAdapter(pairListMedium)
                    topHardPlayersAdapter = TopHardPlayersAdapter(pairListHard)

                    binding.easyRecyclerView.adapter = topEasyPlayersAdapter
                    binding.mediumRecyclerView.adapter= topMediumPlayersAdapter
                    binding.hardRecyclerView.adapter = topHardPlayersAdapter


                }
            }
            .addOnFailureListener { exception ->
                println("error!")
            }











    }
}