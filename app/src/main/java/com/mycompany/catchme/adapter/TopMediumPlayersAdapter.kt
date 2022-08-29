package com.mycompany.catchme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.databinding.TopMediumPlayersRecyclerRowBinding

class TopMediumPlayersAdapter(val scoreList : List<Pair<String,Int>>) : RecyclerView.Adapter<TopMediumPlayersAdapter.TopMediumPlayerHolder>() {

    private lateinit var auth : FirebaseAuth
    val db = Firebase.firestore



    class TopMediumPlayerHolder(val binding : TopMediumPlayersRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMediumPlayerHolder {
        val binding = TopMediumPlayersRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopMediumPlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: TopMediumPlayerHolder, position: Int) {

        holder.binding.playerNameText.text = "${scoreList.get(position).first} : ${scoreList.get(position).second}"

    }

    override fun getItemCount(): Int {
        return scoreList.size
    }



}