package it.slug.distagione

import android.graphics.Color
import android.os.Bundle
import org.scaloid.common._

class ListaDiStagione extends SActivity {
  onCreate {
    contentView = new SVerticalLayout {
      STextView("Hello from Scaloid")
      val produces = SArrayAdapter("Primo", "Secondo", "Terzo").style(_.textColor(Color.YELLOW))
      SListView().setAdapter(produces)
    }
  }
}
