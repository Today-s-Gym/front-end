package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.standard.todaygym.data.mdoel.BoardData
import umc.standard.todaygym.data.mdoel.PostData
import umc.standard.todaygym.databinding.FragmentTabNewBinding

class TabNewFragment: Fragment() {
    private lateinit var viewBinding: FragmentTabNewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTabNewBinding.inflate(layoutInflater)
        val dataList: ArrayList<PostData> = arrayListOf()
        dataList.apply {
            add(PostData("닉네임","안녕안녕"))
            add(PostData("하잉","안녕안녕"))
            add(PostData("운동기록","운동기록"))
        }

        val dataRVAdapter = TabRVAdapter(dataList)

        viewBinding.recycler.adapter = dataRVAdapter
        viewBinding.recycler.layoutManager = LinearLayoutManager(context)

        return viewBinding.root

    }
}