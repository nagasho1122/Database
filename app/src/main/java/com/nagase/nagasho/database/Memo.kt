package com.nagase.nagasho.database

import io.realm.RealmObject

open class Memo (   //openはrealmを使う際に必要
    open var title:String = "",
    open var content: String = ""
) : RealmObject()  //realmで保存できる型にする