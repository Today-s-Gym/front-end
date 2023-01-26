package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.data.DataRewinder
import umc.standard.todaygym.databinding.FragmentPostBinding

class PostFragment: Fragment() {
    private lateinit var viewBinding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPostBinding.inflate(layoutInflater)

        val dataList: ArrayList<PostData> = arrayListOf()
        dataList.apply {
            add(PostData("멍","냥"))
            add(PostData("세인","인생이란"))
            add(PostData("잉","구려"))
        }

        val dataRVAdapter = PostRVAdapter(dataList)

        viewBinding.recyclerChat.adapter = dataRVAdapter
        viewBinding.recyclerChat.layoutManager = LinearLayoutManager(context)

        return viewBinding.root
    }
}