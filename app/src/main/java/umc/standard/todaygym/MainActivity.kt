package umc.standard.todaygym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import umc.standard.todaygym.databinding.ActivityMainBinding
import umc.standard.todaygym.presentation.calendar.AddrecordFragment
import umc.standard.todaygym.presentation.calendar.AddtagFragment
import umc.standard.todaygym.presentation.calendar.ShowrecordFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initNavigation()
    }
    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initNavigation() {
        NavigationUI.setupWithNavController(binding.navBar, findNavController(R.id.nav_host))
    }
}