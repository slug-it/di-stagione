package it.slug.distagione

import android.util.Log
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.content.Context


class MonthDetailPagerController(
    val pager: ViewPager,
    val parentActivity: MonthDetailActivity
    )
{
    /* ------------ ctor --------------------------------------------------- */
    // model vars
    val mData = DataSource
    val mActivity = parentActivity

    // view vars
    val mPager = pager

    // connections
    mActivity.addListener(onCurrentMonthChange)

    // initializations
    val fragManager = mActivity.getSupportFragmentManager()
    mPager.setAdapter(new MonthDetailPagerAdapter(fragManager))
    /* --------------------------------------------------------------------- */

    /* ------------ model handlers ----------------------------------------- */
    private def onCurrentMonthChange(newMonth: Int): Unit = {
        Log.i("Distagione", "New Month " + newMonth)
    }
    /* --------------------------------------------------------------------- */

    /* ------------ view handlers ------------------------------------------ */

    /* --------------------------------------------------------------------- */

    /* ------------ pager adapter class ------------------------------------ */
    class MonthDetailPagerAdapter(val fm: FragmentManager)
        extends FragmentPagerAdapter(fm)
    {
        val mMonths = mData.monthNames()

        // return the number of pages (i.e. the number of months)
        override def getCount(): Int = mMonths.length

        override def getPageTitle(position: Int): CharSequence = {
            mActivity.getString(mMonths(position))
        }

        override def getItem(position: Int): Fragment = {
            new MonthDetailPageFragment(mActivity.getString(mMonths(position)))
        }
    }
    /* --------------------------------------------------------------------- */
}
