package umc.standard.todaygym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.standard.todaygym.databinding.ActivityMainBinding
import umc.standard.todaygym.util.CameraActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //카메라 테스트용 btn listener
//        binding.btnCamera.setOnClickListener {
//            val intent = Intent(this, CameraActivity::class.java)
//            startActivity(intent)
//        }


    }
}