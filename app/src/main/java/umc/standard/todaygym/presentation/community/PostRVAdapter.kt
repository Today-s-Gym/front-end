package umc.standard.todaygym.presentation.community


import android.app.Dialog
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.ChatData
import umc.standard.todaygym.data.model.Heart
import umc.standard.todaygym.data.model.PostData
import umc.standard.todaygym.data.model.Report
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.*


class PostRVAdapter(
    private val chatDataList: List<ChatData.Result>,
    private val postDataList: List<PostData.Result>,
    var categoryId: Int,
    var postId: Int
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                    bundle.putInt("editCategoryId",categoryId)
                    bundle.putInt("editPostId",data.getPostRes.postId)
                    bundle.putString("editTitle",viewBinding.tvTitle.text.toString())
                    bundle.putString("editContent",viewBinding.tvContent.text.toString())
                    dialog.dismiss()
                    itemView.findNavController().navigate(R.id.action_postFragment_to_editPostFragment,bundle)

                }
                diaBinding.btnNo.setOnClickListener {
                    dlg.dismiss()
                }
                diaBinding.btnYes.setOnClickListener {
                    deletePost(data.getPostRes.postId)
                    dlg.dismiss()
                    dialog.dismiss()
                    itemView.findNavController().popBackStack()
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

            viewBinding.imgViewpager.adapter = ViewPagerAdapter(data.getPostRes.postPhotoList)
            viewBinding.imgViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


            //데이터 넣기
            var data2 = data.getPostRes
            viewBinding.apply {
                tvNickname.text = data2.writerNickName
                tvCreateAt.text = data2.createdAt
                tvTitle.text = data2.title
                tvContent.text = data2.content
                if (data2.likeCounts != 0) {
                    tvHeart.visibility = View.VISIBLE
                    tvHeart.text = data2.likeCounts.toString()
                }
                if (data2.commentCounts != 0) {
                    tvChat.visibility = View.VISIBLE
                    tvChat.text = data2.commentCounts.toString()
                }

                if (data2.recordId == 0) {
                    btnExrecord.visibility = View.GONE

                } else {
                    tvExdate.text = data2.recordCreatedAt
                    tvExcontent.text = data2.recordContent
                    Glide.with(itemView).load(data2.recordPhotoImgUrl).into(imgExrecord)
                }

                if (data2.postPhotoList.isEmpty()) {
                    imgViewpager.visibility = View.GONE
                }

                if (data2.liked) {
                    imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                    //좋아요 취소
                    var i = 0
                    imgHeart.setOnClickListener {
                        if (i % 2 == 0) {
                            imgHeart.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            (data2.likeCounts - 1).toString().also { tvHeart.text = it }
                            if ((data2.likeCounts - 1) == 0) {
                                tvHeart.visibility = View.GONE
                            }
                        } else {
                            imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                            data2.likeCounts.toString().also { tvHeart.text = it }
                            tvHeart.visibility = View.VISIBLE
                        }
                        i++
                        requestHeart(data2.postId)

                    }

                }
                //좋아요 안누른 경우
                else{
                //좋아요 누름
                    var i = 0
                    imgHeart.setOnClickListener {
                        if (i % 2 == 0) {
                            imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                            (data2.likeCounts + 1).toString().also { tvHeart.text = it }
                            tvHeart.visibility = View.VISIBLE
                        } else {
                            imgHeart.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            data2.likeCounts.toString().also { tvHeart.text = it }
                            if (data2.likeCounts == 0) {
                                tvHeart.visibility = View.GONE
                            }
                        }
                        i++
                        requestHeart(data2.postId)
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

                //내 댓글일 때 - 댓글 삭제
                if(data.mine){
                    dialog.setContentView(bottomSheetView.root)

                    imgEdit.setOnClickListener {
                        dialog.show()
                        bottomSheetView.groupEdit.visibility = View.GONE
                        bottomSheetView.tvDelete.setOnClickListener {
                            deleteChat(data.commentId)
                            PostFragment().loadChat(postId)
                            dialog.dismiss()
                        }
                    }

                }

                //남의 댓글일 때 - 댓글 신고
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
                }

            }

            override fun onFailure(call: Call<Heart>, t: Throwable) {

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
