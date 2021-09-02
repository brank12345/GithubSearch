package com.example.githubsearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.utils.setAvatarUrl
import kotlinx.android.synthetic.main.item_user.view.*

abstract class MainViewHolder<T: SearchUiModel>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: T)
}

class UserViewHolder(view: View) : MainViewHolder<SearchUiModel.UserUiModel>(view) {
    override fun onBind(data: SearchUiModel.UserUiModel) {
        with(itemView) {
            avatar.setAvatarUrl(data.user.avatarUrl)
            name.text = data.user.name
        }
    }
}

class LoadingViewHolder(view: View) : MainViewHolder<SearchUiModel.LoadingUiModel>(view) {
    override fun onBind(data: SearchUiModel.LoadingUiModel) = Unit
}