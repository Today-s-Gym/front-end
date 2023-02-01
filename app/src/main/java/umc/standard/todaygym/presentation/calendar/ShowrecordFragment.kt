package umc.standard.todaygym.presentation.calendar

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Response
import retrofit2.create
import umc.standard.todaygym.R
import umc.standard.todaygym.data.api.RecordInterface
import umc.standard.todaygym.data.mdoel.DeleteRecord
import umc.standard.todaygym.data.mdoel.Record
import umc.standard.todaygym.data.mdoel.RecordByDay
import umc.standard.todaygym.data.util.RetrofitClient
import umc.standard.todaygym.databinding.FragmentShowrecordBinding
import java.text.DecimalFormat

class ShowrecordFragment : Fragment() {
    private lateinit var binding: FragmentShowrecordBinding
    private lateinit var recordData: Record
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowrecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            // 1. 넘겨받은 값 변수에 넣기
            recordData = arguments?.getSerializable("recordData") as Record

            // 사용자 정보 넣기
            tvUsernickname.text = "벡스"
            ivUseravarta.setImageResource(R.drawable.logo)

            // 2. 상단바 기능 구현
            // 뒤로가기 버튼
            btnBack.setOnClickListener {
                // 이전 화면으로 전환
                findNavController().apply {
                    previousBackStackEntry?.savedStateHandle?.set("Calendar", recordData.date)
                    popBackStack() // 이전 화면으로 이동
                }
            }
            // 삭제버튼 클릭에 대한 다이얼로그 커스텀
            dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_deleterecord)

            // 삭제 버튼
            btnDeleterecord.setOnClickListener {
                // 커스텀 다이얼로그 띄우기
                showDialog(); // 아래 showDialog01() 함수 호출
            }
            // 수정 버튼
            btnModifyrecord.setOnClickListener {
                // 수정할 날짜 정보 넘기기
                val bundle = Bundle()
                bundle.putSerializable("recordData",recordData)
                bundle.putInt("check",1)
                findNavController().navigate(R.id.action_showrecordFragment_to_addrecordFragment, bundle) // 기록수정 화면으로 전환
            }
            // 이 화면으로 돌아왔을 때 수행할 것
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Record>("ShowRecord")?.observe(viewLifecycleOwner) {
                recordData = it
                putData()
            }

            // 3. 기록 날짜 입력
            tvRecorddate.text = "${recordData.date.year}.${recordData.date.month}.${recordData.date.day}"

            // 4. 기록 데이터 적용하기
            putData()
        }
    }

    // 기록 데이터 대입 함수
    private fun putData() {
        binding.apply {
            // 사진 데이터 viewpager2에 적용하기
            if(recordData.pictures.size != 0) {
                loRecordpic.visibility = View.VISIBLE
                initViewPager2()
            }
            else {
                loRecordpic.visibility = View.GONE
            }

            // 기록 내용 textview에 적용하기
            tvRecordcontent.text = recordData.content
            // 태그 데이터 적용하기
            for(tag in recordData.tags) {
                // tag layout의 뷰를 설정
                val tagView: View = layoutInflater.inflate(R.layout.showtag_layout, null, false)
                // 태그뷰의 widget 설정
                var tagTextView: TextView = tagView.findViewById(R.id.showTagView)

                //tagTextView에 저장된 태그값 입력
                tagTextView.setText(tag)

                // 태그를 하나씩 추가하기
                loTag.addView(tagView)
            }
        }
    }

    // 사진 정보 입력 함수
    private fun initViewPager2() {
        binding.apply {
            tvCurrentpic.text = "1/${recordData.pictures.size}" // 현재 사진 위치 보이기
            vpRecordPic.apply {
                // viewpager2 어댑터 객체 생성
                adapter = RecordPictureAdapter(requireActivity(), recordData.pictures)
                setPageTransformer(MarginPageTransformer(100))
            }
            viewChangeEvent() // 사진 전환 시
        }
    }
    // 사진 전환 함수
    private fun viewChangeEvent(){
        binding.vpRecordPic.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvCurrentpic.text = "${position+1}/${recordData.pictures.size}" // 현재 사진 위치 보이기
            }
        })
    }

    // Dialog 기능 함수
    private fun showDialog() {
        dialog.show() // 다이얼로그 띄우기

        // 아니오 버튼
        var noBtn : Button = dialog.findViewById(R.id.btn_no)
        noBtn.setOnClickListener {
            // 다이얼로그 닫기
            dialog.dismiss()
        }
        var yesBtn : Button = dialog.findViewById(R.id.btn_yes)
        yesBtn.setOnClickListener {
            // 서버에서 해당 기록 삭제
            // CalendarFragment로 이동
            dialog.dismiss()
            val df1 = DecimalFormat("00")
            deleteRecord("${recordData.date.year}-${df1.format(recordData.date.month)}-${df1.format(recordData.date.day)}")
            findNavController().apply {
                previousBackStackEntry?.savedStateHandle?.set("Calendar", recordData.date)
                popBackStack() // 이전 화면으로 이동
            }
        }
    }

    // 서버에서 기록 삭제 함수
    private fun deleteRecord(date: String) {
        val recordInterface: RecordInterface? =
            RetrofitClient.getClient()?.create(RecordInterface::class.java)
        val call = recordInterface?.deleteRecord("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjc0OTY5MzY4LCJleHAiOjE3MDY1MDUzNjh9.wME-N31YIrjAtr7Y1usIIQZwG_cHZcmZqB8hBtgq5lk", "${date}")
        call?.enqueue(object : retrofit2.Callback<DeleteRecord>{
            override fun onResponse(call: Call<DeleteRecord>, response: Response<DeleteRecord>) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if(body?.isSuccess == false) {
                        Log.d("test","${body?.message}")
                    }
                } else {
                    Log.d("test","Response Not Successful ${response.body()}")
                }
            }
            override fun onFailure(call: Call<DeleteRecord>, t: Throwable) {
                Log.d("test","Error!",t)
            }
        })
    }
}