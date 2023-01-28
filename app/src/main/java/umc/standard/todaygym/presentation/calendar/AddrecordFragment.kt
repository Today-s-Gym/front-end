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
import de.hdodenhof.circleimageview.CircleImageView
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
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            check = arguments?.getInt("check") as Int

            // 1. 서버에서 사용자 및 기록 정보 받기
            // 기록 없는 날일 경우
            if(check == 0) {
                recordData = Record(
                    CalendarDay.from(arguments?.getInt("recordYear") as Int,
                        arguments?.getInt("recordMonth") as Int,
                        arguments?.getInt("recordDay") as Int), "", arrayListOf(), arrayListOf())
            } else { // 서버에서 기록 정보 받아오기
                recordData = Record(
                    CalendarDay.from(arguments?.getInt("recordYear") as Int,
                        arguments?.getInt("recordMonth") as Int,
                        arguments?.getInt("recordDay") as Int),
                    "", arrayListOf(R.drawable.add_black, R.drawable.add_gray, R.drawable.logo), arrayListOf("앞차기","이단옆차기","찍기","정권지르기"))
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
            btnCompleterecord.setOnClickListener {
                // 서버에 변경된 내용 적용하기

                // 이전 화면으로 전환
                findNavController().popBackStack()
            }

            // 3. 기록 수정일 경우 기존 데이터 값 입력
            // 수정화면일 경우
            if(check == 1) {
                // 상단바 버튼 수정으로 변경
                btnCompleterecord.text = "수정"

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
                    picImageView.setImageResource(pic)
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
                etRecordcontent.setText(recordData.content)

                // 기존 태그 넣기
                for(tag in recordData.tags) {
                    addDeleteTag(tag)
                }
            }
            else { // 추가화면일 경우
                // 상단바 버튼 작성으로 변경
                btnCompleterecord.text = "작성"
            }

            // 4. 태그 추가화면으로 넘어가는 버튼 기능 구현
            btnAddtag.setOnClickListener {
                findNavController().navigate(R.id.addtagFragment)
            }

            // 5. 사진 업로드 기능

        }

        // tag추가 화면에서 돌아왔을 때
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ArrayList<String>>("tag")?.observe(viewLifecycleOwner){
            for(newtag in it) {
                addDeleteTag(newtag)
                recordData.tags.add(newtag)
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
                Log.d("test","${recordData.tags}")
            }
        }
    }
}