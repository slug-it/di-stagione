import android.Keys._

android.Plugin.androidBuild

name := "di-stagione"

scalaVersion := "2.10.3"

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize")

libraryDependencies += "org.scaloid" %% "scaloid" % "2.4-8"

scalacOptions in Compile += "-feature"

run <<= run in Android

install <<= install in Android
