package it.slug.distagione;

import android.app.Activity
import android.os.Bundle
import android.widget.TextView


class ItemDetailActivity extends Activity with TypedViewHolder {

    override def onCreate(savedInstanceState: Bundle) : Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail)

        findView(TR.item_name).setText("Nome Item")
        findView(TR.item_img).setImageResource(R.drawable.strawberry)
        findView(TR.item_desc).setText(
            "Lorem ipsum dolor sit amet, consectetur " +
            "adipiscing elit. Phasellus scelerisque ligula " +
            "malesuada, hendrerit nisl pellentesque, dictum dui" +
            " Ut eget elit nec ligula scelerisque porttitor.")
    }
}

