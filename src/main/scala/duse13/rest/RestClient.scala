package duse13.rest

import dispatch._
import scala.reflect.BeanInfo
import scala.xml._
import scala.io._
import java.util.Date

object RestClient {

  val host = "localhost"
  val port = 8080
  // def searchPerson(isXML:Boolean)(name:String):Option[Person] = {
  private def searchPerson(isXML: Boolean)(name: String): Option[Person] = {
    val http = new Http
    http(:/(host, port) / createQuery("search", isXML) <<? Map("q" -> name) >~ { resp => toPerson(resp.getLines.mkString, isXML) })

  }
  def searchPersonXML(name: String) = searchPerson(true)(name)
  def searchPersonJson(name: String) = searchPerson(false)(name)
  

  private def updatePerson(isXML: Boolean)(p: Person): Unit = {
    val http = new Http
    if (isXML)
      http(:/(host, port).PUT / createQuery("update", isXML) <<< p.toXml.toString >~ { resp => resp.getLines.mkString })
    else
      http(:/(host, port).PUT / createQuery("update", isXML) <<< p.toJson >~ { resp => resp.getLines.mkString })
  }
  def updatePersonXML(p: Person): Unit = updatePerson(true)(p)

  def updatePersonJson(p: Person): Unit = updatePerson(false)(p)

  private def addPerson(isXML: Boolean)(p: Person): Person = {
    val http = new Http
   if (isXML)
      http(:/(host, port).POST / createQuery("add", isXML) << p.toXml.toString >~ { resp => toPerson(resp.getLines.mkString, isXML).get })
    else
      http(:/(host, port).POST / createQuery("add", isXML) << p.toJson >~ { resp => toPerson(resp.getLines.mkString, isXML).get })

  }

  def addPersonXML(p: Person): Person = addPerson(true)(p)

  def addPersonJson(p: Person): Person = addPerson(false)(p)
  private def createQuery(uri: String, isXML: Boolean): String = uri + (if (isXML) ".xml" else ".json")

  private def toPerson(result: String, isXML: Boolean):Option[Person] = {
    try {
    if (isXML) Some(Person.fromXml(XML.loadString(result))) else Some(Person.fromJson(result))
    } catch {
      case e:Exception => None
    }
  }

  def deletePerson(id: Long) = {
    val http = new Http
    http(:/(host, port).DELETE / ("delete/" + id.toString) >~ { resp => resp.getLines.mkString })

  }
}