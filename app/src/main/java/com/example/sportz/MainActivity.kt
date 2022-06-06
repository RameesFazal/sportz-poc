package com.example.sportz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.sportz.common.Resource
import com.example.sportz.presentation.SportsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        //Todo : This is to ensure data flow. Later will be changed into recycler view
        val tvName = findViewById<TextView>(R.id.tv_name)
        val mainViewModel by viewModels<SportsViewModel>()
        mainViewModel.fetchSportsList()
        mainViewModel.response.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    var names : String = ""
                    for (items in response.data!!){
                       names = names + " "+ items.name
                    }
                    tvName.text = names
                }
                is Resource.Error -> {
                    tvName.text = "Error"
                }
                is Resource.Loading -> {
                    tvName.text = "Loading"
                }
            }
        }
    }
}