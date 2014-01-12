package it.slug.distagione

import android.util.Log
import android.content.Context
import android.view.View
import android.view.View.OnClickListener


class MonthBarController(val barWidget: TabBar, val ctxt: Context)
    extends ViewStateListener
{
    /* ------------ ctor --------------------------------------------------- */
    private val mModel = ModelState
    private val mCtxt = ctxt

    private val mView = ViewState
    private val mBar = barWidget

    mView.addListener(this)

    for ((x, i) <- mModel.monthNames().zipWithIndex) {
        val newItem = mBar.addTab(mCtxt.getString(x))
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
