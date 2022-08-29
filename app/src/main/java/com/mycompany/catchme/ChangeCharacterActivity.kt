package com.mycompany.catchme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.adapter.InventoryAdapter
import com.mycompany.catchme.adapter.SelectCharacterAdapter
import com.mycompany.catchme.databinding.ActivityChangeCharacterBinding
import com.mycompany.catchme.databinding.ActivityPlayerInventoryBinding
import com.mycompany.catchme.model.StoreObjects

class ChangeCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeCharacterBinding
    private lateinit var selectCharacterAdapter: SelectCharacterAdapter
    private lateinit var auth : FirebaseAuth
    var boughtProductArray = ArrayList<Long>()
    private val db = Firebase.firestore
    private lateinit var productList: ArrayList<StoreObjects>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeCharacterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
        val product1 = StoreObjects(R.drawable.simpson,200,"Default Simpson","Character",1)
        val product2 = StoreObjects(R.drawable.mainbg,500,"Default Background","Background",2)
        val product3 = StoreObjects(R.drawable.bartsimpson,1800,"Bart Simpson","Character",3)
        val product4 = StoreObjects(R.drawable.kenny,2500,"Kenny","Character",4)
        val product5 = StoreObjects(R.drawable.minnion,5200,"Minnion","Character",5)
        val product6 = StoreObjects(R.drawable.difbg,850,"Simpson Background","Background",5)

        productList = ArrayList<StoreObjects>()
        auth = Firebase.auth
        val userId = auth.uid


        val docRef = db.collection("gamevalues").document(userId!!) //get data once from firestore.
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    boughtProductArray = document.data!!.get("boughtItems") as ArrayList<Long>
                    if(product1.id.toLong() in boughtProductArray){
                        productList.add(product1)
                    }
                    if(product2.id.toLong() in boughtProductArray){
                        productList.add(product2)
                    }
                    if(product3.id.toLong() in boughtProductArray){
                        productList.add(product3)
                    }
                    if(product4.id.toLong() in boughtProductArray){
                        productList.add(product4)
                    }
                    if(product5.id.toLong() in boughtProductArray){
                        productList.add(product5)
                    }
                    if(product6.id.toLong() in boughtProductArray){
                        productList.add(product6)
                    }
                    println(productList.size)

                    selectCharacterAdapter = SelectCharacterAdapter(productList)
                    binding.selectCharacterRecyclerView.adapter = selectCharacterAdapter
                    binding.selectCharacterRecyclerView.layoutManager = LinearLayoutManager(this)




                } else {
                    println("No such data")
                }
            }
            .addOnFailureListener { exception ->
                println("failed")
            }












    }







}