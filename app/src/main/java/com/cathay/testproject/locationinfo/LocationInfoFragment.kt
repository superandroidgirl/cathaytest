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
    private lateinit var mInfoImg : ImageView
    private lateinit var mInfoTextView: TextView
    private lateinit var mInfoMemoTextView : TextView
    private lateinit var mInfoCategoryTextView: TextView
    private lateinit var mOpenWebview : TextView
    private lateinit var mRecGroup : Group
    private lateinit var mAdapter : PlanetListRecyclerAdapter

    private var mResults : MutableList<ResultX> = mutableListOf()
    private lateinit var progressBar : ProgressBar

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


        initView(view)
        mAdapter = PlanetListRecyclerAdapter(object : PlanetListRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(item: ResultX) {
                //go to planet detail
                mActivity.replaceFragment(PlanetDetailFragment.newInstance(item), "PlanetDetailFragment", true, true)

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

    fun initView(rootview : View) {
        mRecGroup = rootview.findViewById(R.id.planet_list_group)
        mInfoImg = rootview.findViewById(R.id.info_img)
        mInfoTextView = rootview.findViewById(R.id.info_text)
        mInfoMemoTextView = rootview.findViewById(R.id.memo_text)
        rvListView = rootview.findViewById(R.id.planet_list_recyclerview)
        mOpenWebview = rootview.findViewById(R.id.open_in_webview)
        mInfoCategoryTextView = rootview.findViewById(R.id.category_text)

        initData(rootview.context)
    }

    fun initData(rootContext : Context) {
        mInfoTextView.text = mItemData.E_Info
        mInfoMemoTextView.text = mItemData.E_Memo
        mInfoCategoryTextView.text = mItemData.E_Category

        GlideApp.with(rootContext)
            .load(mItemData.E_Pic_URL)
            .fitCenter()
            .placeholder(R.drawable.default_img)
            .transform(GlideRoundTransform(2))
            .centerCrop()
            .into(mInfoImg)

        mOpenWebview.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mItemData.E_URL)))
        }
    }

    fun callPlanetListAPI() {

        addCompositeDisposable(ApiClient.getPlanetList(mItemData.E_Name).subscribeWith(object : Subscriber<PlanetRoot>() {

            override fun onNext(responseData: PlanetRoot) {
                progressBar.visibility = View.GONE

                responseData.result.results?.let {
                    if (it.isNotEmpty()) {
                        mRecGroup.visibility = View.VISIBLE
                        mResults = responseData.result.results.toMutableList()
                        mAdapter.refreshData(mResults)
                    } else {
                        mRecGroup.visibility = View.GONE
                    }
                }

            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                progressBar.visibility = View.GONE
                mRecGroup.visibility = View.GONE
            }
        }))
    }

    override fun onResume() {
        super.onResume()
        mActivity.title = mItemData.E_Name
        mActivity.setBurger(false)
    }

}