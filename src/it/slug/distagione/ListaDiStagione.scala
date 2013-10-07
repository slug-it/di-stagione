package it.slug.distagione

import android.os.Bundle
import org.scaloid.common._

class ListaDiStagione extends SActivity {
  onCreate {
    contentView = new SVerticalLayout {
      STextView("Hello from Scaloid")
      SListView().setAdapter(SArrayAdapter("Primo", "Secondo", "Terzo"))
    }
  }
}
