package com.mycompany.catchme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.catchme.databinding.TopHardPlayersRecyclerRowBinding

class TopHardPlayersAdapter(val scoreList : List<Pair<String,Int>>) : RecyclerView.Adapter<TopHardPlayersAdapter.TopHardPlayerHolder>() {


    class TopHardPlayerHolder(val binding : TopHardPlayersRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHardPlayerHolder {
        val binding = TopHardPlayersRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TopHardPlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: TopHardPlayerHolder, position: Int) {

        holder.binding.playerNameText.text = "${scoreList.get(position).first} : ${scoreList.get(position).second}"

    }

    override fun getItemCount(): Int {
        return scoreList.size
    }



}