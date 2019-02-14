package com.cathay.testproject.planetdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cathay.testproject.GlideApp
import com.cathay.testproject.MainActivity
import com.cathay.testproject.R
import com.cathay.testproject.commonutils.Config
import com.cathay.testproject.commonutils.GlideRoundTransform
import com.cathay.testproject.retrofit.RetrofitFragment
import com.cathay.testproject.retrofit.apimodel.planetlist.ResultX
import org.parceler.Parcels


class PlanetDetailFragment: RetrofitFragment(){

    private lateinit var mItemData : ResultX
    private lateinit var mActivity : MainActivity
    private lateinit var mPlanetImg : ImageView
    private lateinit var mPlanetName: TextView
    private lateinit var mPlanetEnName : TextView
    private lateinit var mAlsoKnownText: TextView
    private lateinit var mBriefText : TextView
    private lateinit var mFeatureText : TextView
    private lateinit var mFuntionTextView: TextView
    private lateinit var mLastUpdate : TextView

    companion object {
        fun newInstance(resultX: ResultX) : PlanetDetailFragment = PlanetDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Config.BUNDLE_PLANET_DETAIL, Parcels.wrap(resultX))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItemData = Parcels.unwrap<ResultX>(arguments?.getParcelable(Config.BUNDLE_PLANET_DETAIL))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.planet_detail_layout, container, false)

        mActivity = activity as MainActivity


        initView(view)

        return view
    }

    fun initView(rootview : View) {

        mPlanetImg = rootview.findViewById(R.id.planet_img)
        mPlanetName = rootview.findViewById(R.id.planet_name)
        mPlanetEnName = rootview.findViewById(R.id.planet_en_name)
        mAlsoKnownText = rootview.findViewById(R.id.also_known_text)
        mBriefText = rootview.findViewById(R.id.planet_brief_text)
        mFeatureText = rootview.findViewById(R.id.planet_feature_text)
        mFuntionTextView = rootview.findViewById(R.id.planet_funtion_text)
        mLastUpdate = rootview.findViewById(R.id.lastupdate_time)

        initData(rootview.context)
    }

    fun initData(rootContext : Context) {
        mPlanetName.text = mItemData.fNameCh
        mPlanetEnName.text = mItemData.fNameEn
        mAlsoKnownText.text = mItemData.fAlsoKnown
        mBriefText.text = mItemData.fBrief
        mFeatureText.text = mItemData.fFeature
        mFuntionTextView.text = mItemData.fFunctionApplication
        mLastUpdate.text = mItemData.fUpdate

        GlideApp.with(rootContext)
            .load(mItemData.fPic01URL)
            .fitCenter()
            .placeholder(R.drawable.default_img)
            .transform(GlideRoundTransform(2))
            .centerCrop()
            .into(mPlanetImg)

    }

    override fun onResume() {
        super.onResume()
        mActivity.title = mItemData.fNameCh
        mActivity.setBurger(false)
    }

}