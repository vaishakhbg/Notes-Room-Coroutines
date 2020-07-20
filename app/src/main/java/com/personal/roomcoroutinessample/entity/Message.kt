package com.personal.roomcoroutinessample.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time


@Entity
data class Message (

    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    var time: String? = null,
    var date: String? = null,
    var messageString :String? = null,
    var location : String? = null)

