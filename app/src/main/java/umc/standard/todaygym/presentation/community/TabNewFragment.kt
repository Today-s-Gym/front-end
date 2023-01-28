package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.standard.todaygym.databinding.FragmentTabNewBinding

class TabNewFragment: Fragment() {
    private lateinit var viewBinding: FragmentTabNewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTabNewBinding.inflate(layoutInflater)
        val dataList: ArrayList<BoardData> = arrayListOf()
        dataList.apply {
            add(BoardData("k","dkddk","dkdkdkdk"))
            add(BoardData("k","dkddk","오늘의 기록"))
            add(BoardData("k","dkddk","ㅇㅇ"))
        }

        val dataRVAdapter = TabRVAdapter(dataList)

        viewBinding.recycler.adapter = dataRVAdapter
        viewBinding.recycler.layoutManager = LinearLayoutManager(context)

        return viewBinding.root

    }
}