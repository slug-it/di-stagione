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


class ListaDiStagione extends FragmentActivity with TypedViewHolder {
    /* The val is `lazy` because the context is not ready during the
       initialization but will be ready when `onCreate` will be triggered */

    /*
    lazy val tf_title = Typeface.createFromAsset(
        getAssets, "fonts/OverlockSC-Regular.ttf")
    */
    lazy val tf_element = Typeface.createFromAsset(
        getAssets, "fonts/SortsMillGoudy-Regular.ttf")

    override def onCreate(saved: Bundle) : Unit = {
        super.onCreate(saved)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main)

        // widgets
        // findView(TR.text).setTypeface(tf_title)
        val pagerWidget = findView(TR.month_pager)
        val monthBar = findView(TR.month_bar)

        // controllers
        val pagerController = new MonthDetailPagerController(pagerWidget, this)
        val barController = new MonthBarController(monthBar, this)
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
}
