package com.cookandroid.dating_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.cookandroid.dating_app.auth.IntroActivity
import com.cookandroid.dating_app.utils.FirebaseAuthUtils
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       // val uid=auth.currentUser?.uid.toString()
        val uid = FirebaseAuthUtils.getUid()
        if(uid=="null"){
            Handler().postDelayed({
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            },3000)
        } else{
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            },3000)

        }

        Log.d(TAG,auth.currentUser?.uid.toString())



    }
}