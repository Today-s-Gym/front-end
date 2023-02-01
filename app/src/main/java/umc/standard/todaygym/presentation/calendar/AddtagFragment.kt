package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.RecordInterface
import umc.standard.todaygym.data.model.RecentTag
import umc.standard.todaygym.data.model.Record
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddtagBinding

class AddtagFragment : Fragment() {

    private lateinit var binding: FragmentAddtagBinding
    lateinit var recordData : Record
    var recentTagList = arrayListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddtagBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. 넘겨받은 기록 정보 변수 설정
        recordData = arguments?.getSerializable("recordData") as Record
        // 넘겨받은 태그 값들 보여주기
        for(tag in recordData.tags) {
            addDeleteTag(tag)
        }
        binding.apply {
            // 2. 상단바 버튼 기능
            // 뒤로가기 버튼 클릭 시
            btnBack.setOnClickListener {
                findNavController().popBackStack() // 이전 화면으로 이동
            }
            // 완료 버튼 클릭 시
            btnCompletetag.setOnClickListener {
                findNavController().apply {
                    // 추가된 태그를 포함하는 기록 데이터 보내기
                    Log.d("test","${recordData}")
                    previousBackStackEntry?.savedStateHandle?.set("addRecord", recordData)
                    popBackStack() // 이전 화면으로 이동
                }
            }

            // 3. 새로운 태그 생성 기능
            // 생성 버튼 클릭 시
            btnMaketag.setOnClickListener {
                // 중복 확인을 위한 변수
                var judge = false
                // 입력된 값이 없는 경우 Toast 출력
                if(etMaketag.text.isNotEmpty()) {
                    // 중복 태그 추가 제한하기
                    for(tag in recordData.tags) {
                        if(tag == etMaketag.text.toString()) {
                            Toast.makeText(requireContext(), "이미 존재하는 태그입니다.", Toast.LENGTH_SHORT).show()
                            judge = true
                            break
                        }
                    }
                    if(!judge) {
                        addDeleteTag(etMaketag.text.toString()) // 태그 생성
                        recordData.tags.add(etMaketag.text.toString())
                    }
                    // 입력된 값 초기화하기
                    etMaketag.setText("")
                } else { // 입력된 값이 없는 경우
                    Toast.makeText(requireContext(), "태그명을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            // 4. 최근 사용한 태그
            // 서버에서 최근 사용한 태그 정보 받아오기 받아서 화면에 표시
            setRecentTag()
        }
    }

    // 삭제 버튼이 있는 태그 추가하는 함수
    private fun addDeleteTag(tag : String) {
        binding.apply {
            // addtag_layout의 뷰를 설정
            var tagView = layoutInflater.inflate(R.layout.addtag_layout, null, false)

            // 태그뷰의 widget설정
            var tagTextView : TextView = tagView.findViewById(R.id.tv_addtag)
            var tagDeleteBtn : ImageView = tagView.findViewById(R.id.btn_addtagdelete)

            // tagTextView에 입력된 태그 추가
            tagTextView.setText(tag)
            loNewtag.addView(tagView)

            // 태그 삭제 기능
            tagDeleteBtn.setOnClickListener{
                recordData.tags.remove(tagTextView.text.toString())
                loNewtag.removeView(tagView)
            }
        }
    }

    // 최근 사용 태그 추가하는 함수
    private fun addRecentTag(tag : String) {
        binding.apply {
            // tag layout의 뷰를 설정
            val tagView: View = layoutInflater.inflate(R.layout.showtag_layout, null, false)
            // 태그뷰의 widget 설정
            var tagTextView: TextView = tagView.findViewById(R.id.showTagView)

            //tagTextView에 저장된 태그값 입력
            tagTextView.setText(tag)

            // 태그를 하나씩 추가하기
            loRecenttag.addView(tagView)
            // 최근 사용 태그 클릭 시 추가 기능
            tagTextView.setOnClickListener {
                // 중복 체크
                var judge = false
                for(tag in recordData.tags) {
                    if(tag == tagTextView.text.toString())
                    {
                        Toast.makeText(requireContext(), "이미 존재하는 태그입니다.", Toast.LENGTH_SHORT).show()
                        judge = true
                        break
                    }
                }
                if(!judge) { // 생성되지 않는 태그라면
                    addDeleteTag(tagTextView.text.toString())
                    recordData.tags.add(tagTextView.text.toString())
                    // 최근 태그 기록에서 클릭한 태그 삭제
                    recentTagList.remove(tagTextView.text.toString())
                    loRecenttag.removeView(tagView)
                }
            }
        }
    }

    // 최근 사용 태그 서버에서 받아오는 함수
    private fun setRecentTag() {
        val recordeInterface: RecordInterface? =
            RetrofitClient.getClient()?.create(RecordInterface::class.java)
        val call = recordeInterface?.getRecentTag("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk",0)
        call?.enqueue(object : retrofit2.Callback<RecentTag>{
            override fun onResponse(call: Call<RecentTag>, response: Response<RecentTag>) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if(body?.isSuccess == true) {
                        for(tag in body.result.content) {
                            recentTagList.add(tag.tag)
                            addRecentTag(tag.tag)
                        }
                    } else {
                        Log.d("test","${response.body()?.code}, ${response.body()?.message}")
                    }
                } else {
                    Log.d("test","Response Not Successful ${response.body()}")
                }
            }
            override fun onFailure(call: Call<RecentTag>, t: Throwable) {
                Log.d("test","Error!",t)
            }

        })
    }

}