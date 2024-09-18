package com.mamyrosa.playrealm.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamyrosa.playrealm.databinding.ActivityMainBinding
import com.mamyrosa.playrealm.datamanager.service.ProductManagerSA
import com.mamyrosa.playrealm.models.ProductDto
import com.mamyrosa.playrealm.utilities.Closure
import com.mamyrosa.playrealm.views.adapters.ProductAdapter
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val products = ProductManagerSA.findAll()
        binding.productRecycler.adapter = ProductAdapter(products)
        binding.productRecycler.layoutManager = LinearLayoutManager(this)

        binding.addButton.setOnClickListener {
            Timber.tag("click button").e("fired")
            val name = binding.productNameEntry.text?.trim()
            val qty = binding.qtyEntry.text?.trim()
            if (name?.isEmpty() == true) {
                binding.productNameEntry.error = "nom produit vide"
                return@setOnClickListener
            }

            if (qty?.isEmpty() == true) {
                binding.qtyEntry.error = "quantité produit vide"
                return@setOnClickListener
            }

            ProductManagerSA.insert(
                ProductDto(name = name.toString(), quantity = qty.toString().toInt())
            ) { isInsert ->
                if (isInsert) {
                    Toast.makeText(this, "Insertion success", Toast.LENGTH_SHORT).show()
                    populateView()
                    binding.productNameEntry.text = null
                    binding.qtyEntry.text = null

                }
            }

        }
    }

    private fun populateView() {
        val products = ProductManagerSA.findQtyMax()
        binding.productRecycler.adapter = ProductAdapter(products)
    }

    /**
     * show confirm dialog
     * @param title : title of the dialog
     * @param message : message to inform the user
     * @param completion : handle the user action
     */

    fun showConfirmDialog(title: String, message: String, completion: Closure<Boolean>) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OUI") { _, _ -> completion.invoke(true) }
            .setNegativeButton("NON") { _, _ -> completion.invoke(false) }
            .show()
    }
}