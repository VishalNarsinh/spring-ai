package com.example.springai.rag;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JavaPDFLoader {

	private final QdrantVectorStore vectorStore;
	@Value("classpath:Eazybytes_HR_Policies.pdf")
	Resource policyFile;

	public JavaPDFLoader(QdrantVectorStore vectorStore) {this.vectorStore = vectorStore;}

	@PostConstruct
	public void loadPDF() {
		TikaDocumentReader documentReader = new TikaDocumentReader(policyFile);
		List<Document> extractedDocuments  = documentReader.get();
		log.info("Loaded {} documents", extractedDocuments .size());
		TextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(100).withMaxNumChunks(400).build();
		List<Document> documentChunks  = textSplitter.split(extractedDocuments );
		log.info("Chunks created: {}", documentChunks.size());
		vectorStore.add(documentChunks);
	}
}
