import java.text.SimpleDateFormat
import java.time.{Duration, Instant}
import scala.io.Source

object parth:
  val list = List.range(0, 50)

  @main def main = {
    val file = Source.fromResource("LogFileGenerator.2021-10-29.log").getLines().toList
    val median = SimpleDateFormat("HH:mm:ss.SSS").parse("13:19:07.000").toInstant
    val range = 1
    print(findelem(file, median, range, 0, file.length))
  }


  def findelem(arr: List[String], medianTime: Instant, range: Int, low: Int, high: Int) : Boolean = {
    if (low > high) return false
    val middle = low + (high - low)/2
    val midTime = SimpleDateFormat("HH:mm:ss.SSS").parse(arr(middle).split(" ")(0)).toInstant
    if (Duration.between(midTime, medianTime).getSeconds.abs <= range) return true
    else if (midTime.compareTo(medianTime) == 1) return findelem(arr, medianTime, range, low, middle - 1)
    else return findelem(arr, medianTime, range, middle + 1, high)
  }