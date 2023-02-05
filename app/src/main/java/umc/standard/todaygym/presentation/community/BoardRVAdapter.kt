package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import umc.standard.todaygym.R
import umc.standard.todaygym.data.model.BoardData
import umc.standard.todaygym.databinding.ItemBoardBinding


class BoardRVAdapter(private val dataList: List<BoardData.Result>):RecyclerView.Adapter<BoardRVAdapter.DataViewHolder>() {
    private lateinit var bundle: Bundle

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
         }

         viewBinding.viewPost.setOnClickListener {
             bundle = Bundle()
             bundle.putInt("id",data.postId)
             Log.d("ididididid",data.postId.toString())
             Navigation.createNavigateOnClickListener(R.id.action_boardFragment_to_postFragment,bundle).onClick(viewBinding.viewPost)
         }

         viewBinding.imgHeart.setOnClickListener {
             viewBinding.imgHeart.setImageResource(R.drawable.ic_baseline_favorite_24)
             viewBinding.tvHeart.text = (data.likeCounts + 1).toString()
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

}
