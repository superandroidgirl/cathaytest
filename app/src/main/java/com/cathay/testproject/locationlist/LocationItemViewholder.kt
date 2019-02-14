package com.cathay.testproject.locationlist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cathay.testproject.GlideApp
import com.cathay.testproject.R
import com.cathay.testproject.commonutils.GlideRoundTransform
import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListResultX
import com.momo.mobile.shoppingv2.android.modules.envelope.adapter.LocationRecyclerAdapter

class LocationItemViewholder(rootView: View, val listner : LocationRecyclerAdapter.OnItemClickListener): RecyclerView.ViewHolder(rootView) {

    private val mName = rootView.findViewById<TextView>(R.id.location_name)
    private val mLocationInfo = rootView.findViewById<TextView>(R.id.location_info)
    private val mLocationMemo = rootView.findViewById<TextView>(R.id.location_memo)
    private val mImageView = rootView.findViewById<ImageView>(R.id.imageView)
    private val mView = rootView

    fun refreshItem(item : CategoryListResultX) {
        if (item == null) return

        mName.text = item.E_Name
        mLocationInfo.text = item.E_Info
        if (item.E_Memo.isEmpty()) {
            mLocationMemo.text = mView.context.getString(R.string.no_memo_string)
        } else {
            mLocationMemo.text = item.E_Memo
        }

        GlideApp.with(itemView.context)
            .load(item.E_Pic_URL)
            .fitCenter()
            .placeholder(R.drawable.default_img)
            .transform(GlideRoundTransform(2))
            .centerCrop()
            .into(mImageView)

        mView.setOnClickListener {
            listner.onItemClick(item)
        }
    }
}