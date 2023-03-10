package umc.standard.todaygym.presentation.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController

import androidx.navigation.fragment.findNavController
import umc.standard.todaygym.R
import umc.standard.todaygym.databinding.FragmentCommunityBinding

class CommunityFragment: Fragment() {
    private lateinit var bindng : FragmentCommunityBinding
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindng = FragmentCommunityBinding.inflate(layoutInflater)

        clickCategory()

        return bindng.root
    }

    private fun clickCategory(){
        bindng.apply {
            bundle= Bundle()
            imgHealth.setOnClickListener {
                navCategory("헬스",1)
            }
            imgCrossfit.setOnClickListener {
                navCategory("크로스핏",2)
            }
            imgClimbing.setOnClickListener {
                navCategory("클라이밍",3)
            }
            imgKarate.setOnClickListener {
                navCategory("가라테",4)
            }
            imgTaekwondo.setOnClickListener {
                navCategory("태권도",5)
            }
            imgMuaythai.setOnClickListener {
                navCategory("무에타이",6)
            }
            imgJiujitsu.setOnClickListener {
                navCategory("주짓수",7)
            }
            imgJeetkunedo.setOnClickListener {
                navCategory("절권도",8)
            }
            imgWushu.setOnClickListener {
                navCategory("우슈",9)
            }
            imgKudo.setOnClickListener {
                navCategory("쿠도",10)
            }
            imgProwrestling.setOnClickListener {
                navCategory("프로레슬링",11)
            }
            imgKickboxing.setOnClickListener {
                navCategory("킥복싱",12)
            }
            imgKendo.setOnClickListener {
                navCategory("검도",13)
            }
        }

    }

    private fun navCategory(category:String,id:Int){
        bundle.putString("category",category)
        bundle.putInt("id",id)
        findNavController().navigate(R.id.action_communityFragment_to_boardFragment,bundle)

    }




}

