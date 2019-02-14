package com.momo.mobile.shoppingv2.android.modules.envelope.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cathay.testproject.R
import com.cathay.testproject.locationinfo.PlanetItemViewholder
import com.cathay.testproject.retrofit.apimodel.planetlist.ResultX
import com.cathay.testproject.locationinfo.HeaderViewHolder
import com.cathay.testproject.locationinfo.LocationInfoFragment
import com.cathay.testproject.locationinfo.MiddleViewHolder
import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListResultX


class PlanetListRecyclerAdapter(private var listener: OnItemClickListener, private var weblistener : LocationInfoFragment.OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_TYPE_HEADER = 0
    private val ITEM_TYPE_MIDDLE = 1
    private val ITEM_TYPE_CONTENT = 2
    private var mResultLists : MutableList<ResultX> = mutableListOf()
    private val mHeaderCount = 1
    private val mMiddleCount = 1
    private var mItemData : CategoryListResultX? = null

    interface OnItemClickListener {
        fun onItemClick(item : ResultX)
    }

    fun refreshData(resultLists : MutableList<ResultX>, categoryListResultX: CategoryListResultX) {
        mResultLists = resultLists
        mItemData = categoryListResultX
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.locationinfo_header, parent, false)
                HeaderViewHolder(view, weblistener)
            }
            ITEM_TYPE_MIDDLE -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.location_info_middle_text, parent, false)
                MiddleViewHolder(view)
            }
            ITEM_TYPE_CONTENT -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.location_list_item, parent, false)
                PlanetItemViewholder(view, listener)
            }
            else -> {
                val view = LayoutInflater.from(parent?.context).inflate(R.layout.location_info_middle_text, parent, false)
                MiddleViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                mItemData?.let { holder.refreshItem(it) }
            }
            is MiddleViewHolder -> {

            }
            is PlanetItemViewholder -> {
                holder.refreshItem(mResultLists[position - 2])
            }
        }
    }

    override fun getItemCount(): Int {
        if (mResultLists.size > 0) {
            return mResultLists?.size + 2
        } else {
            return 1
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (mHeaderCount !== 0 && position < mHeaderCount) {
            ITEM_TYPE_HEADER
        } else if (mMiddleCount !== 0 && mResultLists.size > 0 && position == 1) {
            ITEM_TYPE_MIDDLE
        } else {
            ITEM_TYPE_CONTENT
        }
    }
}