package com.profahad.mvvmex.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.profahad.mvvmex.R
import com.profahad.mvvmex.data.model.User
import com.profahad.mvvmex.ui.main.listeners.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.layout_user_item.view.*
import kotlin.random.Random


class MainAdapter(
    private val listener: RecyclerViewItemClickListener,
    private val users: ArrayList<User>
) :
    RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            listener: RecyclerViewItemClickListener,
            user: User
        ) {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) // because file name is always same

            val rndNo = Integer.valueOf(user.userId) + 200

            itemView.apply {
                textViewUserName.text = user.userName
                textViewUserEmail.text = user.userEmail
                Glide.with(imageViewAvatar.context)
                    .load("https://picsum.photos/200/${rndNo}")
                    .apply(requestOptions)
                    .into(imageViewAvatar)

            }
            itemView.setOnClickListener {
                listener.onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item, parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(listener, users[position])
    }

    fun addUsers(users: List<User>) {
        this.users.apply {
            clear()
            addAll(users)
        }

    }
}