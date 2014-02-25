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

/*
    Fragment class for a single page shown in the month detail activity.
 */
class MonthDetailPageFragment() extends Fragment {

    /* ------------ ctor --------------------------------------------------- */
    // in Scala language, the class body is the first constructor
    var _fixedContent = ""
    val _myKey = "MonthDetailPageFragment::State"
    /* --------------------------------------------------------------------- */

    /* ------------ protected members -------------------------------------- */
    override def onCreate(savedState: Bundle) : Unit =
    {
        super.onCreate(savedState)
        if (savedState != null && savedState.containsKey(_myKey)) {
            _fixedContent = savedState.getString(_myKey)
        } else {
            var (builder, i) = (new StringBuilder(), 0)
            for (i <- 0 until 20) builder.append(_fixedContent).append("\n")
            builder.deleteCharAt(builder.length() - 1)
            _fixedContent = builder.toString()
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
        val activity = getActivity()
        val text = new TextView(activity);
        // text.setTypeface(activity.tf_element)
        text.setGravity(Gravity.CENTER);
        text.setText(_fixedContent);
        text.setTextSize(20 * getResources().getDisplayMetrics().density);
        text.setPadding(20, 20, 20, 20);
        val layout = new LinearLayout(getActivity());
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
