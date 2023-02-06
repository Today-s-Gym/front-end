package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.BoardData
import umc.standard.todaygym.data.model.Heart
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.ItemBoardBinding


class BoardRVAdapter(private val dataList: List<BoardData.Result>):RecyclerView.Adapter<BoardRVAdapter.DataViewHolder>() {
    private lateinit var bundle: Bundle
    var dataheart: Heart? = null

    inner class DataViewHolder(private val viewBinding: ItemBoardBinding):RecyclerView.ViewHolder(viewBinding.root){
     fun bind(data: BoardData.Result){

         viewBinding.apply {
             tvNickname.text = data.writerNickName
             tvCreateAt.text = data.createdAt
             tvTitle.text = data.title
             tvContent.text = data.content
             if(data.likeCounts != 0){
                 tvHeart.visibility = View.VISIBLE
                 tvHeart.text = data.likeCounts.toString()
             }
             if(data.commentCounts != 0){
                 tvChat.visibility = View.VISIBLE
                 tvChat.text = data.commentCounts.toString()
             }

             if(data.recordId == 0 ){
                 btnExrecord.visibility = View.GONE
                 tvExdate.text = data.recordCreatedAt
                 tvExcontent.text = data.recordContent
             }
             if(data.postPhotoList.isEmpty()){
                 viewBinding.imgViewpager.visibility = View.GONE
             }

             Glide.with(itemView)
                 .load(data.writerAvatarImgUrl)
                 .into(imgAccount)

         }



         viewBinding.viewPost.setOnClickListener {
             bundle = Bundle()
             bundle.putInt("id",data.postId)
             Log.d("ididididid",data.postId.toString())
             Navigation.createNavigateOnClickListener(R.id.action_boardFragment_to_postFragment,bundle).onClick(viewBinding.viewPost)
         }

         if(data.liked) {
             viewBinding.imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
             viewBinding.imgHeart.setOnClickListener {
                 viewBinding.imgHeart.setImageResource(R.drawable.favorite)
                 viewBinding.tvHeart.text = (data.likeCounts - 1).toString()
                 requestHeart(data.postId)
             }
         }
         else {
             viewBinding.imgHeart.setOnClickListener {
                 viewBinding.imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                 viewBinding.tvHeart.text = (data.likeCounts + 1).toString()
                 requestHeart(data.postId)
             }
         }

     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size


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
                TODO("Not yet implemented")
            }

        })
    }
}
