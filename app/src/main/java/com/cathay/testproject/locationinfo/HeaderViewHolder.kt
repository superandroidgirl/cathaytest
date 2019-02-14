package com.cathay.testproject.locationinfo

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cathay.testproject.GlideApp
import com.cathay.testproject.R
import com.cathay.testproject.commonutils.GlideRoundTransform
import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListResultX

class HeaderViewHolder(rootView : View, val listner : LocationInfoFragment.OnItemClickListener) : RecyclerView.ViewHolder(rootView) {

    private val mInfoImg = rootView.findViewById<ImageView>(R.id.info_img)
    private val mInfoTextView = rootView.findViewById<TextView>(R.id.info_text)
    private val mInfoMemoTextView = rootView.findViewById<TextView>(R.id.memo_text)
    private val mInfoCategoryTextView = rootView.findViewById<TextView>(R.id.category_text)
    private val mOpenWebview  = rootView.findViewById<TextView>(R.id.open_in_webview)


    fun refreshItem(item : CategoryListResultX) {
        if (item == null) return

        mInfoTextView.text = item.E_Info
        mInfoMemoTextView.text = item.E_Memo
        mInfoCategoryTextView.text = item.E_Category

        GlideApp.with(itemView.context)
            .load(item.E_Pic_URL)
            .fitCenter()
            .placeholder(R.drawable.default_img)
            .transform(GlideRoundTransform(2))
            .centerCrop()
            .into(mInfoImg)

        if (item.E_URL != null && item.E_URL.isNotEmpty()) {
            mOpenWebview.visibility = View.VISIBLE
        } else {
            mOpenWebview.visibility = View.GONE
        }
        mOpenWebview.setOnClickListener {
            listner.onClickWeb(item.E_URL)
        }
    }
}