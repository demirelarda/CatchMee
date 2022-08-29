package com.mycompany.catchme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.databinding.TopEasyPlayersRecyclerRowBinding
import com.mycompany.catchme.model.Player


class TopEasyPlayersAdapter(val scoreList : List<Pair<String,Int>>) : RecyclerView.Adapter<TopEasyPlayersAdapter.TopEasyPlayerHolder>() {

    private lateinit var auth : FirebaseAuth
    val db = Firebase.firestore

    class TopEasyPlayerHolder(val binding : TopEasyPlayersRecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopEasyPlayerHolder {
        val binding = TopEasyPlayersRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopEasyPlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: TopEasyPlayerHolder, position: Int) {


        holder.binding.playerNameText.text = "${scoreList.get(position).first} : ${scoreList.get(position).second}"
    }

    override fun getItemCount(): Int {
        return scoreList.size
    }

}