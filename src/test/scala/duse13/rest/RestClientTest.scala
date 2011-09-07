package duse13.rest

import scala.reflect.BeanInfo
import org.joda.time._
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import org.joda.time.format._
import org.joda.time.DateTime
import net.liftweb.json.JsonAST.{ JValue, JArray }
import net.liftweb.json.ext.JodaTimeSerializers
import io._
import duse13.serialize.util._
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers
import scala.xml._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.util.control.Exception

@RunWith(classOf[JUnitRunner])
class RestClientTest extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {

  val newPerson = Person(None, "Captain Cook", fmt.parseDateTime("1975-07-01T18:58:54.812Z"), List("Scala programming", "Lift", "Akka", "Sailing"))

  it should "crud xml" in {
    //create
    var p = RestClient.addPersonXML(newPerson)
    p.name should be("Captain Cook")
    
    //read create
    var pOption = RestClient.searchPersonXML("Captain Cook")
    pOption should not be(None)
    pOption.get.name should be("Captain Cook")
    
    //update
    RestClient.updatePersonXML(p.copy(name = "Captain Cake"))

    //read update
    pOption = RestClient.searchPersonXML("Captain Cake")
    pOption should not be(None)
    pOption.get.name should be("Captain Cake")   
    
    //delete
    RestClient.deletePerson(p.id.get)
    
    //read delete
    pOption = RestClient.searchPersonXML("Captain Cake")
    pOption should be(None)

  }

  
    it should "crud json" in {
     //create
    var p = RestClient.addPersonJson(newPerson)
    p.name should be("Captain Cook")
    
    //read create
    var pOption = RestClient.searchPersonJson("Captain Cook")
    pOption should not be(None)
    pOption.get.name should be("Captain Cook")
    
    //update
    RestClient.updatePersonJson(p.copy(name = "Captain Cake"))

    //read update
    pOption = RestClient.searchPersonJson("Captain Cake")
    pOption should not be(None)
    pOption.get.name should be("Captain Cake")   
    
    //delete
    RestClient.deletePerson(p.id.get)
    
    //read delete
    pOption = RestClient.searchPersonJson("Captain Cake")
    pOption should be(None)
  }
  

}