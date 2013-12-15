package it.slug.distagione

import android.util.Log
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.content.Context


/*
    Adapter for the ViewPager component in the month detail activity.
 */
class MonthDetailPagerAdapter(val fm: FragmentManager, val ctxt: Context) 
    extends FragmentPagerAdapter(fm)
{
    private val context = ctxt              // needed to access resource table
    private val dataSource = DataSource     // link to model data

    // return the number of pages (i.e. the number of months)
    override def getCount(): Int = dataSource.monthNames().length
    
    override def getPageTitle(position: Int): CharSequence = {
        context.getString(dataSource.monthNames()(position))
    }

    override def getItem(position: Int): Fragment = {
        val name = context.getString(dataSource.monthNames()(position))
        new MonthDetailPageFragment(name)
    }
}
