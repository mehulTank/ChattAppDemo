package com.example.chatappdemo.ui.adapter

/*
*
* Chat List Adapater
* */
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappdemo.R
import com.example.chatappdemo.data.model.ChatModel
import com.example.chatappdemo.databinding.ItemChatListBinding
import com.example.chatappdemo.ui.interfaces.OnRecylerItemClickListener


class ChatListAdapter(
    private val context: Context,
    private val onRecylerItemClickListener: OnRecylerItemClickListener<ChatModel>

) : RecyclerView.Adapter<ChatListAdapter.ProductViewHolder>() {
    private val arrayListHistory = ArrayList<ChatModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val customRowProductListingBinding: ItemChatListBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_chat_list,
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
            holder.setIsRecyclable(false)
            val chatModel = arrayListHistory[position]
            Log.d("--- rec pos","---"+position+ "value "+chatModel.message)
            holder.bind(chatModel)
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onRecylerItemClickListener.onViewItemClicked(chatModel, position)
                }
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ProductViewHolder(val customRowProductListingBinding: ItemChatListBinding) :
        RecyclerView.ViewHolder(customRowProductListingBinding.root) {
        fun bind(chatModel: ChatModel) {
            this.customRowProductListingBinding.chat = chatModel
            customRowProductListingBinding.executePendingBindings()
        }
    }

    fun setList(list: List<ChatModel>) {
        arrayListHistory.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        arrayListHistory.clear()
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<ChatModel> {
        return arrayListHistory
    }
}