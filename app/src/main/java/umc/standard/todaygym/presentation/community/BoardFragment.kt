package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.BoardData
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentBoardBinding

class BoardFragment: Fragment() {
    private lateinit var viewBinding: FragmentBoardBinding
    private lateinit var name: String
    val JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk"
    var data: BoardData? = null
    var boardList: List<BoardData.Result>? = null

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
        var id = arguments?.getInt("id") as Int
        request(id)
        viewBinding.apply {
            name = arguments?.getString("category") as String
            tvToolbar.text = "$name 게시판"

        }

         return viewBinding.root

    }



    private fun request(id:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.getBoard(JWT,id)
        call?.enqueue(object : Callback<BoardData> {
            override fun onResponse(call: Call<BoardData>, response: Response<BoardData>) {
                if(response.isSuccessful){
                    data = response.body()
                    data?.let { boardAdapter(it.result) }
                }

            }

            override fun onFailure(call: Call<BoardData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun boardAdapter(boardList: List<BoardData.Result>){
        val dataRVAdapter = BoardRVAdapter(boardList)

        viewBinding.recyclerPost.adapter = dataRVAdapter
        viewBinding.recyclerPost.layoutManager = LinearLayoutManager(context)
        dataRVAdapter.notifyDataSetChanged()
        viewBinding.recyclerPost.setHasFixedSize(true)

    }




}