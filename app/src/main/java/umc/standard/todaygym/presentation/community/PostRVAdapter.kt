package umc.standard.todaygym.presentation.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.ItemBoardBinding
import umc.standard.todaygym.databinding.ItemPostBinding

class PostRVAdapter(private val dataList: ArrayList<PostData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ContentViewType(val num: Int){
        Post(0), Chat(1),Empty(2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        return when (viewType) {
            ContentViewType.Post.num -> {
                val viewBinding = ItemBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                PostViewHolder(viewBinding)
            }
            ContentViewType.Chat.num -> {
                val viewBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                ChatViewHolder(viewBinding)
            }
            else -> {
                val viewBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                ChatViewHolder(viewBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is PostViewHolder -> {
                holder.bind(dataList[position])
            }
            else -> {
                (holder as ChatViewHolder).bind(dataList[position])
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> ContentViewType.Post.num
            1 -> ContentViewType.Chat.num
            else -> ContentViewType.Empty.num
        }
    }

    inner class PostViewHolder(private val viewBinding: ItemBoardBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: PostData){

        }
    }

    inner class ChatViewHolder(private val viewBinding: ItemPostBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: PostData){
            viewBinding.tvNickname.text = data.nickname
            viewBinding.tvContent.text = data.chat
        }
    }



}
