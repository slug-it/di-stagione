package it.slug.distagione

import java.lang.Object
import android.support.v4.app.Fragment
import android.os.Bundle
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.TextView


/*
    Fragment class for a single page shown in the month detail activity.
 */
class MonthDetailPageFragment() extends Fragment {

    /* ------------ ctor ---------------------------------------------------- */
    // in Scala language, the class body is the first constructor
    private var tf_element: Typeface = null
    var mMonthIndex = -1
    val _myKey = "MonthDetailPageFragment::State"
    /* ---------------------------------------------------------------------- */
    def monthProduces(idx: Integer): Array[String] = {
        val act = getActivity()
        val res = act.getResources()
        val prod = res.getIdentifier(
            "month"+(idx+1),
            "array",
            act.getPackageName()
            )
        res.getStringArray(prod)
    }

    /* ------------ public members ------------------------------------------ */
    def setMonthIndex(monthIndex: Integer): Unit = {
        mMonthIndex = monthIndex
    }
    /* ---------------------------------------------------------------------- */

    /* ------------ protected members --------------------------------------- */
    override def onCreate(savedState: Bundle) : Unit = {
        super.onCreate(savedState)
        if (savedState != null && savedState.containsKey(_myKey)) {
            mMonthIndex = savedState.getInt(_myKey)
        }
    }

    override def onSaveInstanceState(outState: Bundle) : Unit = {
        super.onSaveInstanceState(outState)
        outState.putInt(_myKey, mMonthIndex)
    }

    override def onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup,
            savedState: Bundle
            ) : View = {
        val activity = getActivity().asInstanceOf[ListaDiStagione]

        tf_element = Typeface.createFromAsset(
            activity.getAssets,
            "fonts/SortsMillGoudy-Regular.ttf"
            )

        // build list
        val list = new ListView(activity);
        val adapter = new MyArrayAdapter[String](
            activity,
            android.R.layout.simple_list_item_1,
            monthProduces(mMonthIndex)
            )
        list.setAdapter(adapter)

        // create layout and add list as child
        val layout = new LinearLayout(activity);
        layout.setLayoutParams(
            new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT
                )
            );
        layout.setGravity(Gravity.CENTER);
        layout.addView(list);

        layout;
    }
    /* ---------------------------------------------------------------------- */


    /* ------------ custom array adapter ------------------------------------ */
    class MyArrayAdapter[T](ctxt: Context, r: Integer, os: Array[T with Object])
        extends ArrayAdapter[T](ctxt, r, os)
    {
        override def getView(pos: Int, v: View, vg: ViewGroup): View = {
            val tv = super.getView(pos, v, vg).asInstanceOf[TextView]
            tv.setTypeface(tf_element)
            tv
        }
    }
    /* ---------------------------------------------------------------------- */
}
