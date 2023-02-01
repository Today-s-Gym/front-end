package umc.standard.todaygym.presentation.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.standard.todaygym.R

class RecordPictureAdapter(
    private val fragmentActivity: FragmentActivity,
    private val imageList: MutableList<String>
) : RecyclerView.Adapter<RecordPictureAdapter.PagerViewHolder>(){

    // View 를 담을 ViewHolder class 를 정의 한다.
    inner class PagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val item: ImageView = itemView.findViewById(R.id.iv_recordpicview)
    }

    // ViewHolder 를 인스턴스화 하고 return
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(fragmentActivity).inflate(
            R.layout.viewholder_recordpic,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    // 뷰와 데이터를 바인딩 하는 메서드
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        Glide.with(fragmentActivity).load(imageList[position]).into(holder.item)
        // holder.item.setImageDrawable(ContextCompat.getDrawable(fragmentActivity, imageList[position]))
    }

    override fun getItemCount() = imageList.size
}