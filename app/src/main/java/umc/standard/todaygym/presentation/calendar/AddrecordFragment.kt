package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import umc.standard.todaygym.R
import umc.standard.todaygym.data.mdoel.Record
import umc.standard.todaygym.databinding.FragmentAddrecordBinding


class AddrecordFragment : Fragment() {
    private lateinit var binding: FragmentAddrecordBinding
    private lateinit var recordData: Record
    // 수정이면 1 / 추가면 0
    var check : Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddrecordBinding.inflate(layoutInflater)
        return binding.root

        // 3. 사진 업로드 기능 넣기
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // 기록 날짜 전달받은 값 화면에 출력
            recordData = Record(
                CalendarDay.from(arguments?.getInt("recordYear") as Int,
                    arguments?.getInt("recordMonth") as Int,
                    arguments?.getInt("recordDay") as Int),
                "하핫 1일차다", arrayListOf(R.drawable.add_black, R.drawable.add_gray), arrayListOf("앞차기","이단옆차기","찍기","정권지르기"))
            check = arguments?.getInt("check") as Int
            tvRecorddate.text = "${recordData.date.year}.${recordData.date.month}.${recordData.date.day}"

            // 서버에서 사용자 정보 넣기
            tvUsernickname.text = "벡스"
            ivUseravarta.setImageResource(R.drawable.logo)

            // 수정화면일 경우
            if(check == 1) {

                // 상단바 버튼 수정으로 변경
                btnCompleterecord.text = "수정"
                // 기존 사진 넣기

                // 기존 글 내용 넣기
                etRecordcontent.setText(recordData.content)
                // 기존 태그 넣기
                for(tag in recordData.tags) {
                    //addtag_layout의 뷰를 설정
                    var tagView = layoutInflater.inflate(R.layout.addtag_layout, null, false)

                    // 태그뷰의 widget설정
                    var tagTextView : TextView = tagView.findViewById(R.id.tv_addtag)
                    // var tagWrap : LinearLayout = tagView.findViewById(R.id.lo_addtagWrap)
                    var tagDeleteBtn : ImageView = tagView.findViewById(R.id.btn_addtagdelete)

                    // tagTextView에 listData의 getmTagArray의 텍스트를 입력
                    tagTextView.setText(tag)

                    // 태그를 하나씩 추가
                    loAddtag.addView(tagView)
                    // 태그 삭제 기능
                    tagDeleteBtn.setOnClickListener{
                        loAddtag.removeView(tagView)
                        recordData.tags.remove(tag)
                        Log.d("test","${recordData.tags}")
                    }
                }
            }
            else { // 추가화면일 경우
                // 상단바 버튼 작성으로 변경
                btnCompleterecord.text = "작성"
            }
            // 뒤로가기 버튼 클릭 시 이전 화면으로
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            // 완료 버튼 클릭 시 CalendarFragment로 전환
            btnCompleterecord.setOnClickListener {
                findNavController().popBackStack()
            }
            btnAddtag.setOnClickListener {
                findNavController().navigate(R.id.addtagFragment)
            }
        }

        // tag추가 화면에서 돌아왔을 때
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ArrayList<String>>("tag")?.observe(viewLifecycleOwner){
            for(newtag in it) {
                recordData.tags.add(newtag)
            }
        }
    }
}