package it.slug.distagione;

import android.app.Activity;
import android.os.Bundle;


class ItemDetailActivity extends Activity {

    override def onCreate(savedInstanceState: Bundle) : Unit = {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
    }
}
