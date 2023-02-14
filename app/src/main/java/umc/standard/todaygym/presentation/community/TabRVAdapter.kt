package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.standard.todaygym.R
import umc.standard.todaygym.data.model.PostData
import umc.standard.todaygym.data.model.TabNewData
import umc.standard.todaygym.databinding.ItemExrecordBinding

class TabRVAdapter(private val dataList: List<TabNewData.Result.Content>):RecyclerView.Adapter<TabRVAdapter.DataViewHolder>(){

    inner class DataViewHolder(private val viewBinding: ItemExrecordBinding):RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: TabNewData.Result.Content){
            viewBinding.tvExcontent.text = data.content
            viewBinding.tvExdate.text = data.createdAt
            Glide.with(itemView)
                .load(data.imgUrl)
                .into(viewBinding.imgExrecord)

            val bundle = Bundle()
            viewBinding.btnChoose.setOnClickListener {
//                bundle.putString("content",viewBinding.tvExcontent.text.toString())
//                bundle.putString("date",viewBinding.tvExdate.text.toString())
//                bundle.putString("url",data.imgUrl)
                val navController = it.findNavController()
                navController.previousBackStackEntry?.savedStateHandle?.set("content",viewBinding.tvExcontent.text.toString())
                navController.previousBackStackEntry?.savedStateHandle?.set("date",viewBinding.tvExdate.text.toString())
                navController.previousBackStackEntry?.savedStateHandle?.set("url",data.imgUrl)
                navController.previousBackStackEntry?.savedStateHandle?.set("recordId",data.recordId)
//                Navigation.createNavigateOnClickListener(R.id.action_addExFragment_to_addPostFragment,bundle).onClick(viewBinding.btnChoose)

                navController.popBackStack()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemExrecordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int =dataList.size
}