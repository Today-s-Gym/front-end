package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.standard.todaygym.data.mdoel.PostData
import umc.standard.todaygym.databinding.FragmentPostBinding

class PostFragment: Fragment() {
    private lateinit var viewBinding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPostBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val dataList: ArrayList<PostData> = arrayListOf()
        dataList.apply {
            add(PostData("멍","냥"))
            add(PostData("세인","아아아"))
            add(PostData("잉","하"))
            add(PostData("멍","냥"))
            add(PostData("세인","아아아"))
            add(PostData("잉","하"))
        }

        val dataRVAdapter = PostRVAdapter(dataList)

        viewBinding.recyclerChat.adapter = dataRVAdapter
        viewBinding.recyclerChat.layoutManager = LinearLayoutManager(context)

        return viewBinding.root
    }
}