package com.momo.mobile.shoppingv2.android.modules.envelope.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cathay.testproject.R
import com.cathay.testproject.locationinfo.PlanetItemViewholder
import com.cathay.testproject.retrofit.apimodel.planetlist.ResultX

class PlanetListRecyclerAdapter(private var listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mResultLists : MutableList<ResultX> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(item : ResultX)
    }

    fun refreshData(resultLists : MutableList<ResultX>) {
        mResultLists = resultLists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.location_list_item, parent, false)
            return PlanetItemViewholder(view, listener)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlanetItemViewholder).refreshItem(mResultLists.get(position))
    }

    override fun getItemCount(): Int {
        return mResultLists?.size
    }


}