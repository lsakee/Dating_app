package com.cookandroid.dating_app.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cookandroid.dating_app.R
import com.cookandroid.dating_app.auth.IntroActivity
import com.cookandroid.dating_app.message.MyLikeListActivity
import com.cookandroid.dating_app.message.MymsgActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting2)



        //첫번재 방법으로는 앱에서 notification띄우기
        //두번째 파이어베이스에서 모든앱에게 푸시보내기
        //특정 사용자에게 메시지 보내기(Firebase console에서)
        //앱에서 직접 다른사람에게 메시지 보내기
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
        val myMsg =findViewById<Button>(R.id.myMsg)
        myMsg.setOnClickListener {
            val intent = Intent(this, MymsgActivity::class.java)
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