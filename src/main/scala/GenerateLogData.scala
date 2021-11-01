/*
 *
 *  Copyright (c) 2021. Mark Grechanik and Lone Star Consulting, Inc. All rights reserved.
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under
 *   the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *   either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 */

import Generation.{LogMsgSimulator, RandomStringGenerator}
import HelperUtils.{CreateLogger, Parameters}
import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}

import java.io.File
import collection.JavaConverters.*
import scala.concurrent.{Await, Future, duration}
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

object GenerateLogData:
  val logger = CreateLogger(classOf[GenerateLogData.type])

//this is the main starting point for the log generator
@main def runLogGenerator =
//  import Generation.RSGStateMachine.*
//  import Generation.*
//  import HelperUtils.Parameters.*
//  import GenerateLogData.*
//
//  logger.info("Log data generator started...")
//  val INITSTRING = "Starting the string generation"
//  val init = unit(INITSTRING)
//
//  val logFuture = Future {
//    LogMsgSimulator(init(RandomStringGenerator((Parameters.minStringLength, Parameters.maxStringLength), Parameters.randomSeed)), Parameters.maxCount)
//  }
//  Try(Await.result(logFuture, Parameters.runDurationInMinutes)) match {
//    case Success(value) => logger.info(s"Log data generation has completed after generating ${Parameters.maxCount} records.")
//    case Failure(exception) => logger.info(s"Log data generation has completed within the allocated time, ${Parameters.runDurationInMinutes}")
//  }
  val bucket_name: String = "logsinput"
  val file_path: String = "log/log.log"
  val key_name: String ="log.log"
  System.out.println(new File(file_path).isFile)
  val s3: AmazonS3 = AmazonS3ClientBuilder.standard.withRegion(Regions.US_EAST_2).build
  try s3.putObject(bucket_name, key_name, new File(file_path))
  catch {
    case e: AmazonServiceException =>
      System.err.println(e)
      System.exit(1)
  }
