package it.slug.distagione

import android.util.Log

/* Scala enum example.
object MonthName extends Enumeration {
    type MonthName = Value
    val January, February, March, April, May, June, July, August,
        Semptember, October, November, December = Value
}
*/

/* **************** view state ********************************************* */
/*
    This singleton containes view-specific settings, that should be saved as
    "user preferences" on application shutdown.
 */
abstract class ViewStateListener {
    def onCurrentMonthChange(newMonth: Int): Unit
}

object ViewState {
    /* ------------ ctor --------------------------------------------------- */
    private var mCurrentMonth: Int = 0
    private var mListeners: List[ViewStateListener] = Nil
    /* --------------------------------------------------------------------- */

    /* ------------ object access ------------------------------------------ */
    def currentMonth = mCurrentMonth
    /* --------------------------------------------------------------------- */

    /* ------------ object manipulation ------------------------------------ */
    def addListener(l: ViewStateListener): Unit = mListeners ::= l

    def currentMonth_= (newMonth: Int): Unit = {
        if (mCurrentMonth == newMonth) return
        mCurrentMonth = newMonth
        notifyMonthChange()
    }
    /* --------------------------------------------------------------------- */

    /* ------------ private members ---------------------------------------- */
    private def notifyMonthChange(): Unit = {
        for (l <- mListeners) l.onCurrentMonthChange(mCurrentMonth)
    }
    /* --------------------------------------------------------------------- */
}
/* ************************************************************************* */

