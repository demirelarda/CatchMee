package com.mycompany.catchme.adapter


import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.mycompany.catchme.databinding.InventoryRecyclerRowBinding
import com.mycompany.catchme.model.StoreObjects



class InventoryAdapter(val productList: ArrayList<StoreObjects>): RecyclerView.Adapter<InventoryAdapter.InventoryHolder>() {

    class InventoryHolder(val binding : InventoryRecyclerRowBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryHolder {
        val binding = InventoryRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InventoryHolder(binding)

    }


    override fun onBindViewHolder(holder: InventoryHolder, position: Int) {
        holder.binding.inventoryImageView.setImageResource(productList.get(position).image)
        holder.binding.productTextInventory.text = "Name: ${productList.get(position).name}"
        holder.binding.equipButton.setOnClickListener{
            holder.binding.equipButton.setOnClickListener{

                if(productList.get(position).type == "Background"){
                    //change main menu background photo.
                    println("Background")

                }
                if(productList.get(position).type == "Character"){
                    //change game character.
                    println("Character")


                }
            }
        }


        /*
        holder.binding.equipButton.setOnClickListener{

            if(productList.get(position).type == "Background"){
                //change main menu background photo.
                println("Background")
            }
            if(productList.get(position).type == "Character"){
                //change game character.
                println("Character")


            }


        }
        */

        holder.binding.productTypeInventoryText.text = "Type: ${productList.get(position).type}"
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}