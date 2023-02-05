package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.standard.todaygym.data.api.CommunityService
import umc.standard.todaygym.data.model.TabNewData
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentTabNewBinding

class TabNewFragment: Fragment() {
    private lateinit var viewBinding: FragmentTabNewBinding
    var data: TabNewData?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTabNewBinding.inflate(layoutInflater)
        request(1)
        return viewBinding.root

    }
    private fun request(page:Int){
        val communityInterface: CommunityService? =
            RetrofitClient.getClient()?.create(CommunityService::class.java)
        val call = communityInterface?.tabNewRecord(page)
        call?.enqueue(object : Callback<TabNewData> {
            override fun onResponse(call: Call<TabNewData>, response: Response<TabNewData>) {
                if(response.isSuccessful){
                    data = response.body()
                    data?.let { tabnewdapter(it.result.content) }
                }

            }

            override fun onFailure(call: Call<TabNewData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun tabnewdapter(tabNewList: List<TabNewData.Result.Content>){
        val dataRVAdapter = TabRVAdapter(tabNewList)

        viewBinding.recycler.adapter = dataRVAdapter
        viewBinding.recycler.layoutManager = LinearLayoutManager(context)
        dataRVAdapter.notifyDataSetChanged()
        viewBinding.recycler.setHasFixedSize(true)

    }

}