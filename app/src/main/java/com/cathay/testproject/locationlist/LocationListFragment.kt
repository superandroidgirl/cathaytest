package com.cathay.testproject.locationlist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cathay.testproject.MainActivity
import com.cathay.testproject.R
import com.cathay.testproject.retrofit.RetrofitFragment
import com.cathay.testproject.retrofit.api.ApiClient
import com.cathay.testproject.retrofit.api.Subscriber
import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListResultX
import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListRoot
import com.momo.mobile.shoppingv2.android.modules.envelope.adapter.LocationRecyclerAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.cathay.testproject.locationinfo.LocationInfoFragment


class LocationListFragment: RetrofitFragment() {

    private lateinit var mActivity : MainActivity
    private lateinit var rvListView: RecyclerView
    private lateinit var mNoData : TextView
    private lateinit var mAdapter : LocationRecyclerAdapter
    private var mResults : MutableList<CategoryListResultX> = mutableListOf()
    private lateinit var progressBar : ProgressBar

    companion object {
        fun newInstance() : LocationListFragment = LocationListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.locationlist_layout, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = LocationRecyclerAdapter(object : LocationRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(item: CategoryListResultX) {
                //go to category info
                mActivity.replaceFragment(
                    LocationInfoFragment.newInstance(item),
                    "LocationInfoFragment",
                    true,
                    true
                )
            }
        })

        rvListView = view.findViewById<RecyclerView>(R.id.recycler_layout).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mNoData = view.findViewById(R.id.no_data_text)
        progressBar = view.findViewById(R.id.progressbar) as ProgressBar


        callLocationListAPI()
    }

    fun callLocationListAPI() {

        addCompositeDisposable(ApiClient.getCategoryList().subscribeWith(object : Subscriber<CategoryListRoot>() {

            override fun onNext(responseData: CategoryListRoot) {
                progressBar.visibility = View.GONE
                mNoData.visibility = View.GONE
                if (responseData.result.results != null && responseData.result.results.isNotEmpty()) {
                    mResults = responseData.result.results.toMutableList()
                    mAdapter.refreshData(mResults)
                } else {
                    mNoData.visibility = View.VISIBLE
                }


            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                progressBar.visibility = View.GONE
                mNoData.visibility = View.VISIBLE
            }
        }))
    }

    override fun onResume() {
        super.onResume()
        mActivity.title = getString(R.string.main_title)
        mActivity.setBurger(true)
    }

}