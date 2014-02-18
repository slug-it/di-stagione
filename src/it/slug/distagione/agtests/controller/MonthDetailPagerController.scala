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
    val parentActivity: MonthDetailActivity
    )
    extends ViewStateListener
{
    /* ------------ ctor --------------------------------------------------- */
    // model vars
    private val mActivity = parentActivity

    // view vars
    private val mView = ViewState
    private val mPager = pager

    // controller vars
    private var mCurrentPage = -1

    // connections
    mView.addListener(this)

    // initializations
    private val fragManager = mActivity.getSupportFragmentManager()
    mPager.setOnPageChangeListener(new MonthDetailPagerListener())
    mPager.setAdapter(new MonthDetailPagerAdapter(fragManager))
    onCurrentMonthChange(mView.currentMonth)
    /* --------------------------------------------------------------------- */

    /* ------------ model handlers ----------------------------------------- */
    def onCurrentMonthChange(newMonth: Int): Unit = {
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
            mView.currentMonth = position
        }
    }
    /* --------------------------------------------------------------------- */

    /* ------------ pager adapter class ------------------------------------ */
    class MonthDetailPagerAdapter(val fm: FragmentManager)
        extends FragmentPagerAdapter(fm)
    {
        val res = mActivity.getResources()
        val mMonths = res.getStringArray(R.array.months_ext)

        // return the number of pages (i.e. the number of months)
        override def getCount(): Int = mMonths.length

        override def getPageTitle(position: Int): CharSequence = {
            mMonths(position)
        }

        override def getItem(position: Int): Fragment = {
            new MonthDetailPageFragment(mMonths(position))
        }
    }
    /* --------------------------------------------------------------------- */
}
