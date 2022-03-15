package com.logger.practicletask.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table",
    indices = [Index(value = ["email", "phone_number"], unique = true)])
data class UserDataModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "full_name")
    var fullName: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "phone_number")
    var phoneNumber: Long? = null,

    @ColumnInfo(name = "city")
    var city: String? = null,

    @ColumnInfo(name = "gender")
    var gender: Int? = null,

    @ColumnInfo(name = "password")
    var password: String? = null,
)