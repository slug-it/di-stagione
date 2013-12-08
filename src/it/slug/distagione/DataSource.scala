package it.slug.distagione

import android.util.Log
import java.util.ArrayList



object Month extends Enumeration {
    type Month = Value
    val January, February, March, April, May, June, July, August,
        Semptember, October, November, December = Value
}

object DataSource {

    private val monthName = fillMonthNames()

    def fillMonthNames() : Array[Integer] = {
        var names = new Array[Integer](12)
        Log.i("Distagione", "Creating month names array")
        names
    }

    private val logTag = "DataSource"
}
