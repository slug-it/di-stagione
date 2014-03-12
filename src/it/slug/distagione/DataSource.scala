package it.slug.distagione

import java.util.Date
import android.util.Log


/* **************** view state ********************************************** */
/*
    This singleton containes view-specific settings, that should be saved as
    "user preferences" on application shutdown.
 */
abstract class ViewStateListener {
    def onSelectedMonthChange(newMonth: Int): Unit
}

object ViewState {
    /* ------------ ctor ---------------------------------------------------- */
    private val mCurrentMonth: Int = new Date().getMonth
    private var mSelectedMonth: Int = mCurrentMonth
    private var mListeners: List[ViewStateListener] = Nil
    /* ---------------------------------------------------------------------- */

    /* ------------ object read --------------------------------------------- */
    def selectedMonth = mSelectedMonth
    /* ---------------------------------------------------------------------- */

    /* ------------ object write -------------------------------------------- */
    def addListener(l: ViewStateListener): Unit = mListeners ::= l

    def selectedMonth_= (newMonth: Int): Unit = {
        if (mSelectedMonth == newMonth) return
        mSelectedMonth = newMonth
        notifyMonthChange()
    }
    /* ---------------------------------------------------------------------- */

    /* ------------ private members ----------------------------------------- */
    private def notifyMonthChange(): Unit = {
        for (l <- mListeners) l.onSelectedMonthChange(mSelectedMonth)
    }
    /* ---------------------------------------------------------------------- */
}
/* ************************************************************************** */

