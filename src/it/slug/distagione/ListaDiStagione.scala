package it.slug.distagione

import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface

import org.scaloid.common._
import scala.language.postfixOps

object ListaDiStagione {
  var tf_title: Typeface = null
  var tf_element: Typeface = null
}

class ListaDiStagione extends SActivity {
  onCreate {
    // FIXME: do this one time setup in the constructor
    if (ListaDiStagione.tf_title == null) {
      ListaDiStagione.tf_title = Typeface.createFromAsset(getAssets(), "fonts/OverlockSC-Regular.ttf")
      ListaDiStagione.tf_element = Typeface.createFromAsset(getAssets(), "fonts/SortsMillGoudy-Regular.ttf")
    }
    contentView = new SVerticalLayout {
      STextView("Hello from Scaloid").typeface(ListaDiStagione.tf_title).textSize(18 sp)
      val produces = SArrayAdapter("Primo", "Secondo", "Terzo").style(_.textColor(Color.YELLOW).typeface(ListaDiStagione.tf_element))
      SListView().setAdapter(produces)
    }
  }
}
