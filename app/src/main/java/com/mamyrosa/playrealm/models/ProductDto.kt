package com.mamyrosa.playrealm.models

import com.mamyrosa.playrealm.utilities.randomUUID

data class ProductDto(
    var id : String = randomUUID,
    var name: String,
    var quantity: Int
)
