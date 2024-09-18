package com.mamyrosa.playrealm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ProductDo: RealmObject() {
    @PrimaryKey
    var id : String = ""
    var name : String = ""
    var quantity : Int = 0
}