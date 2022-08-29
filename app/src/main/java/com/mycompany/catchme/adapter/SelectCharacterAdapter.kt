package com.mycompany.catchme.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.catchme.databinding.SelectCharacterRowBinding
import com.mycompany.catchme.model.StoreObjects
import com.mycompany.catchme.view.MainActivity

class SelectCharacterAdapter(val productList: ArrayList<StoreObjects>): RecyclerView.Adapter<SelectCharacterAdapter.SelectCharacterHolder>() {

    class SelectCharacterHolder(val binding : SelectCharacterRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCharacterHolder {
        val binding = SelectCharacterRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SelectCharacterHolder(binding)

    }


    override fun onBindViewHolder(holder: SelectCharacterHolder, position: Int) {
        holder.binding.inventoryImageView.setImageResource(productList.get(position).image)
        holder.binding.productTextInventory.text = productList.get(position).name
        holder.binding.equipButton.setOnClickListener{
            val selectedId = productList.get(position).id
            val context = holder.itemView.context
            val intent = Intent(context,MainActivity::class.java)
            intent.putExtra("selectedId",selectedId)
            context.startActivity(intent)



        }


    }

    override fun getItemCount(): Int {
        return productList.size
    }
}