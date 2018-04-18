package nl.maastro.nlp.nafcasctakesmapper;

import ixa.kaflib.KAFDocument;
import org.apache.ctakes.core.cc.XMISerializer;
import org.apache.ctakes.core.cc.XmiWriterCasConsumerCtakes;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


public class NafJCasMapperTests {

    private KAFDocument kafDocument;

    @Test
	public void testNafToJCas() throws AnalysisEngineProcessException, ResourceInitializationException {

        JCas jcas = NafJCasMapper.getJCas(kafDocument);

        AnalysisEngine xWriter = AnalysisEngineFactory.createEngine(
                XmiWriterCasConsumerCtakes.class,
                XmiWriterCasConsumerCtakes.PARAM_OUTPUTDIR,
                "./out");

        xWriter.process(jcas);
	}

	@Before
	public void init() throws IOException {
		File file = new File(NafJCasMapperTests.this.getClass().getClassLoader().getResource("wikinews_1173_en.naf").getFile());
		kafDocument = KAFDocument.createFromFile(file);
	}

}
