package com.example.githubsearch

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.base.SingleLiveEvent
import com.example.githubsearch.utils.inflate

class MainAdapter(
    private val needToLoadNextEvent : SingleLiveEvent<Void>
) : RecyclerView.Adapter<MainViewHolder<*>>() {
    private var dataList = listOf<SearchUiModel>()

    fun setData(userList: List<SearchUiModel>) {
        val diffCallback = SearchDiffCallback(dataList, userList)
        val result = DiffUtil.calculateDiff(diffCallback)
        result.dispatchUpdatesTo(this)
        dataList = userList
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataList[position]) {
            is SearchUiModel.UserUiModel -> USER_VIEW_TYPE
            is SearchUiModel.LoadingUiModel -> LOADING_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder<*> {
       return when(viewType) {
           USER_VIEW_TYPE -> UserViewHolder(parent.inflate(R.layout.item_user))
           LOADING_VIEW_TYPE -> LoadingViewHolder(parent.inflate(R.layout.item_loading))
           else -> LoadingViewHolder(parent.inflate(R.layout.item_loading))
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder<*>, position: Int) {
        when(holder) {
            is UserViewHolder -> holder.onBind(dataList[position] as SearchUiModel.UserUiModel)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onViewAttachedToWindow(holder: MainViewHolder<*>) {
        super.onViewAttachedToWindow(holder)
        if (holder.bindingAdapterPosition == itemCount - LOAD_NEXT_BUFFER) needToLoadNextEvent.call()
    }

    inner class SearchDiffCallback(
        private val oldList: List<SearchUiModel>,
        private val newList: List<SearchUiModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return (oldItem is SearchUiModel.UserUiModel && newItem is SearchUiModel.UserUiModel)
                    || (oldItem is SearchUiModel.LoadingUiModel && newItem is SearchUiModel.LoadingUiModel)
        }
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return when {
                oldItem is SearchUiModel.UserUiModel && newItem is SearchUiModel.UserUiModel -> {
                    oldItem.user == newItem.user
                }
                oldItem is SearchUiModel.LoadingUiModel && newItem is SearchUiModel.LoadingUiModel -> true
                else -> false
            }
        }
    }

    companion object {
        private const val USER_VIEW_TYPE = 1
        private const val LOADING_VIEW_TYPE = 2
        private const val LOAD_NEXT_BUFFER = 10
    }
}