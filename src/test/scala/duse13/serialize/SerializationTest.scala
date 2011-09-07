package duse13.serialize

import scala.reflect.BeanInfo
import org.joda.time._
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import org.joda.time.format._
import org.joda.time.DateTime
import net.liftweb.json.JsonAST.{ JValue, JArray }
import net.liftweb.json.ext.JodaTimeSerializers
import io._
import duse13.rest.{Person}
import util._
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers
import scala.xml._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.util.control.Exception

@RunWith(classOf[JUnitRunner])
class SerializationTest extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {

  val pp = new PrettyPrinter(80, 5)

  val jsonFile: String = fileFromClasspathAsString("person.json")
  val xmlFile = XML.load(fileFromClasspathAsInputStream("person.xml"))
  val expected = Person(Some(1), "John Doe", fmt.parseDateTime("1975-07-01T18:58:54.812Z"), List("Scala programming", "Lift", "Akka"))
 val expected2 = Person(None, "John Doe", fmt.parseDateTime("1975-07-01T18:58:54.812Z"), List("Scala programming", "Lift", "Akka"))

  it should "deserialize a person from json correctly" in {
    val p = Person.fromJson(jsonFile)
    expected.toString should be(p.toString)
  }
  it should "serialize a person to json correctly" in {
    val pJson = expected.toJson
    pJson should be("""{"id":1,"name":"John Doe","birthdate":"1975-07-01T18:58:54.812Z","hobbies":["Scala programming","Lift","Akka"]}""")
  }

  it should "deserialize a person from xml correctly" in {
    val p = Person.fromXml(xmlFile)
    expected.toString should be(p.toString)
    
    Person.fromXml(expected2.toXml)

  }

  it should "serialize a person to xml correctly" in {
    pp.formatNodes(xmlFile) should be(pp.formatNodes(expected.toXml))
  }

}