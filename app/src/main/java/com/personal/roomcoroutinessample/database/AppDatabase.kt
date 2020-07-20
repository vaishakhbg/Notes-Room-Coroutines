package com.personal.roomcoroutinessample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.personal.roomcoroutinessample.dao.MessageDao
import com.personal.roomcoroutinessample.entity.Message


@Database(entities = [Message::class] , version = 1)
abstract  class AppDatabase : RoomDatabase() {

    abstract fun messageDao():MessageDao

    companion object{
        var INSTANCE : AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }

}