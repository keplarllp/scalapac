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
package co.orderly

/**
 * Provides a pair of classes for interacting with the Amazon Product API from Scala.
 *
 * ==Overview==
 * The main class to use is [[main.scala.co.orderly.scalapac.scalapac.OperationHelper]]. Create an instance
 * with your Amazon API connection details like so:
 *
 * val opHelper = new OperationHelper(awsAccessKeyId     = "Enter here",
 *                                    awsSecretKey       = "Enter here",
 *                                    awsAssociateTagKey = "Enter here"
 *                                    )
 *
 * Once you have created an opHelper, you can then run operations against the Amazon Product
 * API using the execute command:
 *
 * val (code, xml) = opHelper.execute("ItemSearch", Map("SearchIndex"    -> "Books",
 *                                                      "Keywords"       -> "harry potter",
 *                                                      "ResponseGroup"  -> "ItemAttributes,Offers"
 *                                                     ))
 *
 * For testing queries in the Scala console there is also a helper method, use like so:
 *
 * scala> opHelper.debug("ItemSearch", ...)
 */
package object scalapac {
}
