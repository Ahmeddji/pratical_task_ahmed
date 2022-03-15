package com.logger.practicletask.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.logger.practicletask.db.entities.UserDataModel

@Dao
interface UserDao {
    @Insert
    suspend fun insert(userDataModel: UserDataModel)

    @Update
    suspend fun update(userDataModel: UserDataModel)

    @Query("SELECT * from user_table WHERE id = :userId LIMIT 1")
    suspend fun get(userId: Long?): UserDataModel?

    @Query("SELECT * from user_table WHERE email = :emailId LIMIT 1")
    suspend fun getByEmail(emailId: String?): UserDataModel?

    @Query("SELECT * from user_table WHERE phone_number = :phone LIMIT 1")
    suspend fun getByPhoneNumber(phone: String?): UserDataModel?
}