package duse13.rest

import org.joda.time._
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import org.joda.time.format._
import org.joda.time.DateTime
import net.liftweb.json.JsonAST.{ JValue, JArray }
import net.liftweb.json.ext.JodaTimeSerializers
import io._
import duse13.serialize._
import util._
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.ShouldMatchers
import scala.xml._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.util.control.Exception

case class Person(val id: Option[Long], val name: String, val birthdate: DateTime, val hobbies: List[String])

object Person extends JSONSerializer[Person] with XMLSerializer[Person] {

  implicit val timeXmlFormat = new XmlFormat[Person] {

    override def fromXml(elem: Elem): Person = {
      require(elem != null, "xml must not be null!")
      val idAttr = (elem \\ "id")
      val id = if (!idAttr.text.isEmpty) Some(idAttr.text.toLong) else None
      val name = (elem \\ "name").text
      val birthdate = fmt.parseDateTime((elem \\ "birthdate").text)
      val hobbies = for (hobby <- (elem \\ "hobbies" \ "hobby")) yield hobby.text

      Person(id, name, birthdate, hobbies.toList)
    }

    override def toXml(person: Person): Elem = {
      <person>
        { person.id.map(id => <id>{ id }</id>).getOrElse(<id/>) }
        <name>{ person.name }</name>
        <birthdate>{ person.birthdate }</birthdate>
        <hobbies>
          { person.hobbies.map(hobby => { <hobby>{ hobby }</hobby> }) }
        </hobbies>
      </person>
    }
  }
  private def toInt(attr: Node): Option[Int] = Exception.catching(classOf[NumberFormatException]) opt { attr.text.toInt }

}