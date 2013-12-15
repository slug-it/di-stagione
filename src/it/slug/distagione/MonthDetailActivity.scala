package it.slug.distagione

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager


class MonthDetailActivity extends FragmentActivity with TypedViewHolder 
{
    /* ------------ ctor --------------------------------------------------- */
    private var mCurrentMonth = 0
    private var listeners: List[Int => Unit] = Nil
    /* --------------------------------------------------------------------- */

    /* ------------ protected members -------------------------------------- */
    override def onCreate(savedInstanceState: Bundle) : Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.month_detail)

        // widgets
        val pagerWidget = findView(TR.month_pager)

        // controllers
        var mCurrentMonth = 0
        val pagerController = new MonthDetailPagerController(pagerWidget, this)
    }
    /* --------------------------------------------------------------------- */

    /* ------------ public members ----------------------------------------- */
    def currentMonth = mCurrentMonth

    def currentMonth_= (newMonth: Int): Unit = {
        if (newMonth == mCurrentMonth) return
        mCurrentMonth = newMonth
        notifyMonth()
    }

    def addListener(listener: Int => Unit): Unit = listeners ::= listener
    /* --------------------------------------------------------------------- */

    /* ------------ private members ---------------------------------------- */
    private def notifyMonth(): Unit = for (l <- listeners) l(mCurrentMonth)
    /* --------------------------------------------------------------------- */
}
