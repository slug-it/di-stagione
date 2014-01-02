package it.slug.distagione

import android.util.Log
import android.content.Context


class MonthBarController(val barWidget: TabBar, val ctxt: Context)
{
    /* ------------ ctor --------------------------------------------------- */
    val mModel = ModelState
    val mCtxt = ctxt

    val mBar = barWidget

    for (x <- mModel.monthNames()) mBar.addTab(mCtxt.getString(x))
    /* --------------------------------------------------------------------- */

    /* ------------ private members ---------------------------------------- */

    /* --------------------------------------------------------------------- */
}
