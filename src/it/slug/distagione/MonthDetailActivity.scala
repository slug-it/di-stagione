package it.slug.distagione

import android.os.Bundle
import android.support.v4.app.FragmentActivity

class MonthDetailActivity extends FragmentActivity {

    override def onCreate(savedInstanceState: Bundle) : Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.month_detail)
    }
}
