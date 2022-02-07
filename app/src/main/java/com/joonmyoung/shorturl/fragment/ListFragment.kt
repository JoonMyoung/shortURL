package com.joonmyoung.shorturl.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.joonmyoung.shorturl.Database.Appdatabase
import com.joonmyoung.shorturl.Database.BookMarkAdapter
import com.joonmyoung.shorturl.Database.History
import com.joonmyoung.shorturl.Database.HistoryAdapter
import com.joonmyoung.shorturl.R
import com.joonmyoung.shorturl.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var bookMarkAdapter: BookMarkAdapter
    private lateinit var db: Appdatabase
    private val historyList = mutableListOf<History>()
    private val bookMarkList = mutableListOf<History>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentListBinding = FragmentListBinding.bind(view)
        binding = fragmentListBinding

        db = Appdatabase.getInstance(requireContext())!!


        initHistoryRecyclerView()


    }


    private fun initHistoryRecyclerView() {

        Thread(Runnable

        {

            val data = db.historyDao().getAll().reversed().toMutableList()
            historyList.clear()
            bookMarkList.clear()

            try {
                for (i in data){
                    if (i.bookMark=="0"){
                        historyList.add(i)
                    }
                    if (i.bookMark=="1"){
                        bookMarkList.add(i)
                    }
                }
            } catch (e:Exception){

            }




        }).start()


        activity?.runOnUiThread {

            historyAdapter =
                HistoryAdapter(historyDeleteClickedListener = { deleteSearchKeyWord(it) },
                    historyBookMarkClickedListener = {saveBookMarkKeyword(it)},
                    historyList,requireContext(),requireActivity())
            binding.historyRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.historyRecyclerView.adapter = historyAdapter

            historyAdapter.notifyDataSetChanged()

            bookMarkAdapter =
                BookMarkAdapter(historyDeleteClickedListener = { deleteSearchKeyWord(it) },
                    historyBookMarkClickedListener = {saveBookMarkKeyword(it)},
                    bookMarkList,requireContext(),requireActivity())
            binding.bookMarkRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.bookMarkRecyclerView.adapter = bookMarkAdapter

            bookMarkAdapter.notifyDataSetChanged()
        }

    }

    private fun deleteSearchKeyWord(id: String) {
        Thread(Runnable {
            db.historyDao().delete(id)
            // view 갱신
            activity?.runOnUiThread {
                initHistoryRecyclerView()
            }
        }).start()

    }

    private fun saveBookMarkKeyword(history: History){

        deleteSearchKeyWord(history.id.toString())

        Thread {
            db.historyDao().insertHistory(History(null,history.Keyword,history.orgUrl,history.bookMark))

            activity?.runOnUiThread {
                initHistoryRecyclerView()
            }

        }.start()

    }



}