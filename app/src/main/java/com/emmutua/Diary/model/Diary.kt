package com.emmutua.Diary.model

import androidx.room.PrimaryKey
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class Diary : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.create()
    var title: String = ""
    var mood : String = Mood.Neutral.name
    var description: String = ""
    var images: RealmList<String> = realmListOf()
    var date: RealmInstant = RealmInstant.from(System.currentTimeMillis(), 0)
}