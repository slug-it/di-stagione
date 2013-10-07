package it.slug.distagione

import android.app.Activity
import android.os.Bundle

class ListaDiStagione extends Activity
{
    /** Called when the activity is first created. */
    override def onCreate(savedInstanceState: Bundle) : Unit =
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
    }
}
