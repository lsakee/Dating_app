package com.cookandroid.dating_app.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
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
    lateinit var listviewAdapter : ListViewAdapte
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)

        val userListview = findViewById<ListView>(R.id.userListView)
        val listviewAdapter = ListViewAdapte(this,likeUserList)
        userListview.adapter=listviewAdapter

        // 내가 좋아요한 사람들과
        getMyLikeList()
        //나를 좋아요한 사람의 리스트를 받아오기
        // 전체 유정 중에서, 내가 좋아요한 사람들 가져와서
        // 이사람이 나와 매칭이 되어있는지 확인하는것

        userListview.setOnItemClickListener { parent, view, position, id ->
            checkMatching(likeUserList[position].uid.toString())
        }
    }
    private fun checkMatching(otherUid : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.children.count()==0){
                    Toast.makeText(this@MyLikeListActivity,"매칭이 실패되었습니다.",Toast.LENGTH_LONG).show()
                }else{
                    for (dataModel in dataSnapshot.children) {
                        val likeUserKey = dataModel.key.toString()
                        if(likeUserKey.equals(uid)){
                            Toast.makeText(this@MyLikeListActivity,"매칭이 되었습니다.",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this@MyLikeListActivity,"매칭이 실패되었습니다.",Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(otherUid).addValueEventListener(postListener)
//
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
                    //전체 유저중에 내가좋아요한 사람들의 정보만 add
                    if(likeUserListuid.contains(user?.uid)){
                        likeUserList.add(user!!)
                    }
                }
                listviewAdapter.notifyDataSetChanged()
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


