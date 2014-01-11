package it.slug.distagione

import android.util.Log
import android.content.Context


class MonthBarController(val barWidget: TabBar, val ctxt: Context)
{
    /* ------------ ctor --------------------------------------------------- */
    private val mModel = ModelState
    private val mCtxt = ctxt

    private val mView = ViewState
    private val mBar = barWidget

    for (x <- mModel.monthNames()) mBar.addTab(mCtxt.getString(x))
    mBar.setCurrentItem(mView.currentMonth)
    /* --------------------------------------------------------------------- */

    /* ------------ private members ---------------------------------------- */

    /* --------------------------------------------------------------------- */
}
