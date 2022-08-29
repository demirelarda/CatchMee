package com.mycompany.catchme.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.R
import com.mycompany.catchme.adapter.StoreAdapter
import com.mycompany.catchme.databinding.ActivityStoreBinding
import com.mycompany.catchme.model.StoreObjects
import kotlinx.android.synthetic.main.activity_store.*


class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var productList: ArrayList<StoreObjects>
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var auth : FirebaseAuth
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        val product1 = StoreObjects(R.drawable.simpson,200,"Default Simpson","Character",1)
        val product2 = StoreObjects(R.drawable.mainbg,500,"Default Background","Background",2)
        val product3 = StoreObjects(R.drawable.bartsimpson,1800,"Bart Simpson","Character",3)
        val product4 = StoreObjects(R.drawable.kenny,2500,"Kenny","Character",4)
        val product5 = StoreObjects(R.drawable.minnion,5200,"Minnion","Character",5)
        val product6 = StoreObjects(R.drawable.difbg,850,"Simpson Background","Background",5)

        productList = ArrayList<StoreObjects>()
        productList.add(product1)
        productList.add(product2)
        productList.add(product3)
        productList.add(product4)
        productList.add(product5)
        productList.add(product6)
        storeAdapter = StoreAdapter(productList,coinText)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = storeAdapter

        auth = Firebase.auth
        val userId = auth.uid
        coinText.text = "Loading..."
        var coinsFromFB = 0.0


        val document = db.collection("gamevalues").document(userId!!)
        document.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    coinsFromFB = document.get("coins").toString().toDouble()
                    coinText.text = "Coins:$coinsFromFB"


                } else {
                    println("No such document!")
                }
            }
            .addOnFailureListener { exception ->
                println("Failed.")

            }







    }


}


