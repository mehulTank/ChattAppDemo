package com.example.chatappdemo.ui.adapter

/*
*
* User list adapater
* */

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappdemo.R
import com.example.chatappdemo.data.model.User
import com.example.chatappdemo.databinding.ItemUserListBinding
import com.example.chatappdemo.ui.interfaces.OnRecylerItemClickListener

class UserListAdapter(
    private val context: Context,
    private val onRecylerItemClickListener: OnRecylerItemClickListener<User>

) : RecyclerView.Adapter<UserListAdapter.ProductViewHolder>() {
    private val arrayListHistory = ArrayList<User>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val customRowProductListingBinding: ItemUserListBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_user_list,
                parent,
                false
            )

        return ProductViewHolder(customRowProductListingBinding)
    }

    override fun getItemCount(): Int {
        return arrayListHistory.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (arrayListHistory.size > 0) {
            val partListingModel = arrayListHistory[position]
            holder.bind(partListingModel)
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onRecylerItemClickListener.onViewItemClicked(partListingModel, position)
                }
            })
        }
    }

    class ProductViewHolder(val customRowProductListingBinding: ItemUserListBinding) :
        RecyclerView.ViewHolder(customRowProductListingBinding.root) {
        fun bind(user: User) {
            this.customRowProductListingBinding.user = user
            customRowProductListingBinding.executePendingBindings()
        }
    }

    fun setList(list: List<User>) {
        arrayListHistory.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        arrayListHistory.clear()
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<User> {
        return arrayListHistory
    }
}