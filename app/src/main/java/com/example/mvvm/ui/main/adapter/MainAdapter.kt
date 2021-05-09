package com.example.mvvm.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm.R
import com.example.mvvm.data.model.User

class MainAdapter(private val users: ArrayList<User>): RecyclerView.Adapter<MainAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_layout, parent, false
        )
        return DataViewHolder(itemView)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            val name: TextView = itemView.findViewById(R.id.textViewUserName)
            val email: TextView = itemView.findViewById(R.id.textViewUserEmail)
            val avatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
            name.text = user.name
            email.text = user.email
            Glide.with(avatar.context)
                .load(user.avatar)
                .into(avatar)
        }
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }
}

