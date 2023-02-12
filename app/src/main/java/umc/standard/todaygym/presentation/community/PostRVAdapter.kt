package umc.standard.todaygym.presentation.community

import android.app.Dialog
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment


import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.MainActivity
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.ChatData
import umc.standard.todaygym.data.model.Heart
import umc.standard.todaygym.data.model.PostData
import umc.standard.todaygym.data.model.Report
import umc.standard.todaygym.data.util.RetrofitClient

import umc.standard.todaygym.databinding.DialogBottomMineBinding
import umc.standard.todaygym.databinding.DialogBottomYoursBinding
import umc.standard.todaygym.databinding.DialogDeletePostBinding
import umc.standard.todaygym.databinding.ItemBoardBinding
import umc.standard.todaygym.databinding.ItemPostBinding


class PostRVAdapter(private val chatDataList: List<ChatData.Result>,private val postDataList:List<PostData.Result>,var categoryId: Int):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var dlg: Dialog
    private lateinit var bundle: Bundle
    var dataheart: Heart? = null
    var reportPost:Report?=null
    var reportUser:Report?=null
    var reportChat:Report?=null
    var deleteChat:Report?=null
    var data2:ChatData?=null
    var deletePost:Report?=null

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

                bottomSheetView.tvEdit.setOnClickListener {
                    bundle =Bundle()
                    bundle.getInt("editCategoryId",categoryId)
                    bundle.getInt("editPostId",data.getPostRes.postId)
                    bundle.putString("editTitle",viewBinding.tvTitle.text.toString())
                    bundle.putString("editContent",viewBinding.tvContent.text.toString())
//                    val navHostFragment = (parent.context as MainActivity).supportFragmentManager.findFragmentById(R.id.action_postFragment_to_editPostFragment) as NavHostFragment
//                    val navController = navHostFragment.navController
//
                    it.findNavController().navigate(R.id.action_postFragment_to_editPostFragment)
                }
                diaBinding.btnNo.setOnClickListener {
                    dlg.dismiss()
                }
                diaBinding.btnYes.setOnClickListener {
                    deletePost(data.getPostRes.postId)
                    dlg.dismiss()
                    dialog.dismiss()
                }
                dialog.setContentView(bottomSheetView.root)
            }

            //남의 게시물일 때
            else{
                dialog.setContentView(bottomSheetyouView.root)
                viewBinding.imgEdit.setOnClickListener {
                    dialog.show()
                }
                bottomSheetyouView.tvPost.setOnClickListener {
                    reportPost(data.getPostRes.postId)
                    dialog.dismiss()
                }
                bottomSheetyouView.tvUser.setOnClickListener {
                    reportUser(data.getPostRes.writerId)
                    dialog.dismiss()
                }

            }

            //데이터 넣기
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
                    imgViewpager.visibility = View.GONE
                }

                if(data2.liked) {
//                    imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                    imgHeart.setOnClickListener {
                        imgHeart.setImageResource(R.drawable.favorite)
                        requestHeart(data2.postId)
                        notifyDataSetChanged()
                    }
                }
                else {
//                    imgHeart.setImageResource(R.drawable.favorite)
                    imgHeart.setOnClickListener {
                        imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
//                        tvHeart.text = (data2.likeCounts + 1).toString()
                        requestHeart(data2.postId)
                        notifyDataSetChanged()
                    }
                }

            }
        }
    }

    inner class ChatViewHolder(private val viewBinding: ItemPostBinding, parent: ViewGroup) : RecyclerView.ViewHolder(viewBinding.root){
        private val dialog = BottomSheetDialog(parent.context)
        private val bottomSheetView = DialogBottomMineBinding.inflate(LayoutInflater.from(parent.context))
        private val bottomSheetyouView = DialogBottomYoursBinding.inflate(LayoutInflater.from(parent.context))


        fun bind(data: ChatData.Result){
            viewBinding.apply {
                tvNickname.text = data.writerName
                tvContent.text = data.content

                //내 댓글일 때
                if(data.mine){
                    dialog.setContentView(bottomSheetView.root)

                    imgEdit.setOnClickListener {
                        dialog.show()
                        bottomSheetView.groupEdit.visibility = View.GONE
                        bottomSheetView.tvDelete.setOnClickListener {
                            deleteChat(data.commentId)
                            dialog.dismiss()
                        }
                    }

                }

                //남의 댓글일 때
                else {
                    dialog.setContentView(bottomSheetyouView.root)

                    imgEdit.setOnClickListener {
                        dialog.show()
                        bottomSheetyouView.apply {
                            groupEdit.visibility = View.GONE
                            tvUser.text = "신고하기"
                            tvUser.setOnClickListener {
                                reportChat(data.commentId)
                                dialog.dismiss()
                            }
                        }
                    }

                }
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

    private fun reportPost(reportId:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.reportPost(reportId)
        call?.enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if(response.isSuccessful){
                    reportPost = response.body()

                }

            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun reportUser(reportId:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.reportUser(reportId)
        call?.enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if(response.isSuccessful){
                    reportUser = response.body()

                }

            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun reportChat(reportId:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.reportChat(reportId)
        call?.enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if(response.isSuccessful){
                    reportChat = response.body()

                }

            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun deleteChat(commentId:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.deleteChat(commentId)
        call?.enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if(response.isSuccessful){
                    deleteChat = response.body()
                }

            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
            }

        })
    }

    private fun deletePost(postId:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.deletePost(postId)
        call?.enqueue(object : Callback<Report> {
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if(response.isSuccessful){
                    deletePost=response.body()
                }

            }

            override fun onFailure(call: Call<Report>, t: Throwable) {
            }

        })
    }


}
