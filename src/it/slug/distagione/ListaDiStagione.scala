package it.slug.distagione

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.widget.Button
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import android.view.Window
import android.view.MenuItem
import android.util.Log
import android.util.AttributeSet
import android.support.v4.app.FragmentActivity
import android.widget.TextView


class ListaDiStagione extends FragmentActivity with TypedViewHolder {

    private val mMonthColors = Array(
        R.color.winter_color,   // january
        R.color.winter_color,   // february
        R.color.spring_color,   // march
        R.color.spring_color,   // april
        R.color.spring_color,   // may
        R.color.summer_color,   // june
        R.color.summer_color,   // july
        R.color.summer_color,   // august
        R.color.autumn_color,   // september
        R.color.autumn_color,   // october
        R.color.autumn_color,   // november
        R.color.winter_color    // december
        )

    private var mMonthTitle: TextView = null
    private var mInnerListener: MyListener = null

    override def onCreate(saved: Bundle) : Unit = {
        super.onCreate(saved)

        val tf_title = Typeface.createFromAsset(getAssets, "fonts/OverlockSC-Regular.ttf")
        val tf_element = Typeface.createFromAsset(getAssets, "fonts/SortsMillGoudy-Regular.ttf")

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main)

        // model
        mInnerListener = new MyListener()
        ViewState.addListener(mInnerListener)

        // widgets
        val pagerWidget = findView(TR.month_pager)
        val monthBar = findView(TR.month_bar)
        mMonthTitle = findView(TR.month_title)

        // controllers
        val pagerController = new MonthDetailPagerController(pagerWidget, this)
        val barController = new MonthBarController(monthBar, this)

        // widgets initializations
        mMonthTitle.setTypeface(tf_title)
        setMonthTitle(ViewState.selectedMonth)
    }

    override def onCreateOptionsMenu(menu: Menu) : Boolean = {
        getMenuInflater().inflate(R.layout.menu_main, menu)
        return true
    }

    override def onOptionsItemSelected(item: MenuItem) : Boolean = {
        item.getItemId() match {

        case R.id.test_detail_menu => {
            val intent = new Intent(ListaDiStagione.this,
                                    classOf[ItemDetailActivity])
            startActivity(intent)
            return true
        }

        case _ => return super.onOptionsItemSelected(item)
        }
    }

    private def setMonthTitle(newMonth: Int): Unit = {
        val mMonths = getResources().getStringArray(R.array.months_ext)
        mMonthTitle.setText(mMonths(newMonth))
        mMonthTitle.setBackgroundResource(mMonthColors(newMonth))
    }

    /* ------------ model handlers ------------------------------------------ */
    class MyListener extends ViewStateListener
    {
        override def onSelectedMonthChange(newMonth: Int): Unit = {
            setMonthTitle(newMonth)
        }
    }
    /* ---------------------------------------------------------------------- */
}
