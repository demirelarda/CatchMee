package com.mycompany.catchme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mycompany.catchme.adapter.NewStoreAdapter
import com.mycompany.catchme.databinding.ActivityNewStoreBinding
import com.mycompany.catchme.model.NewStoreObjectModel
import kotlinx.android.synthetic.main.activity_store.*

class NewStoreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewStoreBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var productList : ArrayList<NewStoreObjectModel>
    private lateinit var newStoreAdapter : NewStoreAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore
        productList = ArrayList<NewStoreObjectModel>()

        getData()

        binding.newStoreRecyclerView.layoutManager = LinearLayoutManager(this)
        newStoreAdapter = NewStoreAdapter(productList)
        binding.newStoreRecyclerView.adapter = newStoreAdapter

    }


    fun getData(){

        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                productList.clear()
                for (document in result) {
                    val productId = document.get("productId") as String
                    val productType = document.get("productType") as String
                    val productPrice = document.get("price") as Double
                    val imageDownloadUrl = document.get("imageDownloadUrl") as String
                    val productName = document.get("productName") as String
                    val product = NewStoreObjectModel(productId,productPrice,imageDownloadUrl,productType,productName)
                    productList.add(product)
                }
                newStoreAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                println("Error!")

            }

    }



}