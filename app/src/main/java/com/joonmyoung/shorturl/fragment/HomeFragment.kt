package com.joonmyoung.shorturl.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.joonmyoung.shorturl.R
import com.joonmyoung.shorturl.databinding.FragmentHomeBinding
import com.joonmyoung.shorturl.retrofit.DataManager


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding : FragmentHomeBinding? = null
    private lateinit var dataManager : DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        dataManager = DataManager()

        var shortenBtn: Button = binding!!.shortenBtn
        shortenBtn.setOnClickListener { it ->
            //람다 표현식
            //let 사용
            binding!!.enterUrl.text.toString().let { text ->
                if (text.isNotEmpty()) {
                    //람다 표현식으로 콜백 사용하기
                    dataManager.loadShorturl(
                        text,
                        {
                            Toast.makeText(context, it.url, Toast.LENGTH_SHORT).show()
//                            share(it.url)
                        },
                        { _, t ->
                            Toast.makeText(context, "error: " + t.message, Toast.LENGTH_SHORT).show()
                        })
                }
            }
        }

    }




}