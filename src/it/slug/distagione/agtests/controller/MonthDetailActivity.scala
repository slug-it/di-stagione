package it.slug.distagione

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.Window


class MonthDetailActivity extends FragmentActivity with TypedViewHolder
{
    /* ------------ ctor --------------------------------------------------- */
    private val mViewState = ViewState
    /* --------------------------------------------------------------------- */

    /* ------------ protected members -------------------------------------- */
    override def onCreate(savedInstanceState: Bundle) : Unit = {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.month_detail)

        // widgets
        val upBar = findView(TR.month_bar)
        val pagerWidget = findView(TR.month_pager)

        // controllers
        val upBarController = new MonthBarController(upBar, this)
        val pagerController = new MonthDetailPagerController(pagerWidget, this)
    }
    /* --------------------------------------------------------------------- */
}
