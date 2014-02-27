package it.slug.distagione

import android.util.Log
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.content.Context


class MonthDetailPagerController(
    val pager: ViewPager,
    val ctxt: FragmentActivity
    )
    extends ViewStateListener
{
    /* ------------ ctor --------------------------------------------------- */
    // model vars
    private val mCtxt = ctxt

    // view vars
    private val mView = ViewState
    private val mPager = pager

    // controller vars
    private var mCurrentPage = -1

    // connections
    mView.addListener(this)

    // initializations
    private val fragManager = mCtxt.getSupportFragmentManager()
    mPager.setOnPageChangeListener(new MonthDetailPagerListener())
    mPager.setAdapter(new MonthDetailPagerAdapter(fragManager))
    onSelectedMonthChange(mView.selectedMonth)
    /* --------------------------------------------------------------------- */

    /* ------------ model handlers ----------------------------------------- */
    def onSelectedMonthChange(newMonth: Int): Unit = {
        if (mCurrentPage == newMonth) return
        mCurrentPage = newMonth
        mPager.setCurrentItem(mCurrentPage)
    }
    /* --------------------------------------------------------------------- */

    /* ------------ view handlers ------------------------------------------ */
    class MonthDetailPagerListener extends OnPageChangeListener
    {
        override def onPageScrollStateChanged(state: Int): Unit = {}

        override def onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
                ): Unit = {}

        override def onPageSelected(position: Int): Unit = {
            mCurrentPage = position
            mView.selectedMonth = position
        }
    }
    /* --------------------------------------------------------------------- */

    /* ------------ pager adapter class ------------------------------------ */
    class MonthDetailPagerAdapter(val fm: FragmentManager)
        extends FragmentPagerAdapter(fm)
    {
        val monthProduces = Array(R.array.month1,
                                  R.array.month2,
                                  R.array.month3,
                                  R.array.month4,
                                  R.array.month5,
                                  R.array.month6,
                                  R.array.month7,
                                  R.array.month8,
                                  R.array.month9,
                                  R.array.month10,
                                  R.array.month11,
                                  R.array.month12)
        val res = mCtxt.getResources()
        val mMonths = res.getStringArray(R.array.months_ext)

        // return the number of pages (i.e. the number of months)
        override def getCount(): Int = mMonths.length

        override def getPageTitle(position: Int): CharSequence = {
            mMonths(position)
        }

        override def getItem(position: Int): Fragment = {
            val pf = new MonthDetailPageFragment()
            val mProduces = res.getStringArray(monthProduces(position))
            pf._fixedContent = mProduces(5)
            pf
        }
    }
    /* --------------------------------------------------------------------- */
}
