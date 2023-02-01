package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.prolificinteractive.materialcalendarview.CalendarDay
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.RecordInterface
import umc.standard.todaygym.data.mdoel.AddRecordRequest1
import umc.standard.todaygym.data.mdoel.AddRecordResponse
import umc.standard.todaygym.data.mdoel.Record
import umc.standard.todaygym.data.mdoel.Tag
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddrecordBinding


class AddrecordFragment : Fragment() {
    private lateinit var binding: FragmentAddrecordBinding
    private lateinit var recordData: Record
    private var JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk"
    // 수정이면 1 / 추가면 0
    var check : Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddrecordBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            check = arguments?.getInt("check") as Int

            // 1. 넘겨받은 기록 정보 변수 설정
            // 기록 없는 날일 경우
            if(check == 0) {
                recordData = Record(
                    arguments?.getParcelable<CalendarDay>("recordDate") as CalendarDay, "", arrayListOf(), arrayListOf())
            } else {
                recordData = arguments?.getSerializable("recordData") as Record
            }

            // 서버에서 사용자 정보 받아서 넣기
            tvUsernickname.text = "벡스"
            ivUseravarta.setImageResource(R.drawable.logo)

            // 2. 상단바 기능 구현
            // 상단 날짜 표시
            tvRecorddate.text = "${recordData.date.year}.${recordData.date.month}.${recordData.date.day}"
            // 뒤로가기 버튼
            btnBack.setOnClickListener {
                // 이전 화면으로 전환
                findNavController().popBackStack()
            }

            // 완료 버튼
            if(check == 1) {
                // 상단바 버튼 수정으로 변경
                btnCompleterecord.text = "수정"
            } else { // 추가화면일 경우
                // 상단바 버튼 작성으로 변경
                btnCompleterecord.text = "작성"
            }
            btnCompleterecord.setOnClickListener {
                recordData.content = etRecordcontent.text.toString()
                if(check == 0) {
                    if(recordData.pictures.size == 0) {
                        addRecord(0, etRecordcontent.text.toString())
                    } else {
                        addRecord(1, etRecordcontent.text.toString())
                    }
                    findNavController().apply {
                        previousBackStackEntry?.savedStateHandle?.set("Calendar", recordData.date)
                        popBackStack() // 이전 화면으로 이동
                    }
                } else {
                    findNavController().apply {
                        recordData.content = etRecordcontent.text.toString()
                        previousBackStackEntry?.savedStateHandle?.set("ShowRecord", recordData)
                        popBackStack() // 이전 화면으로 이동
                    }
                }
            }

            // 3. 태그 추가화면으로 넘어가는 버튼 기능 구현
            btnAddtag.setOnClickListener {
                // 기존 태그 값 보내주기
                val bundle = Bundle()
                bundle.putSerializable("recordData", recordData)
                findNavController().navigate(R.id.action_addrecordFragment_to_addtagFragment, bundle)
            }

            // tag추가 화면에서 돌아왔을 때
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Record>("addRecord")?.observe(viewLifecycleOwner){
                recordData = it
                putData()
            }
            // 4. 기록 데이터 값 입력
            putData()

            // 5. 사진 업로드 기능

        }

    }
    // 데이터 넣어주는 함수
    private fun putData() {
        binding.apply {
            // 사진 개수가 3개이면 사진 추가 버튼 없애기
            if(recordData.pictures.size == 3) btnAddpicture.visibility = View.GONE
            // 기존 사진 넣기
            for(pic in recordData.pictures) {
                // addpic_layout의 뷰를 설정
                var picView = layoutInflater.inflate(R.layout.addpic_layout, null, false)
                // 사진 뷰의 widget 설정
                var picImageView : ImageView = picView.findViewById(R.id.iv_addpic)
                var picDeleteBtn : CircleImageView = picView.findViewById(R.id.btn_addpicdelete)
                // picImageView에 저장된 사진을 입력
                Glide.with(this@AddrecordFragment).load(pic).into(picImageView)
                // 사진을 하나씩 추가
                loAddpic.addView(picView)
                // 사진 삭제 기능
                picDeleteBtn.setOnClickListener {
                    loAddpic.removeView(picView)
                    if(recordData.pictures.size == 3) {
                        // 사진이 3개면 사진 추가 버튼을 다시 보이게 하기
                        btnAddpicture.visibility = View.VISIBLE
                    }
                    recordData.pictures.remove(pic)
                }
            }

            // 기존 글 내용 넣기
            if(recordData.content != "") {
                etRecordcontent.setText(recordData.content)
            }

            // 기존 태그 넣기
            for(tag in recordData.tags) {
                addDeleteTag(tag)
            }
        }
    }

    // 삭제 버튼이 있는 태그 생성 함수
    private fun addDeleteTag(tag: String) {
        binding.apply {
            //addtag_layout의 뷰를 설정
            var tagView = layoutInflater.inflate(R.layout.addtag_layout, null, false)

            // 태그뷰의 widget설정
            var tagTextView : TextView = tagView.findViewById(R.id.tv_addtag)
            var tagDeleteBtn : ImageView = tagView.findViewById(R.id.btn_addtagdelete)

            // tagTextView에 저장된 태그값을 입력
            tagTextView.setText(tag)

            // 태그를 하나씩 추가
            loAddtag.addView(tagView)
            // 태그 삭제 기능
            tagDeleteBtn.setOnClickListener{
                loAddtag.removeView(tagView)
                recordData.tags.remove(tag)
            }
        }
    }

    // 서버에 기록 추가 함수
    private fun addRecord(check : Int, content: String) {
        val recordInterface: RecordInterface? =
            RetrofitClient.getClient()?.create(RecordInterface::class.java)
        var tags = arrayListOf<Tag>()
        for(tag in recordData.tags) {
            tags.add(Tag(tag))
        }
        val recordGetReq = AddRecordRequest1(content,tags)
        val call = recordInterface?.addRecord2(JWT, recordGetReq)
        call?.enqueue(object : retrofit2.Callback<AddRecordResponse>{
            override fun onResponse(
                call: Call<AddRecordResponse>,
                response: Response<AddRecordResponse>
            ) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if(body?.isSuccess == true) {
                        // Toast.makeText(requireContext(), "기록을 추가하였습니다", Toast.LENGTH_SHORT).show()
                    } else {
                        if(body?.code == 2401) {
                            Toast.makeText(requireContext(), "${body?.message}", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("test", "${body?.message}")
                        }
                    }
                } else {
                    Log.w("test", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<AddRecordResponse>, t: Throwable) {
                Log.d("test","Error!",t)
            }

        })
    }
}