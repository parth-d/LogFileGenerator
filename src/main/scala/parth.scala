import java.text.SimpleDateFormat
import java.time.{Duration, Instant}
import scala.io.Source

object parth:
  val list = List.range(0, 50)

  @main def main = {
//    print(findelem(list, 14))
    val file = Source.fromResource("LogFileGenerator.2021-10-29.log").getLines().toList
    println("File Lines: " + file.length)
    val median = SimpleDateFormat("HH:mm:ss.SSS").parse("13:19:07.000").toInstant
    val range = 1
    print(findelem(file, median, range, 0, file.length))
//    print(upper_limit)
  }


  def findelem(arr: List[String], medianTime: Instant, range: Int, low: Int, high: Int) : Int = {
    if (low > high) return -1
    val middle = low + (high - low)/2
    val midTime = SimpleDateFormat("HH:mm:ss.SSS").parse(arr(middle).split(" ")(0)).toInstant
    System.out.println(low + "\t" + high + "\t" + middle.toString + "\t" + midTime.toString + "\t" + medianTime.toString)
    if (Duration.between(midTime, medianTime).getSeconds.abs <= range) return middle
    else if (midTime.compareTo(medianTime) == 1) return findelem(arr, medianTime, range, low, middle - 1)
    else return findelem(arr, medianTime, range, middle + 1, high)
  }