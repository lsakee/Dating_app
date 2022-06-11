package com.cookandroid.dating_app.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cookandroid.dating_app.R
import com.cookandroid.dating_app.auth.IntroActivity
import com.cookandroid.dating_app.message.MyLikeListActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting2)

        val mybtn= findViewById<Button>(R.id.myPagebtn)
        mybtn.setOnClickListener {
            val intent = Intent(this,MyPageActivity::class.java)
            startActivity(intent)
        }

        val myLikeBtn =findViewById<Button>(R.id.myLikeList)
        myLikeBtn.setOnClickListener {

            val intent = Intent(this, MyLikeListActivity::class.java)
            startActivity(intent)
        }

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()

            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }
    }
}