
import com.mk.data.SampleData
import org.specs2.Specification
import play.api.libs.json.Json


/**
 * Created by mohit on 6/24/15.
 */
class ObjectToJsonTest extends Specification {
  def is = s2"""

  This is a specification for the 'Object to JSON' string

  The Any Object should
    convert to Json string                             $e1
                                                      """

  def e1 = Json.toJson(SampleData("mohit", 13)).toString() == "{\"name\":\"mohit\",\"age\":13}"


}
