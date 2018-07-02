import play.sbt.PlayJava
import sbt.Credentials
import sbt.Keys.libraryDependencies

name := "apibasev2"
version := "0.1"
organization := "ro.iss"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
scalaVersion := "2.12.4"

//activate default play used libraries
libraryDependencies ++= Seq(
  guice,
  ws,
  )
  


publishArtifact in Test := false

// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)

// Java project. Don't expect Scala IDE
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           

// Use .class files instead of generated .scala files for views and routes
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  

