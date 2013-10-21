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
- Connettere il telefono e lanciare `adb install bin/di-stagione-debug.apk`
