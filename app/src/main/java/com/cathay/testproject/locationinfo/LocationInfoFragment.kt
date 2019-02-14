package com.cathay.testproject.locationinfo

import android.content.Context
import android.os.Bundle
import android.support.constraint.Group
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.cathay.testproject.GlideApp
import com.cathay.testproject.MainActivity
import com.cathay.testproject.R
import com.cathay.testproject.commonutils.Config
import com.cathay.testproject.commonutils.GlideRoundTransform
import com.cathay.testproject.retrofit.RetrofitFragment
import com.cathay.testproject.retrofit.api.ApiClient
import com.cathay.testproject.retrofit.api.Subscriber
import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListResultX
import com.cathay.testproject.retrofit.apimodel.planetlist.PlanetRoot
import com.cathay.testproject.retrofit.apimodel.planetlist.ResultX
import com.momo.mobile.shoppingv2.android.modules.envelope.adapter.PlanetListRecyclerAdapter
import org.parceler.Parcels
import android.content.Intent
import android.net.Uri
import com.cathay.testproject.planetdetail.PlanetDetailFragment


class LocationInfoFragment: RetrofitFragment(){

    private lateinit var mItemData : CategoryListResultX
    private lateinit var mActivity : MainActivity
    private lateinit var rvListView: RecyclerView
    private lateinit var mAdapter : PlanetListRecyclerAdapter

    private var mResults : MutableList<ResultX> = mutableListOf()
    private lateinit var progressBar : ProgressBar

    interface OnItemClickListener {
        fun onClickWeb(urlString : String)
    }

    companion object {
        fun newInstance(categoryListResultX: CategoryListResultX) : LocationInfoFragment = LocationInfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Config.BUNDLE_LOCATION_INFO, Parcels.wrap(categoryListResultX))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItemData = Parcels.unwrap<CategoryListResultX>(arguments?.getParcelable(Config.BUNDLE_LOCATION_INFO))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.location_info_layout, container, false)

        mActivity = activity as MainActivity

        rvListView = view.findViewById(R.id.planet_list_recyclerview)
        mAdapter = PlanetListRecyclerAdapter(object : PlanetListRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(item: ResultX) {
                //go to planet detail
                mActivity.replaceFragment(PlanetDetailFragment.newInstance(item), "PlanetDetailFragment", true, true)

            }
        }, object : OnItemClickListener {
            override fun onClickWeb(urlString: String) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlString)))
            }
        })

        rvListView = view.findViewById<RecyclerView>(R.id.planet_list_recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        rvListView.isNestedScrollingEnabled = false;
        progressBar = view.findViewById(R.id.progressbar) as ProgressBar

        callPlanetListAPI()
        return view
    }

    fun callPlanetListAPI() {

        addCompositeDisposable(ApiClient.getPlanetList(mItemData.E_Name).subscribeWith(object : Subscriber<PlanetRoot>() {

            override fun onNext(responseData: PlanetRoot) {
                progressBar.visibility = View.GONE

                responseData.result.results?.let {
                    if (it.isNotEmpty()) {
                        mResults = responseData.result.results.toMutableList()
                        mAdapter.refreshData(mResults, mItemData)
                    } else {
                        mAdapter.refreshData(mResults, mItemData)
                    }
                }

            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                progressBar.visibility = View.GONE
                mAdapter.refreshData(mResults, mItemData)
            }
        }))
    }

    override fun onResume() {
        super.onResume()
        mActivity.title = mItemData.E_Name
        mActivity.setBurger(false)
    }

}