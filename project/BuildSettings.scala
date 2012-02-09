/* Distributed as part of scalapac, an Amazon Product API client for Scala.
 *
 * Copyright (c) 2012 Orderly Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
import sbt._
import Keys._

object BuildSettings {

  // Basic settings for our app
  lazy val basicSettings = Seq[Setting[_]](
    organization  := "co.orderly",
    version       := "0.2",
    description   := "A Scala client library for the Amazon Product Advertising API",
    scalaVersion  := "2.9.1",
    scalacOptions := Seq("-deprecation", "-encoding", "utf8")
  )

  // Proguard settings for packaging
  import ProguardPlugin._
  lazy val proguard = proguardSettings ++ Seq(
    proguardOptions := Seq(
      "-keepattributes *Annotation*,EnclosingMethod",
      "-dontskipnonpubliclibraryclassmembers",
      "-dontoptimize",
      "-dontshrink"
    )
  )

  lazy val scalapacSettings = basicSettings ++ proguard
}