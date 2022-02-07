package com.joonmyoung.shorturl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.joonmyoung.shorturl.fragment.HomeFragment
import com.joonmyoung.shorturl.fragment.ListFragment
import com.joonmyoung.shorturl.retrofit.DataManager

class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val listFragment = ListFragment()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        replaceFragment(homeFragment)


        // 1. 모바일광고 SDK 초기화
         MobileAds.initialize(this) {}
        // 2. 광고 띄우기
         mAdView = findViewById(R.id.adViewBanner)
         val adRequest = AdRequest.Builder().build()
         mAdView.loadAd(adRequest)



        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.list -> replaceFragment(listFragment)

            }
            true
        }
    }



    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }



    }

}

object NaverConsts {

    const val CLIENT_ID = "BJpqfIGpuU1V7HjiyOCY"
    const val CLIENT_SECRET = "2qyZMQALDO"

}