package com.mycompany.catchme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.catchme.databinding.NewStoreRecyclerRowBinding
import com.mycompany.catchme.model.NewStoreObjectModel
import com.squareup.picasso.Picasso

class NewStoreAdapter(private val productList: ArrayList<NewStoreObjectModel>) : RecyclerView.Adapter<NewStoreAdapter.NewStoreHolder>(){


    class NewStoreHolder(val binding: NewStoreRecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewStoreHolder {
        val binding = NewStoreRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewStoreHolder(binding)
    }

    override fun onBindViewHolder(holder: NewStoreHolder, position: Int) {

        Picasso.get().load(productList.get(position).imageURL).into(holder.binding.imageView10)
        holder.binding.productText.text = "${productList.get(position).productName}(${productList.get(position).productType})"
        holder.binding.priceText.text = "Price : ${productList.get(position).price}"


    }

    override fun getItemCount(): Int {
        return productList.size
    }


}