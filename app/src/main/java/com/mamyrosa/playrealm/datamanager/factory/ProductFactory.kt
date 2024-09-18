package com.mamyrosa.playrealm.datamanager.factory

import com.mamyrosa.playrealm.models.ProductDo
import com.mamyrosa.playrealm.models.ProductDto

object ProductFactory : BaseFactory<ProductDo, ProductDto> {
    override fun toDomainObject(value: ProductDto) = ProductDo().apply {
        this.id = value.id
        this.name = value.name
        this.quantity = value.quantity
    }

    override fun toDataTransferObject(value: ProductDo) = ProductDto(
        id = value.id,
        name = value.name,
        quantity = value.quantity
    )

}