package com.logger.practicletask.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.logger.practicletask.R
import com.logger.practicletask.common.SharedPreferencesManager
import com.logger.practicletask.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = SharedPreferencesManager.getInstance(applicationContext).getUserId()
        if (userId != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_auth)
        }
    }
}