package com.mamyrosa.playrealm.datamanager.service

import com.mamyrosa.playrealm.datamanager.factory.ProductFactory
import com.mamyrosa.playrealm.datamanager.repository.ProductRepository
import com.mamyrosa.playrealm.models.ProductDo
import com.mamyrosa.playrealm.models.ProductDto

object ProductManagerSA : BaseManager<ProductDo, ProductDto, ProductRepository, ProductFactory>(
    repository = ProductRepository(),
    factory = ProductFactory,
    pkFieldName = "productId"
) {

    fun findAllProduct() = factory.toDataTransferObjects(repository.findAll())

    fun findQtyMax() = findAll().filter { it.quantity >= 8 }
}