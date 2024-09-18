package com.mamyrosa.playrealm.datamanager.repository

import com.mamyrosa.playrealm.models.ProductDo
import io.realm.Realm

class ProductRepository : BaseRepository<ProductDo>(ofType = ProductDo::class.java)
