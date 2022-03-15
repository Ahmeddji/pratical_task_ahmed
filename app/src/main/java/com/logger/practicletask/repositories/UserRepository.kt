package com.logger.practicletask.repositories

import com.logger.practicletask.db.dao.UserDao
import com.logger.practicletask.db.entities.UserDataModel
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun addUser(userDataModel: UserDataModel) = userDao.insert(userDataModel)

    suspend fun getUser(userId: Long?) = userDao.get(userId)

    suspend fun getUserByEmail(email: String?) = userDao.getByEmail(email)

    suspend fun getUserByPhoneNumber(phone: String?) = userDao.getByPhoneNumber(phone)

    suspend fun updateUser(userDataModel: UserDataModel) = userDao.update(userDataModel)
}