package com.cookandroid.dating_app.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Paint
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.cookandroid.dating_app.MainActivity
import com.cookandroid.dating_app.R
import com.cookandroid.dating_app.utils.FirebaseRef
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var nickname =""
    private var gender = ""
    private var city = ""
    private var age = ""
    private var uid= ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth
        val joinBtn = findViewById<Button>(R.id.joinBtn)

        joinBtn.setOnClickListener {

            val email = findViewById<TextInputEditText>(R.id.emailArea)
            val pwd = findViewById<TextInputEditText>(R.id.pwdArea)

            gender=findViewById<TextInputEditText>(R.id.genderArea).text.toString()
            city=findViewById<TextInputEditText>(R.id.cityArea).text.toString()
            age=findViewById<TextInputEditText>(R.id.ageArea).text.toString()
            nickname=findViewById<TextInputEditText>(R.id.nicknameArea).text.toString()



            auth.createUserWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val user = auth.currentUser
                        uid = user?.uid.toString()

                        val userModel = UserDataModel(
                            uid,
                            nickname,
                            age,
                            gender,
                            city
                        )

                        FirebaseRef.userInfoRef.child(uid).setValue(userModel)


//                        val intent = Intent(this,MainActivity::class.java)
//                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }



    }
}