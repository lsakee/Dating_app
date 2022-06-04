package com.cookandroid.dating_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.cookandroid.dating_app.auth.IntroActivity
import com.cookandroid.dating_app.auth.UserDataModel
import com.cookandroid.dating_app.setting.SettingActivity
import com.cookandroid.dating_app.slider.CardStackAdapter
import com.cookandroid.dating_app.utils.FirebaseRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter : CardStackAdapter
    lateinit var manager: CardStackLayoutManager
    private var TAG = "MainActivity"
    private val usersDataList = mutableListOf<UserDataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val setting =findViewById<ImageView>(R.id.settingIcon)
        setting.setOnClickListener {
//            val auth = Firebase.auth
//            auth.signOut()
//
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }

        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object : CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })


        cardStackAdapter=CardStackAdapter(baseContext,usersDataList)
        cardStackView.layoutManager=manager
        cardStackView.adapter = cardStackAdapter

        getUserDataList()
    }

    private fun getUserDataList(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

          for (dataModel in dataSnapshot.children) {

              val user = dataModel.getValue(UserDataModel::class.java)
              usersDataList.add(user!!)

          }

                cardStackAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
         FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }

}