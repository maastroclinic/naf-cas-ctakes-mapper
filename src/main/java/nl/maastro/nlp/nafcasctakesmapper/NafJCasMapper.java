package nl.maastro.nlp.nafcasctakesmapper;


import ixa.kaflib.KAFDocument;
import ixa.kaflib.Term;
import ixa.kaflib.WF;
import org.apache.ctakes.core.ae.TokenConverter;
import org.apache.ctakes.core.util.DocumentIDAnnotationUtil;
import org.apache.ctakes.typesystem.type.structured.DocumentID;
import org.apache.ctakes.typesystem.type.syntax.BaseToken;
import org.apache.ctakes.typesystem.type.syntax.NewlineToken;
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
            DocumentID documentIDAnnotation = new DocumentID(jcas);
            String docID = kafDocument.getPublic().publicId;
            documentIDAnnotation.setDocumentID(docID);
            documentIDAnnotation.addToIndexes();

            documentIDAnnotation.setDocumentID(docID);
			setTokens(kafDocument, jcas);
			logger.debug("CAS object generated");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Cas object could not be generated");
		}
		return jcas;
	}


	private static JCas setTokens(KAFDocument kafDocument, JCas jcas){

	    //WordToken
        //PunctuationToken
        //NumToken
        //ContractionToken
        //SymbolToken
        
        //FractionAnnotation
        //DateAnnotation
        //

        List<WF> wfs = kafDocument.getWFs();
		for (WF wf : wfs) {
			int startOffset = wf.getOffset();
			int endOffset = wf.getOffset() + wf.getLength();
			Token token = new Token(startOffset, endOffset);
			//wf.
            //NewlineToken token2 = new NewlineToken(jcas, startOffset, endOffset);


//			token.setCaps();
//
//			token.setText();

		}

		List<Term> terms = kafDocument.getTerms();
		for(Term term : terms){
			List<WF> termWfs = term.getWFs();
			String pos = term.getPos();
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
