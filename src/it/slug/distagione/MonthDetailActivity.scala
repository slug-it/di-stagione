package it.slug.distagione

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager


class MonthDetailActivity extends FragmentActivity with TypedViewHolder {

    override def onCreate(savedInstanceState: Bundle) : Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.month_detail)

        val fragManager = getSupportFragmentManager()
        val pagerAdapter = new MonthDetailPagerAdapter(fragManager, this)
        findView(TR.month_pager).setAdapter(pagerAdapter)
    }
}
