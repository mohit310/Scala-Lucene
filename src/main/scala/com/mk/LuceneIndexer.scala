package com.mk

import com.mk.data.SampleData
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{Document, Field, StoredField, TextField}
import org.apache.lucene.index.IndexWriterConfig.OpenMode
import org.apache.lucene.index.{DirectoryReader, IndexReader, IndexWriter, IndexWriterConfig}
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.RAMDirectory


class LuceneIndexer {


  val directory = new RAMDirectory()
  val analyzer = new StandardAnalyzer()
  val writer = new IndexWriter(directory, new IndexWriterConfig(analyzer).setOpenMode(OpenMode.CREATE))

  def getValue(x: Any, field: String): String = {
    val methodName = x.getClass.getMethod(field)
    methodName.invoke(x).toString
  }

  def index(x: SampleData, indexField: String) = {
    val doc = new Document()
    val jsString = ObjectToJsonConverter.convert(x)
    doc.add(new TextField("name", getValue(x, indexField), Field.Store.YES))
    doc.add(new StoredField("json", jsString))
    writer.addDocument(doc)
    writer.commit()
    writer.close()
  }

  def search(indexField: String, searchCriteria: String): String = {
    val query = new QueryParser(indexField, analyzer).parse(searchCriteria)
    val indexReader = DirectoryReader.open(directory)
    val searcher = new IndexSearcher(indexReader)
    val results = searcher.search(query, 100)
    val hits = results.scoreDocs
    println("Total hits:" + results.totalHits)
    results.totalHits match {
      case 0 =>
        ""
      case _ =>
        val doc = searcher.doc(hits(0).doc)
        val data = doc.toString
        indexReader.close()
        data
    }
  }

}
