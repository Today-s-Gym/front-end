package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager


import umc.standard.todaygym.R
import umc.standard.todaygym.data.model.BoardData
import umc.standard.todaygym.databinding.FragmentBoardBinding

class BoardFragment: Fragment() {
    private lateinit var viewBinding: FragmentBoardBinding
    var data: BoardData? = null
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(layoutInflater)

        viewBinding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        viewBinding.recyclerPost.setOnClickListener {
            findNavController().navigate(R.id.action_boardFragment_to_postFragment)
        }

        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_boardFragment_to_addPostFragment)
        }

        viewBinding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_boardFragment_to_addPostFragment)
        }

        val dataList: ArrayList<BoardData> = arrayListOf()
        val resultList: ArrayList<BoardData.Result> = arrayListOf()
        val imgurl: List<String> = listOf()
        imgurl.apply {
            "ㅇ"
        }

        dataList.apply {
            add(BoardData(1000,true,"쩝",resultList))
            add(BoardData(1000,true,"쩝",resultList))

        }

        resultList.apply {
            add(BoardData.Result("주짓수",5,"델리히바에서 스윕","3분 전",10,false,10,
                listOf(),"오운완","2023-2-2",0,"","오늘 주짓수 기록","","오늘의 짐"))
            add(BoardData.Result("주짓수",5,"발목을 내 쪽으로 강하게 끌어 당기면서 라펠을 바닥으로 당긴다.","1일 전",10,false,10,
                listOf(),"오운완","2023-2-1",0,"","오늘 주짓수 기록","","오늘의 짐"))

            add(BoardData.Result("주짓수",6,"오늘 운동은 이것저것","8일 전",5,false,11,imgurl,"오늘 기록은","2022-12-24",10,"url","오늘 운동은","","세인"))
            add(BoardData.Result("주짓수",1,"이 운동 저 운동","10일 전",7,false,12,
                listOf("ddd"),"오늘 기록은","2022-12-24",0,"url","운동운동운동","","센"))

        }

        val dataRVAdapter = BoardRVAdapter(resultList)

        viewBinding.recyclerPost.adapter = dataRVAdapter
        viewBinding.recyclerPost.layoutManager = LinearLayoutManager(context)

        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            name = arguments?.getString("category") as String
            tvToolbar.text = "$name 게시판"
        }
    }




}