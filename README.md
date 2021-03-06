# NAF-CAS-cTakes-Mapper

## Use NLP modules serialized to KAF/NAF format for cTakes (UIMA)


### Motivation
This repository is initially created to integrate spaCy with cTakes for its UMLS dictionary lookup.
Where cTakes is English only, SpaCy contains trained models (non medical) for several languages.

cTakes is build on UIMA which has its serialization formats (e.g. CAS XMI).
NAF (KAFDocument) is chosen over the UIMA CAS XMI format for its native Python and Java implementations.
Mapping between NAF and CAS is implemented in Java using cTakes UIMA's namespace.
 
### Implemented features
Mapped Modules (partly done):
 - [x] Text
 - [x] Token
 - [x] POS
 - [ ] Chunk (todo) 
 
### Usage
See test classes

### References

- [Apache cTakes](http://ctakes.apache.org/)
- [Apache UIMA](https://uima.apache.org/)
- [UMLS](https://www.nlm.nih.gov/research/umls/)
- [SpaCy](https://spacy.io/)
- NAF [GitHub](https://github.com/newsreader/NAF) / 
[PDF](http://www.newsreader-project.eu/files/2013/01/techreport.pdf) specs & [Java](https://github.com/ixa-ehu/kaflib) / [Python](https://github.com/cltl/KafNafParserPy) implementations