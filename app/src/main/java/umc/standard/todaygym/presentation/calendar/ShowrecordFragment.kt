package umc.standard.todaygym.presentation.calendar

import android.app.Dialog
import android.os.Bundle
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
import umc.standard.todaygym.R
import umc.standard.todaygym.data.mdoel.Record
import umc.standard.todaygym.databinding.FragmentShowrecordBinding

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
            // 서버에서 기록 정보 받기!!!!!!!!!!!
            recordData = Record(CalendarDay.from(arguments?.getInt("recordYear") as Int,
                arguments?.getInt("recordMonth") as Int,
                arguments?.getInt("recordDay") as Int),
                "하핫 1일차다", arrayListOf(R.drawable.add_black, R.drawable.add_gray), arrayListOf("asdfsadfasdfdsafdsasdaf","asdfasdfasdfdsfsdaff","태dfdf"))

            // 서버에서 사용자 정보 넣기
            tvUsernickname.text = "벡스"
            ivUseravarta.setImageResource(R.drawable.logo)

            // 기록 날짜 넣기
            tvRecorddate.text = "${recordData.date.year}.${recordData.date.month}.${recordData.date.day}"

            // 날짜를 통해 데이터 받아오기
            // 1. 사진 데이터 viewpager2에 적용하기
            if(recordData.pictures.size != 0) {
                loRecordpic.visibility = View.VISIBLE
                initViewPager2()
            }
            else {
                loRecordpic.visibility = View.GONE
            }

            // 2. 기록 내용 textview에 적용하기
            tvRecordcontent.text = recordData.content
            // 3. 태그 데이터 적용하기
            for(tag in recordData.tags) {
                // tag layout의 뷰를 설정
                val tagView: View = layoutInflater.inflate(R.layout.showtag_layout, null, false)
                // 태그뷰의 widget 설정
                var tagTextView: TextView = tagView.findViewById(R.id.showTagView)

                //tagTextView에 listData의 getmTagArray의 텍스트를 입력
                tagTextView.setText(tag)

                // 태그를 하나씩 추가하기
                loTag.addView(tagView)
            }
            // 상단바 기능 적용
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            // 다이얼로그 커스텀
            dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_deleterecord)

            btnDeleterecord.setOnClickListener {
                // 커스텀 다이얼로그 띄우기
                showDialog(); // 아래 showDialog01() 함수 호출
            }
            btnModifyrecord.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("recordYear",recordData.date.year)
                bundle.putInt("recordMonth",recordData.date.month)
                bundle.putInt("recordDay",recordData.date.day)
                bundle.putInt("check",1)
                findNavController().navigate(R.id.addrecordFragment, bundle)
            }
        }
    }

    private fun initViewPager2() {
        binding.apply {
            tvCurrentpic.text = "1/${recordData.pictures.size}"
            vpRecordPic.apply {
                adapter = RecordPictureAdapter(requireActivity(), recordData.pictures)
                setPageTransformer(MarginPageTransformer(100))
            }
            viewChangeEvent()
        }

    }
    private fun viewChangeEvent(){
        binding.vpRecordPic.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvCurrentpic.text = "${position+1}/${recordData.pictures.size}"
            }
        })
    }

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
            findNavController().popBackStack()
        }
    }
}