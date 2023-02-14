package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
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


class BoardRVAdapter(private val dataList: List<BoardData.Result>,var categoryID:Int):RecyclerView.Adapter<BoardRVAdapter.DataViewHolder>() {
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

             // 좋아요 누른 경우
             if(data.liked){
                 imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                 //좋아요 취소
                 var i=0
                 imgHeart.setOnClickListener {
                     if(i%2==0){
                         imgHeart.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                         (data.likeCounts - 1).toString().also { tvHeart.text = it }
                         if((data.likeCounts -1) ==0){
                             tvHeart.visibility = View.GONE
                         }
                     }
                     else{
                         imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                         data.likeCounts.toString().also { tvHeart.text = it }
                         tvHeart.visibility = View.VISIBLE
                     }
                     i++
                     requestHeart(data.postId)

                 }
             }

             //좋아요 안누른 경우
             else{
                 //좋아요 누름
                 var i=0
                 imgHeart.setOnClickListener {
                     if(i%2==0){
                         imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
                         (data.likeCounts + 1).toString().also { tvHeart.text = it }
                         tvHeart.visibility = View.VISIBLE
                     }
                     else{
                         imgHeart.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                         data.likeCounts.toString().also { tvHeart.text = it }
                         if(data.likeCounts ==0){
                             tvHeart.visibility = View.GONE
                         }
                     }
                     i++
                     requestHeart(data.postId)
                 }

             }


             if(data.commentCounts != 0){
                 tvChat.visibility = View.VISIBLE
                 tvChat.text = data.commentCounts.toString()
             }

             if(data.recordId == 0 ){
                 btnExrecord.visibility = View.GONE
             }
             else{
                 btnExrecord.visibility = View.VISIBLE
                 tvExdate.text = data.recordCreatedAt
                 tvExcontent.text = data.recordContent
                 Glide.with(itemView).load(data.recordPhotoImgUrl).into(imgExrecord)
             }

             if(data.postPhotoList.isEmpty()){
                 viewBinding.imgViewpager.visibility = View.GONE
             }

             Glide.with(itemView)
                 .load(data.writerAvatarImgUrl)
                 .into(imgAccount)

         }

         viewBinding.imgViewpager.adapter = ViewPagerAdapter(data.postPhotoList)
         viewBinding.imgViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL



         viewBinding.viewPost.setOnClickListener {
             bundle = Bundle()
             bundle.putInt("id",data.postId)
             bundle.putInt("categoryId",categoryID)
             Navigation.createNavigateOnClickListener(R.id.action_boardFragment_to_postFragment,bundle).onClick(viewBinding.viewPost)
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


    override fun getItemViewType(position: Int): Int {
        return position
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
}
