package com.cathay.testproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import com.cathay.testproject.locationlist.LocationListFragment
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mToggle: ActionBarDrawerToggle

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


         initToolbar()
     }

    private fun initToolbar() {
        setSupportActionBar(toolbar)

        mToggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(mToggle)
        mToggle.syncState()
        initFragment()

    }

    private fun initFragment() {
        replaceFragment(LocationListFragment.newInstance(), "LocationListFragment", false, false)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
                super.onBackPressed()
                getCurrentFragment().onResume()

        }

    }

    /**
     * 替換FragmentLayout中的Fragment
     *
     * @param fragment         ftagment實體
     * @param fragmentName     fragment名稱 用以辨別
     * @param isAdd            fragment是用add方式還是replace方式 true:add, false:replace
     * @param isAddToBackStack 是否要將前一個fragment塞入BackStack  true:塞入, false:不塞入
     */
    fun replaceFragment(fragment: Fragment, fragmentName: String, isAdd: Boolean, isAddToBackStack: Boolean) {
        var layoutID = R.id.fragment_layout

        try {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (isAdd) {
                fragmentTransaction.add(layoutID, fragment, fragmentName)
            } else {
                fragmentTransaction.replace(layoutID, fragment, fragmentName)
            }

            if (isAddToBackStack)
                fragmentTransaction.addToBackStack(null)

            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

    }

    fun setBurger(show : Boolean) {
        val actionBar = supportActionBar

        if (show.not()) {
            actionBar?.let {
                actionBar.setHomeButtonEnabled(true)
                actionBar.setDisplayHomeAsUpEnabled(true)

            }

            toolbar.setNavigationOnClickListener { this.onBackPressed() }
        } else {
            actionBar?.let {
                actionBar.setDisplayHomeAsUpEnabled(false)
                mToggle.isDrawerIndicatorEnabled = true
                mToggle.syncState()
            }
        }
    }

    private fun getCurrentFragment(): Fragment {
        return supportFragmentManager.findFragmentById(R.id.fragment_layout)!!
    }

}
