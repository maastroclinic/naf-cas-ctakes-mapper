package nl.maastro.nlp.nafcasctakesmapper;

import ixa.kaflib.KAFDocument;
import org.apache.ctakes.core.cc.XmiWriterCasConsumerCtakes;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class NafJCasMapperTests {

    private KAFDocument kafDocument;
    private JCas jcasFromFile;

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
    public void init() throws IOException, UIMAException, SAXException {
        File kafDocument = new File(NafJCasMapperTests.this.getClass().getClassLoader().getResource("wikinews_1173_en.naf").getFile());
        this.kafDocument = KAFDocument.createFromFile(kafDocument);


//        File xmiFile = new File(NafJCasMapperTests.this.getClass().getClassLoader().getResource("wikinews_1173_en.xmi").getFile());
//        FileInputStream inputStream = new FileInputStream(xmiFile);
//        this.jcasFromFile = JCasFactory.createJCas();
//
//        XmiCasDeserializer.deserialize(inputStream, jcasFromFile.getCas());
//        inputStream.close();
    }


}
