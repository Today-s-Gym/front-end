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
//        val dataList: ArrayList<PostData> = arrayListOf()
//        dataList.apply {
//            add(PostData("2023-2-2","오운완"))
//            add(PostData("2023-2-1","안녕안녕"))
//        }

//        val dataRVAdapter = TabRVAdapter(dataList)
//
//        viewBinding.recycler.adapter = dataRVAdapter
//        viewBinding.recycler.layoutManager = LinearLayoutManager(context)

        return viewBinding.root

    }
}