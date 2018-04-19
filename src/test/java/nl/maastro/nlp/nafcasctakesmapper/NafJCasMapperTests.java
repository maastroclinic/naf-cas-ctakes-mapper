package nl.maastro.nlp.nafcasctakesmapper;

import ixa.kaflib.KAFDocument;
import org.apache.ctakes.core.cc.XmiWriterCasConsumerCtakes;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.ResultSpecification;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
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

        TypeSystemDescription typeSystemDescription =
                // use the uimafit method of finding available type system
                // descriptor via META-INF/org.apache.uima.fit/types.txt
                // (found in ctakes-type-system/src/main/resources)
                TypeSystemDescriptionFactory.createTypeSystemDescription();

        AnalysisEngine xWriter = AnalysisEngineFactory.createEngine(
                XmiWriterCasConsumerCtakes.class,
                typeSystemDescription,
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
