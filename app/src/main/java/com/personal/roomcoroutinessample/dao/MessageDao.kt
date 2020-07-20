package com.personal.roomcoroutinessample.dao

import androidx.room.*
import com.personal.roomcoroutinessample.entity.Message

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)

    @Delete
    fun deleteMessage(message: Message)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMessage(message: Message)

    @Query("SELECT * FROM Message")
    fun getMessages(): List<Message>

}