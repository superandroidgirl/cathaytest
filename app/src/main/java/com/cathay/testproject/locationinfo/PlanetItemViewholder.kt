package com.cathay.testproject.locationinfo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cathay.testproject.GlideApp
import com.cathay.testproject.R
import com.cathay.testproject.commonutils.GlideRoundTransform
import com.cathay.testproject.retrofit.apimodel.planetlist.ResultX
import com.momo.mobile.shoppingv2.android.modules.envelope.adapter.PlanetListRecyclerAdapter

class PlanetItemViewholder(rootView: View, val listner : PlanetListRecyclerAdapter.OnItemClickListener): RecyclerView.ViewHolder(rootView) {

    private val mName = rootView.findViewById<TextView>(R.id.location_name)
    private val mPlanetAlsoKnown = rootView.findViewById<TextView>(R.id.location_info)
    private val mLocationMemo = rootView.findViewById<TextView>(R.id.location_memo)
    private val mImageView = rootView.findViewById<ImageView>(R.id.imageView)
    private val mView = rootView

    fun refreshItem(item : ResultX) {
        if (item == null) return

        mName.text = item.fNameCh
        mPlanetAlsoKnown.text = item.fAlsoKnown
        mLocationMemo.visibility = View.GONE

        GlideApp.with(itemView.context)
            .load(item.fPic01URL)
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