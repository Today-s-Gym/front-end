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
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.RecordInterface
import umc.standard.todaygym.data.model.Record
import umc.standard.todaygym.data.model.RecordRequest
import umc.standard.todaygym.data.model.RecordResponse
import umc.standard.todaygym.data.model.Tag
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentAddrecordBinding
import java.text.DecimalFormat


class AddrecordFragment : Fragment() {
    private lateinit var binding: FragmentAddrecordBinding
    private lateinit var recordData: Record
    private var JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk"
    // 수정이면 1 / 추가면 0
    var check : Int = -1
    val df1 = DecimalFormat("00")
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
            val dateForm = "${recordData.date.year}.${df1.format(recordData.date.month)}.${df1.format(recordData.date.day)}"
            tvRecorddate.text = dateForm
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
                        addRecord(1, recordData.content)
                    } else {
                        addRecord(2, recordData.content)
                    }
                    findNavController().apply {
                        previousBackStackEntry?.savedStateHandle?.set("Calendar", recordData.date)
                        popBackStack() // 이전 화면으로 이동
                    }
                } else {
                    if(recordData.pictures.size == 0) {
                        Log.d("test","${recordData}")
                        addRecord(3, recordData.content)
                    } else {
                        addRecord(4, recordData.content)
                    }
                    findNavController().apply {
                        previousBackStackEntry?.savedStateHandle?.set("ShowRecord", recordData)
                        popBackStack() // 이전 화면으로 이동
                    }
                }
            }

            // 3. 태그 추가화면으로 넘어가는 버튼 기능 구현
            btnAddtag.setOnClickListener {
                recordData.content = etRecordcontent.text.toString()
                // 기존 태그 값 보내주기
                val bundle = Bundle()
                bundle.putSerializable("recordData", recordData)
                findNavController().navigate(R.id.action_addrecordFragment_to_addtagFragment, bundle)
            }
            // tag추가 화면에서 돌아왔을 때
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Record>("addRecord")?.observe(viewLifecycleOwner){
                recordData = it
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
                val picView = layoutInflater.inflate(R.layout.addpic_layout, null, false)
                // 사진 뷰의 widget 설정
                val picImageView : ImageView = picView.findViewById(R.id.iv_addpic)
                val picDeleteBtn : CircleImageView = picView.findViewById(R.id.btn_addpicdelete)
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
            val tagView = layoutInflater.inflate(R.layout.addtag_layout, null, false)

            // 태그뷰의 widget설정
            val tagTextView : TextView = tagView.findViewById(R.id.tv_addtag)
            val tagDeleteBtn : ImageView = tagView.findViewById(R.id.btn_addtagdelete)

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

    // 서버에 기록 추가 및 수정 함수
    private fun addRecord(method: Int, content: String) {
        val recordInterface: RecordInterface? =
            RetrofitClient.getClient()?.create(RecordInterface::class.java)
        val tags = arrayListOf<Tag>()
        for(tag in recordData.tags) {
            tags.add(Tag(tag))
        }
        val recordGetReq = RecordRequest(content,tags)
        var call : Call<RecordResponse>?
        when(method) {
            1 -> call = recordInterface?.addRecord2(JWT,recordGetReq) // 이미지 없는 기록 추가
            2 -> {
                val imgArray = arrayListOf<MultipartBody.Part>()
                for(img in recordData.pictures) {
                    // 이미지들 Multipart 형태로 바꿔주기
                }
                call = recordInterface?.addRecord2(JWT,recordGetReq) // 이미지 있는 기록 추가
            }
            3 -> call = recordInterface?.modifyRecord2(JWT,
                "${recordData.date.year}-${df1.format(recordData.date.month)}-${df1.format(recordData.date.day)}",
                recordGetReq) // 이미지 없는 기록 수정
            else -> {
                val imgArray = arrayListOf<MultipartBody.Part>()
                for(img in recordData.pictures) {
                    // 이미지들 Multipart 형태로 바꿔주기
                }
                call = recordInterface?.modifyRecord2(JWT,
                    "${recordData.date.year}-${df1.format(recordData.date.month)}-${df1.format(recordData.date.day)}",
                    recordGetReq) // 이미지 있는 기록 수정
            }
        }
        call?.enqueue(object : retrofit2.Callback<RecordResponse>{
            override fun onResponse(
                call: Call<RecordResponse>,
                response: Response<RecordResponse>
            ) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if(body?.isSuccess == true) {
                        Log.d("test","기록이 성공적으로 추가되었습니다.")
                    } else {
                        if(body?.code == 2401) {
                            Toast.makeText(requireContext(), body.message, Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("test", "${body?.message}")
                        }
                    }
                } else {
                    Log.w("test", "${response.code()}")
                }
            }
            override fun onFailure(call: Call<RecordResponse>, t: Throwable) {
                Log.d("test","Error!",t)
            }

        })
    }

}