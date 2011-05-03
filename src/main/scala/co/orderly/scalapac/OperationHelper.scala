/* Distributed as part of scalapac, an Amazon Product API client for Scala.
 *
 * Copyright (c) 2011 Alex Dean
 *
 * scalapac is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * scalapac is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with scalapac. If not, see <http://www.gnu.org/licenses/>.
 */
package co.orderly.scalapac

import java.text.SimpleDateFormat
import java.util.{Calendar, TimeZone}

import java.net.{URL, URLConnection, HttpURLConnection}
import java.io.{InputStream, IOException}

import scala.collection.immutable.TreeMap
import scala.xml._

/**
 * Instantiate an OperationHelper to start executing operations against the Amazon Product API
 */
class OperationHelper(awsAccessKeyId:       String,
                      awsSecretKey:         String,
                      awsAssociateTagKey:   String,
                      endPoint:             String = "ecs.amazonaws.com",
                      baseUri:              String = "/onca/xml"
                      )
{
  // Definitions we use in the standard params
  val VERSION = "2010-11-01"
  val SERVICE = "AWSECommerceService"

  /**
   * Execute an operation against the Amazon Product API with the supplied arguments
   *
   * Returns a tuple containing the return code and XML contents from the Amazon
   * Product API.
   */
  def execute(operation: String, args: Map[String, String]): Tuple2[Int, Elem] = {

    // Put together the standard params with args for this operation
    val params = generateParams(operation, args)

    // Set up the request signature helper, sign the params and canonicalize
    val helper = new RequestSignatureHelper(awsAccessKeyId = awsAccessKeyId,
                                            awsSecretKey = awsSecretKey,
                                            endPoint = endPoint,
                                            requestUri = baseUri)
    val signedParams = helper.sign(params)
    val queryString = helper.canonicalize(signedParams)

    // Construct the API URL from the above components
    val url = new URL("http://" + endPoint + baseUri + "?" + queryString)

    // Because Amazon can throw 403s which XML.load doesn't like, load in a two-stage process
    val connection = url.openConnection() match {
      case x: HttpURLConnection => x
      case _ => throw new ClassCastException
    }

    // Get the return value
    val responseCode = connection.getResponseCode();

    // Now fetch the XML and return a tuple of the return code and XML
    val data = try {
      connection.getInputStream()
    } catch {
      case e => connection.getErrorStream()
    }

    val xml = XML.load(data)
    return (responseCode, xml)
  }

  /**
   * A wrapper which pretty-prints the XML and prints out the return code.
   * Useful for checking API responses in the console
   */
  def debug(operation: String, args: Map[String, String]) {

    val (code, xml) = execute(operation, args)

    // Now pretty print the XML
    val ppr = new scala.xml.PrettyPrinter(80,2)
    val out = ppr.format(xml)
    Console.println(out)

    // Append the response code
    Console.println("Response code: " + code)
  }

  /**
   * Returns an ordered list of parameters ready for Amazon
   */
  protected def generateParams(operation: String, args: Map[String, String]): TreeMap[String, String] = {

    var params = TreeMap.empty[String, String]

    params += "Version" -> VERSION
    params += "Operation" -> operation
    params += "AWSAccessKeyId" -> awsAccessKeyId
    params += "AssociateTag" -> awsAssociateTagKey
    params += "Timestamp" -> generateTimestamp()

    // Copy in all the args passed into this function
    args foreach ( (arg) => params += arg._1 -> arg._2)

    return params
  }

  /**
   * Returns a timestamp in the format required by Amazon
   */
  protected def generateTimestamp(): String = {

      val dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
      dfm.setTimeZone(TimeZone.getTimeZone("GMT"))
      return dfm.format(Calendar.getInstance().getTime())
  }
}
