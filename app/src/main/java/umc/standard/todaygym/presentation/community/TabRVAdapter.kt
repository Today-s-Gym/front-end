package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentTabNewBinding
import umc.standard.todaygym.databinding.ItemExrecordBinding

class TabRVAdapter(private val dataList: ArrayList<BoardData>):RecyclerView.Adapter<TabRVAdapter.DataViewHolder>(){

    inner class DataViewHolder(private val viewBinding: ItemExrecordBinding):RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: BoardData){
            viewBinding.tvExcontent.text = data.desc
            val bundle = Bundle()
            viewBinding.btnChoose.setOnClickListener {
                bundle.putString("desc",viewBinding.btnChoose.text.toString())
                Navigation.createNavigateOnClickListener(R.id.action_addExFragment_to_addPostFragment,bundle).onClick(viewBinding.btnChoose)

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