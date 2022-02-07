package com.joonmyoung.shorturl.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History (

    @PrimaryKey val id : Int?,
    @ColumnInfo(name = "keyword") val Keyword : String?,
    @ColumnInfo(name = "orgUrl") val orgUrl : String?,
    @ColumnInfo(name = "bookMark") var bookMark : String?


        )

