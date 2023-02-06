package umc.standard.todaygym.presentation.community

import android.app.Dialog
import android.util.Log

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.marginBottom

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.ChatData
import umc.standard.todaygym.data.model.Heart
import umc.standard.todaygym.data.model.PostData
import umc.standard.todaygym.data.util.RetrofitClient

import umc.standard.todaygym.databinding.DialogBottomMineBinding
import umc.standard.todaygym.databinding.DialogBottomYoursBinding
import umc.standard.todaygym.databinding.DialogDeletePostBinding
import umc.standard.todaygym.databinding.ItemBoardBinding
import umc.standard.todaygym.databinding.ItemPostBinding

class PostRVAdapter(private val chatDataList: List<ChatData.Result>,private val postDataList:List<PostData.Result>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var dlg: Dialog
    var dataheart: Heart? = null

    enum class ContentViewType(val num: Int){
        Post(0), Chat(1),Empty(2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        return when (viewType) {
            ContentViewType.Post.num -> {
                val viewBinding = ItemBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                dlg = Dialog(parent.context)
                val diaBinding = DialogDeletePostBinding.inflate(LayoutInflater.from(parent.context))
                PostViewHolder(viewBinding,parent,diaBinding)
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
                holder.bind(postDataList[position])
            }
            else -> {
                (holder as ChatViewHolder).bind(chatDataList[position-1])
            }
        }
    }

    override fun getItemCount(): Int = postDataList.size + chatDataList.size

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> ContentViewType.Post.num
            1-> ContentViewType.Chat.num
            else -> ContentViewType.Empty.num
        }
    }

    inner class PostViewHolder(private val viewBinding: ItemBoardBinding, parent: ViewGroup,private val diaBinding: DialogDeletePostBinding) : RecyclerView.ViewHolder(viewBinding.root){
        private val dialog = BottomSheetDialog(parent.context)
        private val bottomSheetView = DialogBottomMineBinding.inflate(LayoutInflater.from(parent.context))
        private val bottomSheetyouView = DialogBottomYoursBinding.inflate(LayoutInflater.from(parent.context))

        fun bind(data: PostData.Result){
            //내 게시물일 때의 dialog
            if(data.mine){
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
                dialog.setContentView(bottomSheetView.root)
            }
            else{
                dialog.setContentView(bottomSheetyouView.root)
                viewBinding.imgEdit.setOnClickListener {
                    dialog.show()
                }

            }


            var data2 = data.getPostRes
            viewBinding.apply {
                tvNickname.text = data2.writerNickName
                tvCreateAt.text = data2.createdAt
                tvTitle.text = data2.title
                tvContent.text = data2.content
                if(data2.likeCounts != 0){
                    tvHeart.visibility = View.VISIBLE
                    tvHeart.text = data2.likeCounts.toString()
                }
                if(data2.commentCounts != 0){
                    tvChat.visibility = View.VISIBLE
                    tvChat.text = data2.commentCounts.toString()
                }

                if(data2.recordId == 0 ){
                    btnExrecord.visibility = View.GONE
                    tvExdate.text = data2.recordCreatedAt
                    tvExcontent.text = data2.recordContent
                }
                if(data2.postPhotoList.isEmpty()){
                    viewBinding.imgViewpager.visibility = View.GONE
                }
                if(data2.liked) {
                    viewBinding.imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                    viewBinding.imgHeart.setOnClickListener {
                        viewBinding.imgHeart.setImageResource(R.drawable.favorite)
                        viewBinding.tvHeart.text = (data2.likeCounts - 1).toString()
                        requestHeart(data2.postId)
                    }
                }
                else {
                    viewBinding.imgHeart.setOnClickListener {
                        viewBinding.imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                        viewBinding.tvHeart.text = (data2.likeCounts + 1).toString()
                        requestHeart(data2.postId)
                    }
                }

            }
        }
    }

    inner class ChatViewHolder(private val viewBinding: ItemPostBinding, parent: ViewGroup) : RecyclerView.ViewHolder(viewBinding.root){
        private val dialog = BottomSheetDialog(parent.context)
        private val bottomSheetView = DialogBottomMineBinding.inflate(LayoutInflater.from(parent.context))

        fun bind(data: ChatData.Result){
            viewBinding.apply {
                tvNickname.text = data.writerName
                tvContent.text = data.content
            }
            dialog.setContentView(bottomSheetView.root)

            viewBinding.imgEdit.setOnClickListener {
                dialog.show()
                bottomSheetView.groupEdit.visibility = View.GONE
            }
        }
    }
    private fun requestHeart(id:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.heart(id)
        call?.enqueue(object : Callback<Heart> {
            override fun onResponse(call: Call<Heart>, response: Response<Heart>) {
                if(response.isSuccessful){
                    dataheart = response.body()
                    Log.d("ididididiidid",id.toString())
                }

            }

            override fun onFailure(call: Call<Heart>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }



}
