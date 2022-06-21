package com.cookandroid.dating_app.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cookandroid.dating_app.R
import com.cookandroid.dating_app.auth.UserDataModel
import com.cookandroid.dating_app.utils.FirebaseAuthUtils
import com.cookandroid.dating_app.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

//내가 좋아요한 사람들이 나를 좋아요한 리스트
class MyLikeListActivity : AppCompatActivity() {

    private val TAG = "MyLikeListActivity"
    private val uid = FirebaseAuthUtils.getUid()
    private val likeUserListuid = mutableListOf<String>()
    private val likeUserList = mutableListOf<UserDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)
        //전체 유저의 대이터 받아오기
        getUserDataList()
        // 내가 좋아요한 사람들과
        getMyLikeList()
        //나를 좋아요한 사람의 리스트를 받아오기
    }

    private fun getMyLikeList(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//리스트안에서 나의 Uid가 있는 지확인하기
                for (dataModel in dataSnapshot.children) {
               //내가 좋아요한 사람들의 uid 가 라이크 에 들어있음
                    likeUserListuid.add(dataModel.key.toString())
                }
                getUserDataList()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)

    }

    private fun getUserDataList(){
//
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ////
                for (dataModel in dataSnapshot.children) {

                    val user = dataModel.getValue(UserDataModel::class.java)
                    if(likeUserListuid.contains(user?.uid)){
                        likeUserList.add(user!!)
                    }
                }
                Log.d(TAG,likeUserList.toString())
            }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)
    }

}


