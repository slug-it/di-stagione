package it.slug.distagione

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.content.res.Resources;


/*
    Fragment class for a single page shown in the month detail activity.
 */
class MonthDetailPageFragment() extends Fragment {

    /* ------------ ctor --------------------------------------------------- */
    // in Scala language, the class body is the first constructor
    private val monthProduces = Array(R.array.month1,
                                  R.array.month2,
                                  R.array.month3,
                                  R.array.month4,
                                  R.array.month5,
                                  R.array.month6,
                                  R.array.month7,
                                  R.array.month8,
                                  R.array.month9,
                                  R.array.month10,
                                  R.array.month11,
                                  R.array.month12)

    var _fixedContent = ""
    val _myKey = "MonthDetailPageFragment::State"
    /* --------------------------------------------------------------------- */

    def setMonthIndex(res: Resources, idx: Integer): Unit = {
        val mProduces = res.getStringArray(monthProduces(idx))
        var builder = new StringBuilder()
        for ((x, i) <- mProduces.zipWithIndex) {
            builder.append(x).append('\n')
        }
        _fixedContent = builder.toString()
    }

    /* ------------ protected members -------------------------------------- */
    override def onCreate(savedState: Bundle) : Unit =
    {
        super.onCreate(savedState)
        if (savedState != null && savedState.containsKey(_myKey)) {
            _fixedContent = savedState.getString(_myKey)
        }
    }

    override def onSaveInstanceState(outState: Bundle) : Unit =
    {
        super.onSaveInstanceState(outState)
        outState.putString(_myKey, _fixedContent)
    }

    override def onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedState: Bundle
        ) : View =
    {
        val activity = getActivity().asInstanceOf[ListaDiStagione]
        val text = new TextView(activity);
        text.setTypeface(activity.tf_element)
        text.setGravity(Gravity.CENTER);
        text.setText(_fixedContent);
        text.setTextSize(20 * getResources().getDisplayMetrics().density);
        text.setPadding(20, 20, 20, 20);
        val layout = new LinearLayout(activity);
        layout.setLayoutParams(
            new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT
                )
            );
        layout.setGravity(Gravity.CENTER);
        layout.addView(text);

        layout;
    }
    /* --------------------------------------------------------------------- */
}
