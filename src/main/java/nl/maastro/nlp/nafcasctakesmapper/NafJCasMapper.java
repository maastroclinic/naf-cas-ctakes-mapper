package nl.maastro.nlp.nafcasctakesmapper;


import ixa.kaflib.KAFDocument;
import ixa.kaflib.Term;
import ixa.kaflib.WF;
import org.apache.ctakes.core.ae.TokenConverter;
import org.apache.ctakes.core.util.DocumentIDAnnotationUtil;
import org.apache.ctakes.typesystem.type.syntax.BaseToken;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.apache.ctakes.core.nlp.tokenizer.Token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class NafJCasMapper {

	final static Logger logger = LoggerFactory.getLogger(NafJCasMapper.class);

	public static JCas getJCas(KAFDocument kafDocument){

		JCas jcas = null;
		try {
			jcas = JCasFactory.createJCas();
			jcas.setDocumentLanguage(kafDocument.getLang());
			jcas.setDocumentText(kafDocument.getRawText());
			setTokens(kafDocument, jcas);
			logger.debug("CAS object generated");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Cas object could not be generated");
		}
		return jcas;
	}


	private static JCas setTokens(KAFDocument kafDocument, JCas jcas){

        List<WF> wfs = kafDocument.getWFs();
		for (WF wf : wfs) {
			
		}

//		Iterator<Token> tokenItr = tokens.iterator();
//		while (tokenItr.hasNext()) {
//			Token token = tokenItr.next();
//
//			// convert token into JCas object
//			BaseToken bta = TokenConverter.convert(token, jcas, beginPos);
//
//			bta.setTokenNumber();
//
//			// add JCas object to CAS index
//			bta.addToIndexes();
//
//		}

		return jcas;
	}





}
