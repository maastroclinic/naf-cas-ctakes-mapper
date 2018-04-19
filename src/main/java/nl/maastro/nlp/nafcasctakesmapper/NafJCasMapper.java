package nl.maastro.nlp.nafcasctakesmapper;


import ixa.kaflib.KAFDocument;
import ixa.kaflib.Term;
import ixa.kaflib.WF;
import org.apache.ctakes.typesystem.type.structured.DocumentID;
import org.apache.ctakes.typesystem.type.syntax.*;
import org.apache.ctakes.typesystem.type.textspan.Segment;
import org.apache.ctakes.typesystem.type.textspan.Sentence;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static nl.maastro.nlp.nafcasctakesmapper.PosTag.POS;
import static nl.maastro.nlp.nafcasctakesmapper.PosTag.valueOf;

/**
 *
 */
public class NafJCasMapper {

    final static Logger logger = LoggerFactory.getLogger(NafJCasMapper.class);

    public static JCas getJCas(KAFDocument kafDocument){

        JCas jCas = null;
        try {
            jCas = JCasFactory.createJCas();
            jCas.setDocumentLanguage(kafDocument.getLang());

            String text = kafDocument.getRawText();
            jCas.setDocumentText(text);
            DocumentID documentIDAnnotation = new DocumentID(jCas);
            String docID = kafDocument.getPublic().publicId;
            documentIDAnnotation.setDocumentID(docID);
            documentIDAnnotation.addToIndexes();

            Segment s = new Segment(jCas);
            s.setBegin(0);
            s.setEnd(text.length());
            s.setId("DEFAULT");
            s.addToIndexes();

            documentIDAnnotation.setDocumentID(docID);

            setSentenceTextSpan(kafDocument, jCas);
            setTokens(kafDocument, jCas);
            logger.debug("CAS object generated");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Cas object could not be generated");
        }
        return jCas;
    }


    /**
     * 	<textspan:Sentence xmi:id="19" sofa="6" begin="0" end="60" sentenceNumber="0"/>
     *
     * @param jCas
     * @return
     */
    private static JCas setSentenceTextSpan(KAFDocument kafDocument, JCas jCas){
        kafDocument.getSentences().forEach(sentence -> setSentence(jCas, sentence));
        return jCas;
    }

    private static void setSentence(JCas jCas, List<WF> sentence){
        if(sentence.size()>0){
            WF beginWf = sentence.get(0);
            WF endWf = sentence.get(sentence.size()-1);
            int begin = beginWf.getOffset();
            int end = endWf.getOffset() + endWf.getLength();

            Sentence jCasSentence = new Sentence(jCas, begin, end);
            jCasSentence.setSentenceNumber(beginWf.getSent()-1);
            jCas.addFsToIndexes(jCasSentence);
        }
    }


    /**
     * <syntax:NewlineToken xmi:id="301" sofa="6" begin="60" end="61" tokenNumber="9"/>
     * newline tokens are not present in NAF (uses paragraphs instead). To keep the information new tokens could be added (which will create a mess).
     * <syntax:WordToken xmi:id="509" sofa="6" begin="0" end="8" tokenNumber="0" partOfSpeech="JJ" capitalization="1" numPosition="0"/>
     * <syntax:PunctuationToken xmi:id="557" sofa="6" begin="29" end="30" tokenNumber="4" partOfSpeech="HYPH"/>
     * <syntax:NumToken xmi:id="625" sofa="6" begin="71" end="73" tokenNumber="12" partOfSpeech="CD" numType="1"/>
     * <syntax:SymbolToken xmi:id="783" sofa="6" begin="153" end="154" tokenNumber="28" partOfSpeech="$"/>
     * <syntax:ContractionToken xmi:id="1797" sofa="6" begin="653" end="655" tokenNumber="118" partOfSpeech="POS"/>
     * @param kafDocument
     * @param jcas
     * @return
     */
    private static JCas setTokens(KAFDocument kafDocument, JCas jCas){


        List<Term> terms = kafDocument.getTerms();

        for (Term term : terms) {

            PosTag pos = PosTag.O;
            try {
                pos = valueOf(term.getMorphofeat());
            }
            catch(IllegalArgumentException e){
                logger.warn("Pos not recognized=" + term.getMorphofeat(), e);
            }


            List<WF> wfs = term.getWFs();
            for (WF wf : wfs) {

                int startOffset = wf.getOffset();
                int endOffset = wf.getOffset() + wf.getLength();

                switch (pos) {
                    case CC:
                        SymbolToken symbolToken = new SymbolToken(jCas, startOffset, endOffset);
                        symbolToken.addToIndexes();
                        break;
                    case CD:
                        NumToken numToken = new NumToken(jCas, startOffset, endOffset);
                        numToken.addToIndexes();
                        break;
                    case POS:
                        ContractionToken contractionToken = new ContractionToken(jCas, startOffset, endOffset);
                        contractionToken.addToIndexes();
                        break;
                    case O:
                        PunctuationToken punctuationToken = new PunctuationToken(jCas, startOffset, endOffset);
                        punctuationToken.addToIndexes();
                        break;
                    default:
                        WordToken wordToken = new WordToken(jCas, startOffset, endOffset);
                        wordToken.addToIndexes();
                        break;
                }


                //NewlineToken newLineToken = new NewlineToken(jCas, startOffset, endOffset);
            }
        }

        return jCas;
    }



    /**
     * <syntax:NP xmi:id="3581" sofa="6" begin="0" end="16" chunkType="NP"/>
     * <syntax:VP xmi:id="3586" sofa="6" begin="17" end="22" chunkType="VP"/>
     * <syntax:PP xmi:id="3601" sofa="6" begin="58" end="60" chunkType="PP"/>
     * <syntax:O xmi:id="3631" sofa="6" begin="107" end="108" chunkType="O"/>
     * <syntax:ADVP xmi:id="3776" sofa="6" begin="413" end="420" chunkType="ADVP"/>
     * <syntax:ADJP xmi:id="3821" sofa="6" begin="523" end="532" chunkType="ADJP"/>
     * <syntax:SBAR xmi:id="3956" sofa="6" begin="830" end="832" chunkType="SBAR"/>
     * @param kafDocument
     * @param jCas
     */
    private static JCas setChunkType(KAFDocument kafDocument, JCas jCas){



        return jCas;
    }




}
