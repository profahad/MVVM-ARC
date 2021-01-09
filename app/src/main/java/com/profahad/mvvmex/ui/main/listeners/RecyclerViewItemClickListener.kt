package com.profahad.mvvmex.ui.main.listeners

import com.profahad.mvvmex.data.model.User

interface RecyclerViewItemClickListener {
    fun onItemClicked(user: User)
}