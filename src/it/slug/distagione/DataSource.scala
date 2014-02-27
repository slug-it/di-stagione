package it.slug.distagione

import java.util.Date
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
    def onSelectedMonthChange(newMonth: Int): Unit
}

object ViewState {
    /* ------------ ctor --------------------------------------------------- */
    private var mCurrentMonth: Int = new Date().getMonth
    private var mSelectedMonth: Int = mCurrentMonth
    private var mListeners: List[ViewStateListener] = Nil
    /* --------------------------------------------------------------------- */

    /* ------------ object access ------------------------------------------ */
    def selectedMonth = mSelectedMonth
    /* --------------------------------------------------------------------- */

    /* ------------ object manipulation ------------------------------------ */
    def addListener(l: ViewStateListener): Unit = mListeners ::= l

    def selectedMonth_= (newMonth: Int): Unit = {
        if (mSelectedMonth == newMonth) return
        mSelectedMonth = newMonth
        notifyMonthChange()
    }
    /* --------------------------------------------------------------------- */

    /* ------------ private members ---------------------------------------- */
    private def notifyMonthChange(): Unit = {
        for (l <- mListeners) l.onSelectedMonthChange(mSelectedMonth)
    }
    /* --------------------------------------------------------------------- */
}
/* ************************************************************************* */

