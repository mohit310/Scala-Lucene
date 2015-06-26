import com.mk.LuceneFactory
import com.mk.data.SampleData
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.{IndexWriter, IndexWriterConfig}
import org.apache.lucene.index.IndexWriterConfig.OpenMode
import org.apache.lucene.store.RAMDirectory

/**
 * Created by mohit on 6/24/15.
 */
object LuceneMain {

  def main(args: Array[String]): Unit = {
    val luceneIndexer = LuceneFactory.getInstance()
    val sample = new SampleData("Mohit", 23)
    luceneIndexer.index(sample, "name")
    val searchDoc = luceneIndexer.search("name", "Mohit")
    println(searchDoc)
  }
}
