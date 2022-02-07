package com.joonmyoung.shorturl.Database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [History::class],version = 1)

abstract class Appdatabase:RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        private var instance: Appdatabase? = null

        @Synchronized
        fun getInstance(context: Context): Appdatabase?{
        if (instance == null)
        {
            synchronized(Appdatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    Appdatabase::class.java,
                    "database"
                ).build()
            }
        }
        return instance
    }

    }

}