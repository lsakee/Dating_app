package com.cookandroid.dating_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cookandroid.dating_app.auth.IntroActivity
import com.cookandroid.dating_app.auth.UserDataModel
import com.cookandroid.dating_app.setting.SettingActivity
import com.cookandroid.dating_app.slider.CardStackAdapter
import com.cookandroid.dating_app.utils.FirebaseAuthUtils
import com.cookandroid.dating_app.utils.FirebaseRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter : CardStackAdapter
    lateinit var manager: CardStackLayoutManager
    private var TAG = "MainActivity"
    private val usersDataList = mutableListOf<UserDataModel>()
    private var userCount = 0
    private val uid = FirebaseAuthUtils.getUid()
    private lateinit var currentUserGender : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val setting =findViewById<ImageView>(R.id.settingIcon)
        setting.setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }

        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object : CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

                if(direction==Direction.Right){
                    Toast.makeText(this@MainActivity,"right",Toast.LENGTH_SHORT).show()
                    Log.d(TAG,usersDataList[userCount].uid.toString())

                    userLikeOtherUser(uid,usersDataList[userCount].uid.toString())
                }

                if(direction==Direction.Left){
                    Toast.makeText(this@MainActivity,"left",Toast.LENGTH_SHORT).show()
                }

                userCount=userCount+1
                if(userCount== usersDataList.count()){
                    getUserDataList(currentUserGender)
                    Toast.makeText(this@MainActivity,"유저 새롭게 받기",Toast.LENGTH_LONG).show()
                }
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
//
//        getUserDataList()
        getMyUserData()
    }

    private fun getMyUserData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.d(TAG,dataSnapshot.toString())
                val data = dataSnapshot.getValue(UserDataModel::class.java)

                Log.d(TAG,data?.gender.toString())
                currentUserGender = data?.gender.toString()
                getUserDataList(currentUserGender)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)
    }

    private fun getUserDataList(currentUserGender : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

          for (dataModel in dataSnapshot.children) {

              val user = dataModel.getValue(UserDataModel::class.java)

              if(user!!.gender.toString().equals(currentUserGender)){

              }else {
                  usersDataList.add(user!!)
              }
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
    //유저의 좋아료를 표시하는 부분
    //나의 uid,상대 uid
    private fun userLikeOtherUser(myUid : String,otherUid : String){
        FirebaseRef.userLikeRef.child(uid).child(otherUid).setValue("true")
    }

}