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
package co.orderly.scalapac

import _root_.java.net.URLEncoder

import _root_.javax.crypto.spec.SecretKeySpec
import _root_.javax.crypto.Mac

import _root_.org.apache.commons.codec.binary.Base64

import scala.collection.immutable.TreeMap

/**
 * A helper class to sign a given set of parameters ready for the Amazon Product API.
 */
class RequestSignatureHelper(awsAccessKeyId:  String,
                             awsSecretKey:    String,
                             endPoint:        String,
                             requestUri:      String,
                             requestMethod:   String = "GET"
                             )
{
  val UTF8_CHARSET = "UTF-8";
  val HMAC_SHA256_ALGORITHM = "HmacSHA256";

  /**
   * Takes an ordered map of parameters, signs it and returns the map
   * of parameters with the signature appended.
   */
  def sign(params: TreeMap[String, String]): TreeMap[String, String] = {

    val stringToSign = List(requestMethod,
                            endPoint,
                            requestUri,
                            canonicalize(params)
                            ).mkString("\n")

    params.insert("Signature", digest(stringToSign)) // Return TreeMap with Signature on the end
  }

  /**
   * Returns a canonicalized, escaped string of &key=value pairs from an ordered map of parameters
   */
  def canonicalize(params: TreeMap[String, String]): String = {

    params.map(
      (param) => escape( param._1 ) + "=" + escape(param._2)
    ).mkString("&")
  }

  /**
   * Returns the digest for a given string
   */
  protected def digest(x: String): String = {

    try {
      val mac = Mac.getInstance(HMAC_SHA256_ALGORITHM)
      val secretKeySpec = new SecretKeySpec(awsSecretKey.getBytes(UTF8_CHARSET), HMAC_SHA256_ALGORITHM);

      mac.init(secretKeySpec)

      val data = x.getBytes(UTF8_CHARSET)
      val rawHmac = mac.doFinal(data)

      val encoder = new Base64()
      return encoder.encodeToString(rawHmac).trim()

    } catch { // User has forgotten to include Base64()
      case e:NoClassDefFoundError => throw new RuntimeException("Cannot find apache.commons.codec.binary.Base64")
      case e => throw new RuntimeException(UTF8_CHARSET + " is unsupported")
    }
  }

  /**
   * Returns an escaped string
   */
  protected def escape(s: String): String = {

    try {
      return URLEncoder.encode(s, UTF8_CHARSET).replace("+", "%20").replace("*", "%2A").replace("%7E", "~")
    } catch  {
      case e => throw new RuntimeException(UTF8_CHARSET + " is unsupported")
    }
  }
}