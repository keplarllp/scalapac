scalapac - a Scala client for the Amazon Product Advertising API.

scalapac (Scala Amazon Product Advertising Client) allows you to access the Amazon Product Advertising API from Scala. [Learn more about the Amazon Product Advertising API](https://affiliate-program.amazon.com/gp/advertising/api/detail/main.html).

scalapac is a thin wrapper around Amazon's API: it takes care of the request signatures, performs the HTTP requests, processing Amazon's response and then returns the response code and XML in a tuple ready for you to work with in Scala. scalapac is "operation agnostic": you simply pass in the operation (as a String) and the operation's parameters (as a Map) and receive the response in XML. So it's like you're working directly with the API, but without the hassle of signing your requests, handling 403s errors from Amazon and so on.

#Credits

scalapac is a near-enough port of @dmcquay's excellent [node-apac](https://github.com/dmcquay/node-apac), an equivalent Amazon Product Advertising Client for node.js. It also leans heavily on Alex Parvulescu's [Amazon Product Api REST Client in Scala](http://blog.pfa-labs.com/2009/08/amazon-product-api-rest-client-in-scala.html) blog post for the Scala request signing code. Many thanks Dustin and Alex.

#Installation

The latest build of scalapac is always available as a .jar from the Downloads dropdown in GitHub. This version was built against Scala 2.8.1 using sbt 0.7.5

Note that the request signing in scalapac depends on the [Apache Commons Codec](http://commons.apache.org/codec/download_codec.cgi) - to run scalapac you will need commons-codec-1.5.jar in your classpath.

#Quick Start

First download and install the scalapac and commons-codec jars. Now fire up scala and load the jars into your classpath (changing the paths as appropriate):

    scala> :cp [YOUR JAR PATH HERE]/commons-codec-1.5.jar
    scala> :cp [YOUR JAR PATH HERE]/scalapac-0.0.1.jar

Now you can import the OperationHelper class:

    scala> import co.orderly.scalapac.OperationHelper

Create an OperationHelper (changing your Amazon credentials):

    val opHelper = new OperationHelper(awsAccessKeyId     = "[YOUR AWS ID HERE]",
                                       awsSecretKey       = "[YOUR AWS SECRET HERE]",
                                       awsAssociateTagKey = "[YOUR ASSOCIATE TAG HERE]"
                                       )

And now you can run a test against the Amazon Product Advertising API:

    opHelper.debug("ItemSearch", Map("SearchIndex"    -> "Books",
                                     "Keywords"       -> "harry potter",
                                     "ResponseGroup"  -> "ItemAttributes,Offers"
                                     ))

If everything is working correctly, you should see an XML print-out of results from the Amazon API, along with a 200 response code.

When using scalapac in production, instead of the debug() method you should use execute(). execute() takes exactly the same arguments as debug() but returns a tuple containing the response code (as an Int in _1) and the XML (as an Elem in _2):

    val (code, xml) = opHelper.execute("ItemSearch", Map(...

#API Documentation

All Amazon Product Advertising API documentation can be found on the AWS website:
[API Reference](http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/index.html?ProgrammingGuide.html)

#Forking 

Forking from GitHub and making changes to the code is easy if you use sbt:

    git clone git://github.com/orderly/scalapac
    cd scalapac/lib
    wget http://apache.mirrors.timporter.net//commons/codec/binaries/commons-codec-1.5-bin.tar.gz
    tar -xzvf commons-code-1.5-bin.tar.gz
    vi src/main/scala/example.scala

Now update example.scala to include your own Amazon credentials, save and finish up with:

    cd ..    
    sbt
    > run

You should see scalapac Compiling main sources, running ExampleItemSearch and then displaying some XML results and a 200 response code.

#Copyright and License

scalapac is copyright (c) 2011-2012 Orderly Ltd

scalapac is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

scalapac is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public
License along with scalapac. If not, see [GNU licenses](http://www.gnu.org/licenses/).
