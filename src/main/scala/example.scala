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
import co.orderly.scalapac.OperationHelper

/**
 * Simple console example of an Amazon Product API call using scalapac
 */
object ExampleItemSearch {
  
  def main(args: Array[String]) {

    // Update this with your Amazon credentials before running
  val opHelper = new OperationHelper(awsAccessKeyId     = "[YOUR AWS ID HERE]",
                                     awsSecretKey       = "[YOUR AWS SECRET HERE]",
                                     awsAssociateTagKey = "[YOUR ASSOCIATE TAG HERE]"
                                     )

    // Or val (code, xml) = opHelper.execute(...
    opHelper.debug("ItemSearch", Map("SearchIndex"    -> "Books",
                                     "Keywords"       -> "harry potter",
                                     "ResponseGroup"  -> "ItemAttributes,Offers"
                                     ))
  }
}
