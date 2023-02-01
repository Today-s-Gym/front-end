package umc.standard.todaygym.presentation.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import umc.standard.todaygym.R
import umc.standard.todaygym.data.model.BoardData
import umc.standard.todaygym.databinding.ItemBoardBinding


class BoardRVAdapter(private val dataList: ArrayList<BoardData.Result>):RecyclerView.Adapter<BoardRVAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: ItemBoardBinding):RecyclerView.ViewHolder(viewBinding.root){
     fun bind(data: BoardData.Result){

         viewBinding.tvTitle.text = data.title
         viewBinding.tvContent.text = data.content
         viewBinding.tvCreateAt.text = data.createdAt
         if(data.likeCounts != 0){
             viewBinding.tvHeart.visibility = View.VISIBLE
             viewBinding.tvHeart.text = data.likeCounts.toString()
         }
         if(data.commentCounts != 0) {
             viewBinding.tvChat.visibility = View.VISIBLE
             viewBinding.tvChat.text = data.commentCounts.toString()
         }
         viewBinding.tvNickname.text = data.writerNickName
         viewBinding.tvExdate.text = data.recordCreatedAt
         viewBinding.tvExcontent.text = data.recordContent


         if(data.postPhotoList.isEmpty()){
             viewBinding.imgViewpager.visibility = View.GONE
         }
         if(data.recordId == 0){
             viewBinding.groupExrecord.visibility = View.GONE
         }
//         if(data.liked){
//             viewBinding.imgHeart.src =
//         }


        viewBinding.viewPost.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.action_boardFragment_to_postFragment).onClick(viewBinding.imgChat)
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
