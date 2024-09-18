package com.mamyrosa.playrealm.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mamyrosa.playrealm.R
import com.mamyrosa.playrealm.datamanager.service.ProductManagerSA
import com.mamyrosa.playrealm.models.ProductDto
import com.mamyrosa.playrealm.utilities.getParentActivity
import com.mamyrosa.playrealm.views.MainActivity

class ProductAdapter(
    private val products: List<ProductDto>
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.product_name)
        val quantityView: TextView = itemView.findViewById(R.id.quantity)
        val deleteView: ImageView = itemView.findViewById(R.id.delete_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val deviceView = inflater.inflate(R.layout.item_product, parent, false)
        return ViewHolder(deviceView)
    }


    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val product = products[position]
        val nameView = holder.nameView
        val qtyView = holder.quantityView
        val deleteView = holder.deleteView
        nameView.text = product.name
        qtyView.text = product.quantity.toString()
        deleteView.setOnClickListener {
            it.getParentActivity<MainActivity>()?.apply {
                showConfirmDialog("Supression", "Voulez vous supprimer cette produit") { ok ->
                    if (ok) {
                        ProductManagerSA.delete(product) { deleted ->
                            if (deleted) {
                                recreate()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}