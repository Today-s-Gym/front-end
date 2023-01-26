package umc.standard.todaygym.presentation.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.ItemBoardBinding


class BoardRVAdapter(private val dataList: ArrayList<BoardData>):RecyclerView.Adapter<BoardRVAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: ItemBoardBinding):RecyclerView.ViewHolder(viewBinding.root){
     fun bind(data: BoardData){
         viewBinding.tvNickname.text = data.nickname
         viewBinding.tvTitle.text = data.title
         viewBinding.tvContent.text = data.desc

         if(data.title.isEmpty()){
             viewBinding.imgViewpager.visibility = View.GONE
         }
         if(data.desc.isEmpty()){
             viewBinding.groupExrecord.visibility = View.GONE
         }

        viewBinding.imgChat.setOnClickListener {
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
