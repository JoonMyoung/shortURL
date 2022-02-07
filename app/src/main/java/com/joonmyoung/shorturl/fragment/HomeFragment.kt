package com.joonmyoung.shorturl.fragment

import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.joonmyoung.shorturl.R
import com.joonmyoung.shorturl.databinding.FragmentHomeBinding
import com.joonmyoung.shorturl.retrofit.DataManager
import kotlinx.android.synthetic.main.fragment_home.*
import android.content.ClipData
import android.content.Context

import android.content.Context.CLIPBOARD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.room.Room
import com.joonmyoung.shorturl.Database.Appdatabase
import com.joonmyoung.shorturl.Database.History


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding : FragmentHomeBinding? = null
    private lateinit var dataManager : DataManager
    private lateinit var db : Appdatabase

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

        db = Appdatabase.getInstance(requireContext())!!



        var shortenBtn: Button = binding!!.shortenBtn
        shortenBtn.setOnClickListener { it ->

            //let 사용
            binding!!.enterUrl.text.toString().let { text ->
                if (text.isNotEmpty()) {
                    //람다 표현식으로 콜백 사용하기
                    dataManager.loadShorturl(
                        text,requireContext(),
                        {
                            binding!!.urlTextView.text = it.url
                            Glide.with(this).load(it.url+".qr").into(binding!!.QRImageView)

                            saveSearchKeyword(it.url,it.orgUrl)
                            hideKeyboard()

                        },
                        { _, t ->

                            Toast.makeText(context, "error: " + t.message, Toast.LENGTH_SHORT).show()
                        })
                }
            }
        }

        binding!!.textClearBtn.setOnClickListener {
            binding!!.enterUrl.text?.clear()
        }

        binding!!.copyBtn.setOnClickListener {
            val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            if (binding!!.urlTextView.text.toString()== "주소"){
                return@setOnClickListener
            }
            val clip = ClipData.newPlainText("URL","${binding!!.urlTextView.text}")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "복사되었습니다", Toast.LENGTH_SHORT).show()
        }

    }

    private fun saveSearchKeyword(keyword:String,orgUrl:String){
        Thread {
            db.historyDao().insertHistory(History(null,keyword,orgUrl,"0"))
        }.start()
    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            // 프래그먼트기 때문에 getActivity() 사용
            val inputManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }



}