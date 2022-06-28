package com.cookandroid.dating_app.message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cookandroid.dating_app.R
import com.cookandroid.dating_app.auth.UserDataModel

class ListViewAdapte(val context: Context, val items : MutableList<UserDataModel>) :BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View {
        var convertView = convertView
        if(convertView==null){
            convertView=LayoutInflater.from(p2?.context).inflate(R.layout.list_view_item,p2,false)
        }
        val nickname = convertView!!.findViewById<TextView>(R.id.listViewItemNickname)
        nickname.text = items[p0].nickname
        return convertView!!
    }


}