import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.amazonaws.regions.Regions
import com.typesafe.config.ConfigFactory

import java.io.File

object putS3:
  val config = ConfigFactory.load()
  val bucket_name: String = config.getString("s3.bucket")
  val file_path: String = config.getString("s3.file_path")
  val key_name: String = config.getString("s3.key")
  
  val s3: AmazonS3 = AmazonS3ClientBuilder.standard.withRegion(Regions.US_EAST_2).build
  try s3.putObject(bucket_name, key_name, new File(file_path))
    
  catch {
    case e: AmazonServiceException =>
      System.err.println(e)
      System.exit(1)
  }