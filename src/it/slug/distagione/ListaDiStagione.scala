package it.slug.distagione

import android.os.Bundle
import org.scaloid.common._

class ListaDiStagione extends SActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    contentView = new SVerticalLayout {
      STextView("Hello from Scaloid")
    }
  }
}
