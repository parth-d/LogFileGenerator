import java.text.SimpleDateFormat
import java.time.{Duration, Instant}
import scala.collection.mutable.ListBuffer
import scala.io.Source

object parth:
  val list = List.range(0, 50)

  @main def main = {
    val file = Source.fromResource("LogFileGenerator.2021-10-29.log").getLines().toList
    val median = SimpleDateFormat("HH:mm:ss.SSS").parse("13:19:07.000").toInstant
    val range = 4
    val logPresent = findelem(file, median, range, 0, file.length)
    if(logPresent != -1){
//      println("Starting for index: " + logPresent)
      val indices = getIndices(file, logPresent, median, range).toList
      println(indices.sorted)
    }
  }


  def findelem(arr: List[String], medianTime: Instant, range: Int, low: Int, high: Int) : Int = {
    if (low > high) return -1
    val middle = low + (high - low)/2
    val midTime = SimpleDateFormat("HH:mm:ss.SSS").parse(arr(middle).split(" ")(0)).toInstant
    if (Duration.between(midTime, medianTime).getSeconds.abs <= range) return middle
    else if (midTime.compareTo(medianTime) == 1) return findelem(arr, medianTime, range, low, middle - 1)
    else return findelem(arr, medianTime, range, middle + 1, high)
  }

  def getIndices(arr: List[String], index: Int, medianTime: Instant, range: Int, lbuffer: ListBuffer[Int] = new ListBuffer[Int]) : ListBuffer[Int] = {
    val midTime = SimpleDateFormat("HH:mm:ss.SSS").parse(arr(index).split(" ")(0)).toInstant
    val difference = Duration.between(midTime, medianTime).toMillis.abs
//    println("getIndices:\t" + "Index: " + index + "\tmidTime: " + midTime.toString + "\tmedianTime: " + medianTime.toString + "\tdifference: " + difference)
    if (difference <= range * 1000) {
      lbuffer.addOne(index)
      getIndicesBefore(arr, index - 1, medianTime, range, lbuffer)
      getIndicesAfter(arr, index + 1, medianTime, range, lbuffer)
    }
    return lbuffer
  }

  def getIndicesBefore(arr: List[String], index: Int, medianTime: Instant, range: Int, lbuffer: ListBuffer[Int]) : ListBuffer[Int] = {
    val midTime = SimpleDateFormat("HH:mm:ss.SSS").parse(arr(index).split(" ")(0)).toInstant
    val difference = Duration.between(midTime, medianTime).toMillis.abs
//    println("getIndicesBefore:\t" + "Index: " + index + "\tmidTime: " + midTime.toString + "\tmedianTime: " + medianTime.toString + "\tdifference: " + difference)
    if (difference <= range * 1000) {
      lbuffer.addOne(index)
      getIndicesBefore(arr, index - 1, medianTime, range, lbuffer)
    }
    return lbuffer
  }
  def getIndicesAfter(arr: List[String], index: Int, medianTime: Instant, range: Int, lbuffer: ListBuffer[Int]) : ListBuffer[Int] = {
    val midTime = SimpleDateFormat("HH:mm:ss.SSS").parse(arr(index).split(" ")(0)).toInstant
    val difference = Duration.between(midTime, medianTime).toMillis.abs
//    println("getIndicesAfter:\t" + "Index: " + index + "\tmidTime: " + midTime.toString + "\tmedianTime: " + medianTime.toString + "\tdifference: " + difference)
    if (difference <= range * 1000) {
      lbuffer.addOne(index)
      getIndicesAfter(arr, index + 1, medianTime, range, lbuffer)
    }
    return lbuffer
  }