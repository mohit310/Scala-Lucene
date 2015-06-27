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

  private def writeIndex(iw: IndexWriter => Unit) = {
    iw(writer)
    writer.commit()
    writer.close()
  }

  def index(x: SampleData, indexField: String) = {
    val doc = new Document()
    val jsString = ObjectToJsonConverter.convert(x)
    doc.add(new TextField("name", getValue(x, indexField), Field.Store.YES))
    doc.add(new StoredField("json", jsString))
    writeIndex(indexWriter => indexWriter.addDocument(doc))
  }

  private def readSearcher(isearch: IndexSearcher => String) = {
    val indexReader = DirectoryReader.open(directory)
    val searcher = new IndexSearcher(indexReader)
    val data = isearch(searcher)
    indexReader.close()
    data
  }

  def search(indexField: String, searchCriteria: String) = {
    val query = new QueryParser(indexField, analyzer).parse(searchCriteria)
    readSearcher(indexSearcher => {
      val results = indexSearcher.search(query, 100)
      val hits = results.scoreDocs
      println("Total hits:" + results.totalHits)
      if (results.totalHits > 0) {
        val doc = indexSearcher.doc(hits(0).doc)
        doc.toString
      } else
        ""
    })
  }

}
