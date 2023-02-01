package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.mdoel.BoardData
import umc.standard.todaygym.data.mdoel.RequestAddPost
import umc.standard.todaygym.data.util.API_CONSTNATS.BASE_URL
import umc.standard.todaygym.data.util.RetrofitClient
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

        }

        resultList.apply {
            add(BoardData.Result("주짓수",10,"오늘 운동은 이것저것","1분 전",10,false,10,imgurl,"오늘 기록은","2022-12-24",10,"url","제목이다 드디어","ha","몰라하"))
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