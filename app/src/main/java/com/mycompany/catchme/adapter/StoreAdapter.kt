package com.mycompany.catchme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.model.StoreObjects
import com.mycompany.catchme.databinding.RecyclerRowBinding


class StoreAdapter(val productList: ArrayList<StoreObjects>, var coinText: TextView): RecyclerView.Adapter<StoreAdapter.StoreHolder>(){

    private lateinit var auth : FirebaseAuth
    val db = Firebase.firestore
    var boughtProductArray = ArrayList<Long>()




    class StoreHolder(val binding : RecyclerRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoreHolder(binding)

    }

    override fun onBindViewHolder(holder: StoreHolder, position: Int) {
        auth = Firebase.auth
        val userId = auth.uid
        var coinsFromFB = 0.0
        var coins : Double = 0.0
        val document = db.collection("gamevalues").document(userId!!)
        document.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    coinsFromFB = document.get("coins").toString().toDouble()
                    println("Coins from FB Adapter= $coinsFromFB")
                    coins = coinsFromFB
                } else {
                    println("No such document!")

                }
            }
            .addOnFailureListener { exception ->
                println("Failed.")
            }



        val docRef = db.collection("gamevalues").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    boughtProductArray = document.get("boughtItems") as ArrayList<Long>

                } else {
                    println("No such doc")


                }
            }
            .addOnFailureListener { exception ->
                println("error.")
            }





        holder.binding.buyButton.setOnClickListener{
            println(productList.get(position).id.toLong() in boughtProductArray)
            if(productList.get(position).id.toLong() in boughtProductArray){
                Toast.makeText(holder.itemView.context,"This product has already bought",Toast.LENGTH_LONG).show()
                println("Already Bought!!!")
            }
            else{

                println("Button Clicked")
                val alert = AlertDialog.Builder(holder.itemView.context)
                alert.setTitle("Buy")
                alert.setPositiveButton("Buy"){dialog, which->


                    var productPrice : Double =  productList.get(position).price.toDouble()

                    if(coins>=productPrice){
                        coins-=productPrice

                        document.update("coins",coins)
                        coinText.text = "Loading..."
                        coinText.text = ("Coins: $"+coins)



                        val productId = productList.get(position).id.toLong()
                        boughtProductArray.add(productId)
                        println(boughtProductArray.isEmpty())
                        if(boughtProductArray.isEmpty() == false){
                            document.update("boughtItems",boughtProductArray).addOnSuccessListener {
                                holder.binding.buyButton.isClickable = false
                                holder.binding.buyButton.text = "Bought"
                            }
                        }











                    }
                    else{
                        println("Not enough coins!")
                        Toast.makeText(holder.itemView.context,"Not Enough Coins!",Toast.LENGTH_LONG).show()
                    }


                }
                alert.setNegativeButton("Cancel"){dialog, which ->

                }
                alert.setCancelable(true)
                alert.show()


            }




        }

        holder.binding.imageView10.setImageResource(productList.get(position).image)
        holder.binding.productText.text = productList.get(position).name
        holder.binding.priceText.text = "Price: ${productList.get(position).price}"



    }

    override fun getItemCount(): Int {
        return productList.size
    }


}