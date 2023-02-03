package umc.standard.todaygym.presentation.community

import android.app.Dialog

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.view.WindowManager

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import umc.standard.todaygym.R
import umc.standard.todaygym.data.model.PostData

import umc.standard.todaygym.databinding.DialogBottomMineBinding
import umc.standard.todaygym.databinding.DialogDeletePostBinding
import umc.standard.todaygym.databinding.ItemBoardBinding
import umc.standard.todaygym.databinding.ItemPostBinding

class PostRVAdapter(private val dataList: ArrayList<PostData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var dlg: Dialog

    enum class ContentViewType(val num: Int){
        Post(0), Chat(1),Empty(2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        val dialog = BottomSheetDialog(parent.context)
        val bottomSheetView = DialogBottomMineBinding.inflate(LayoutInflater.from(parent.context))

        dialog.setContentView(bottomSheetView.root)
        return when (viewType) {
            ContentViewType.Post.num -> {
                val viewBinding = ItemBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                dlg = Dialog(parent.context)
                val diaBinding = DialogDeletePostBinding.inflate(LayoutInflater.from(parent.context))
                dlg.setContentView(diaBinding.root)
                dlg.setCancelable(false)
                viewBinding.imgEdit.setOnClickListener {
                        dialog.show()
                }
                bottomSheetView.tvDelete.setOnClickListener {
                    dlg.show()
                    dlg.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
                    dlg.window?.setBackgroundDrawableResource(R.drawable.dialog_box)
                }
                diaBinding.btnNo.setOnClickListener {
                    dlg.dismiss()
                }
                diaBinding.btnYes.setOnClickListener {
                    dlg.dismiss()
                }

                PostViewHolder(viewBinding)
            }
            ContentViewType.Chat.num -> {
                val viewBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)

                ChatViewHolder(viewBinding,parent)
            }
            else -> {
                val viewBinding = ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                ChatViewHolder(viewBinding,parent)
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

    inner class ChatViewHolder(private val viewBinding: ItemPostBinding, parent: ViewGroup) : RecyclerView.ViewHolder(viewBinding.root){
        private val dialog = BottomSheetDialog(parent.context)
        private val bottomSheetView = DialogBottomMineBinding.inflate(LayoutInflater.from(parent.context))

        fun bind(data: PostData){
            viewBinding.tvNickname.text = data.nickname
            viewBinding.tvContent.text = data.chat
            dialog.setContentView(bottomSheetView.root)

            viewBinding.imgEdit.setOnClickListener {
                dialog.show()
                bottomSheetView.groupEdit.visibility = View.GONE
            }
        }
    }



}
