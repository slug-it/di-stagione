package it.slug.distagione

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface


class ListaDiStagione extends Activity with TypedViewHolder {
  /* The val is `lazy` because the context is not ready during the initialization
   * but will be ready when `onCreate` will be triggered */
  lazy val tf_title = Typeface.createFromAsset(getAssets, "fonts/OverlockSC-Regular.ttf")
  lazy val tf_element = Typeface.createFromAsset(getAssets, "fonts/SortsMillGoudy-Regular.ttf")
  override def onCreate(saved: Bundle) : Unit = {
    super.onCreate(saved)
    setContentView(R.layout.main)
    findView(TR.text).setTypeface(tf_title)
  }
}
