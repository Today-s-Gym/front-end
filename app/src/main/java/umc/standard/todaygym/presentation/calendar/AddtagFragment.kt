package umc.standard.todaygym.presentation.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentAddtagBinding

class AddtagFragment : Fragment() {

    private lateinit var binding: FragmentAddtagBinding
    var tagList = arrayListOf<String>()
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

        // 1. 서버에서 최근 사용한 태그 정보 받기
        recentTagList.apply {
            add("벡스")
            add("김준석")
            add("인하대")
            add("컴퓨터공학과")
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
                    previousBackStackEntry?.savedStateHandle?.set("tag", tagList) // 추가된 태그값 전달
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
                    for(tag in tagList) {
                        if(tag == etMaketag.text.toString()) {
                            Toast.makeText(requireContext(), "이미 존재하는 태그입니다.", Toast.LENGTH_SHORT).show()
                            judge = true
                            break
                        }
                    }
                    if(!judge) {
                        addDeleteTag(etMaketag.text.toString()) // 태그 생성
                    }
                    // 입력된 값 초기화하기
                    etMaketag.setText("")
                } else { // 입력된 값이 없는 경우
                    Toast.makeText(requireContext(), "태그명을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            // 4. 최근 사용한 태그 뷰 생성
            for(recentTag in recentTagList) {
                // tag layout의 뷰를 설정
                val tagView: View = layoutInflater.inflate(R.layout.showtag_layout, null, false)
                // 태그뷰의 widget 설정
                var tagTextView: TextView = tagView.findViewById(R.id.showTagView)

                //tagTextView에 저장된 태그값 입력
                tagTextView.setText(recentTag)

                // 태그를 하나씩 추가하기
                loRecenttag.addView(tagView)
                // 최근 사용 태그 클릭 시 추가 기능
                tagTextView.setOnClickListener {
                    // 중복 체크
                    var judge = false
                    for(tag in tagList) {
                        if(tag == tagTextView.text.toString())
                        {
                            Toast.makeText(requireContext(), "이미 존재하는 태그입니다.", Toast.LENGTH_SHORT).show()
                            judge = true
                            break
                        }
                    }
                    if(!judge) { // 생성되지 않는 태그라면
                        addDeleteTag(tagTextView.text.toString())
                        // 최근 태그 기록에서 클릭한 태그 삭제
                        recentTagList.remove(tagTextView.text.toString())
                        loRecenttag.removeView(tagView)
                    }
                }
            }
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

            // tagTextView에 입력된 태그 추가 후 입력창 초기화
            tagTextView.setText(tag)
            loNewtag.addView(tagView)
            tagList.add(tag)

            // 태그 삭제 기능
            tagDeleteBtn.setOnClickListener{
                tagList.remove(tagTextView.text.toString())
                loNewtag.removeView(tagView)
            }
        }
    }

}