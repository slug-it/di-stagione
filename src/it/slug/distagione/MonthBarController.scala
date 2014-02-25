package it.slug.distagione

import android.util.Log
import android.content.Context
import android.view.View
import android.view.View.OnClickListener


class MonthBarController(val barWidget: TabBar, val ctxt: Context)
    extends ViewStateListener
{
    /* ------------ ctor --------------------------------------------------- */
    private val mCtxt = ctxt

    private val mView = ViewState
    private val mBar = barWidget

    val res = ctxt.getResources()
    val mMonths = res.getStringArray(R.array.months_short)

    mView.addListener(this)

    private val monthStyles = Array(
        R.attr.TabBarItemWinterStyle,   // january
        R.attr.TabBarItemWinterStyle,   // february
        R.attr.TabBarItemSpringStyle,   // march
        R.attr.TabBarItemSpringStyle,   // april
        R.attr.TabBarItemSpringStyle,   // may
        R.attr.TabBarItemSummerStyle,   // june
        R.attr.TabBarItemSummerStyle,   // july
        R.attr.TabBarItemSummerStyle,   // august
        R.attr.TabBarItemAutumnStyle,   // september
        R.attr.TabBarItemAutumnStyle,   // october
        R.attr.TabBarItemAutumnStyle,   // november
        R.attr.TabBarItemWinterStyle    // december
        )

    for ((x, i) <- mMonths.zipWithIndex) {
        val newItem = mBar.addTab(x, monthStyles(i))
        newItem.setOnClickListener(new ItemClickListener(i))
    }
    mBar.setCurrentItem(mView.currentMonth)
    /* --------------------------------------------------------------------- */

    /* ------------ model handlers ----------------------------------------- */
    def onCurrentMonthChange(newMonth: Int): Unit = {
        if (mBar.selectedIndex == newMonth) return
        mBar.setCurrentItem(newMonth)
    }
    /* --------------------------------------------------------------------- */

    /* ------------ gui handlers ------------------------------------------- */
    class ItemClickListener(index: Integer) extends OnClickListener
    {
        private val itemIndex: Integer = index

        override def onClick(v: View): Unit = {
            mView.currentMonth = itemIndex
        }
    }
    /* --------------------------------------------------------------------- */
}
