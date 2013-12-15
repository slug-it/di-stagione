Di stagione
===========

App per avere a portata di mano frutta e verdura di stagione, branch di sviluppo per provare scala.

Design in corso, per seguire lo sviluppo: [Trello Board](https://trello.com/board/di-stagione/5150c9051ff9b8d161008df7 "Trello Board - Di stagione")

Requirements
============

- `export ANDROID_HOME=/opt/adt-bundle-linux/sdk/`
- `export PATH=$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH`
- Installare [scala-sbt](http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html)
- `~$ sbt android:package` scarica tutte le dipendenze ed i plugin e genera un file .apk da mandare al telefono/emulatore
- Connettere il telefono e lanciare `adb install -r bin/di-stagione-debug.apk`

Reference
=========

Di solito le liste vengono popolate con dei template presenti nell'sdk:
- http://stackoverflow.com/questions/3663745/what-is-android-r-layout-simple-list-item-1

Per creare dei "componenti" è possibile 1) includere un layout xml 2) estendere una View/Layout:
- http://stackoverflow.com/questions/2289730/android-layout-is-reusable-component-ui-possible
- http://developer.android.com/guide/topics/ui/custom-components.html

Esempi dell'SDK tradotti in scala (senza nessuna estensione particolare)
- https://github.com/michelou/android-examples/tree/master/android-sdk

Temi per widget custom
- http://stackoverflow.com/questions/4493947/how-to-define-theme-style-item-for-custom-widget
- https://thenewcircle.com/s/post/1444/styles_and_themes
