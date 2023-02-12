package umc.standard.todaygym.presentation.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.standard.todaygym.databinding.FragmentImg1Binding

class ViewPagerAdapter(var img: List<String>):RecyclerView.Adapter<ViewPagerAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: FragmentImg1Binding):RecyclerView.ViewHolder(viewBinding.root){
        val img = viewBinding.img1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = FragmentImg1Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
//        holder.img.setImageResource(img[position])
    }

    override fun getItemCount(): Int = img.size
}