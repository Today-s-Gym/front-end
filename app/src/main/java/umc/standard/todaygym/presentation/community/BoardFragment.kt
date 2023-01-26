package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentBoardBinding

class BoardFragment: Fragment() {
    private lateinit var viewBinding: FragmentBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(layoutInflater)


        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_boardFragment_to_addPostFragment)
        }

        val dataList: ArrayList<BoardData> = arrayListOf()
        dataList.apply {
            add(BoardData("세인","title","내용"))
            add(BoardData("세인","제목과","내용"))
            add(BoardData("세인","제목 길이 설정 안해도 되겠지","들여쓰기 해놔야하나"))
            add(BoardData("으","",""))
            add(BoardData("으","","d"))
            add(BoardData("으","l",""))
        }

        val dataRVAdapter = BoardRVAdapter(dataList)

        viewBinding.recyclerPost.adapter = dataRVAdapter
        viewBinding.recyclerPost.layoutManager = LinearLayoutManager(context)

        return viewBinding.root

    }
}