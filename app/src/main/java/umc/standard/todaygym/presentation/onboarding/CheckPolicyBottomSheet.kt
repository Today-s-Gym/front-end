//package umc.standard.todaygym.presentation.onboarding
//
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import umc.standard.todaygym.R
//import umc.standard.todaygym.databinding.BottomsheetCheckPolicyBinding
//
//class CheckPolicyBottomSheet() :BottomSheetDialogFragment(){
//    private lateinit var binding : BottomsheetCheckPolicyBinding
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View?
//    {
//        super.onCreateView(inflater, container, savedInstanceState)
//        binding = BottomsheetCheckPolicyBinding.inflate(inflater, container,false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Log.d("bottomSheet","whwywhy")
//        var policyGlobal:Boolean = false
//        var policyPersonal:Boolean = false
//        var policyMarketing:Boolean = false
//        super.onViewCreated(view, savedInstanceState)
//        // all check
//        binding.btnPolicyAllCheck.setOnClickListener{
//            it.setBackgroundResource(R.drawable.border_account_primary)
//            binding.btnPolicyGlobal.setBackgroundResource(R.drawable.border_account_primary)
//            binding.btnPolicyPersonal.setBackgroundResource(R.drawable.border_account_primary)
//            binding.btnPolicyMarketing.setBackgroundResource(R.drawable.border_account_primary)
//            policyGlobal = true
//            policyPersonal = true
//            policyMarketing = true
//        }
//        //individual check
//        binding.btnPolicyGlobal.setOnClickListener{
//            it.setBackgroundResource(R.drawable.border_account_primary)
//            policyGlobal = true
//
//        }
//        binding.btnPolicyPersonal.setOnClickListener{
//            it.setBackgroundResource(R.drawable.border_account_primary)
//            policyPersonal = true
//
//        }
//        binding.btnPolicyMarketing.setOnClickListener{
//            it.setBackgroundResource(R.drawable.border_account_primary)
//            policyMarketing = true
//        }
//        binding.btnPolicyNext.setOnClickListener{
//            if(policyGlobal && policyPersonal){
//                dismiss()
//            }
//        }
//    }
//}