package com.momo.mobile.shoppingv2.android.modules.envelope.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cathay.testproject.R
import com.cathay.testproject.locationlist.LocationItemViewholder
import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListResultX

class LocationRecyclerAdapter(private var listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mResultLists : MutableList<CategoryListResultX> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(item : CategoryListResultX)
    }

    fun refreshData(resultLists : MutableList<CategoryListResultX>) {
        mResultLists = resultLists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.location_list_item, parent, false)
            return LocationItemViewholder(view, listener)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LocationItemViewholder).refreshItem(mResultLists.get(position))
    }

    override fun getItemCount(): Int {
        return mResultLists?.size
    }


}