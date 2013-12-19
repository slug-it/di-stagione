package it.slug.distagione

import android.util.Log


object MonthName extends Enumeration {
    type MonthName = Value
    val January, February, March, April, May, June, July, August,
        Semptember, October, November, December = Value
}

object DataSource  {
    /* ------------ ctor --------------------------------------------------- */
    private val mMonthNames = fillMonthNames()
    // Log.i("Distagione", monthName.map(_.toString()).deep.mkString("\n"))
    /* --------------------------------------------------------------------- */

    /* ------------ object access ------------------------------------------ */
    def monthNames() : Array[Int] = mMonthNames
    /* --------------------------------------------------------------------- */

    /* ------------ private members ---------------------------------------- */
    private def fillMonthNames() : Array[Int] = {
        var names = new Array[Int](12)

        names(0) = R.string.jan_ext
        names(1) = R.string.feb_ext
        names(2) = R.string.mar_ext
        names(3) = R.string.apr_ext
        names(4) = R.string.may_ext
        names(5) = R.string.jun_ext
        names(6) = R.string.jul_ext
        names(7) = R.string.aug_ext
        names(8) = R.string.sep_ext
        names(9) = R.string.oct_ext
        names(10) = R.string.nov_ext
        names(11) = R.string.dec_ext

        names
    }
    /* --------------------------------------------------------------------- */
}