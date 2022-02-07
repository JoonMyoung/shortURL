package com.joonmyoung.shorturl.Database

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joonmyoung.shorturl.R
import kotlinx.android.synthetic.main.item_history.view.*

class BookMarkAdapter(val historyDeleteClickedListener: (String) -> Unit,
                     val historyBookMarkClickedListener : (History) -> Unit,
                     private val dataList:MutableList<History>,
                     val context : Context,
                     private val activity: Activity
)
    : RecyclerView.Adapter<BookMarkAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_history,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookMarkAdapter.ViewHolder, position: Int) {


        holder.itemView.historyKeywordDeleteButton.setOnClickListener {

            historyDeleteClickedListener(dataList[position].id.toString())

        }
        holder.itemView.bookMarkBtn.setOnClickListener {

            if (dataList[position].bookMark=="1"){

                dataList[position].bookMark = "0"
                historyBookMarkClickedListener(dataList[position])

            }else{

                dataList[position].bookMark = "1"
                historyBookMarkClickedListener(dataList[position])

            }


        }

        if (dataList[position].bookMark=="1"){
            holder.itemView.bookMarkBtn.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }else {
            holder.itemView.bookMarkBtn.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
        }



        holder.itemView.historyKeywordTextView.text = dataList[position].Keyword
        holder.itemView.historyOrgUrlTextView.text = dataList[position].orgUrl

        holder.itemView.setOnClickListener {
            showDialog(position, holder)
        }



    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun showDialog(position: Int, holder: ViewHolder) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_history, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)

        val alertDialog = mBuilder.show()

        val keyword = alertDialog.findViewById<TextView>(R.id.dialog_keyword)
        val orgUrl = alertDialog.findViewById<TextView>(R.id.dialog_orgUrl)
        val qrCode = alertDialog.findViewById<ImageView>(R.id.dialog_imageView)


        keyword?.text = dataList[position].Keyword
        orgUrl?.text = dataList[position].orgUrl

        Glide.with(context).load(dataList[position].Keyword+".qr").into(qrCode!!)



        alertDialog.findViewById<Button>(R.id.closeBtn)?.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.findViewById<Button>(R.id.keywordCopyBtn)?.setOnClickListener {

            val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("URL",dataList[position].Keyword)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, "복사되었습니다", Toast.LENGTH_SHORT).show()
        }

        alertDialog.findViewById<Button>(R.id.orgUrlCopyBtn)?.setOnClickListener {

            val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("URL",dataList[position].orgUrl)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, "복사되었습니다", Toast.LENGTH_SHORT).show()
        }


    }

}