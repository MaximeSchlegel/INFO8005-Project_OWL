import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

public class Factory {

    private OWLManager mOWLManager;
    private OWLOntology mOWLOntology;
    private OWLReasoner mOWLReasoner;
    public void createOntology() throws OWLException {
      OWLOntologyManager man = OWLManager.createOWLOntologyManager();
      OWLOntology o = man.createOntology();
      System.out.println(o);

    }
    public void loadOntologyLocal(String nameOfOntology) throws OWLException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        File file = new File(nameOfOntology);
        this.mOWLOntology = man.loadOntologyFromOntologyDocument(file);
    }
    public void loadOntologyIRI(String IRIOfOntology) throws OWLException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        IRI mIRI = IRI.create(IRIOfOntology);
        this.mOWLOntology = man.loadOntology(mIRI);
//        System.out.println(o);
    }
    public void saveOntologyIRI(String mIRI, String path) throws OWLOntologyCreationException, FileNotFoundException, OWLOntologyStorageException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        File fileout = new File(path);
        IRI ontologyIRI = IRI.create(mIRI);
        this.mOWLOntology = man.loadOntology(ontologyIRI);
        man.saveOntology(this.mOWLOntology, new FunctionalSyntaxDocumentFormat(), new FileOutputStream(fileout));
    }
    public void saveOntologyLocal(String path) throws FileNotFoundException, OWLOntologyStorageException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        File fileout = new File(path);
        man.saveOntology(this.mOWLOntology, new FunctionalSyntaxDocumentFormat(), new FileOutputStream(fileout));
    }
    public void addDeclarationAxiom(String mAxiom){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass axiom = df.getOWLClass(IRI.create("#" + mAxiom));
        OWLDeclarationAxiom da = df.getOWLDeclarationAxiom(axiom);
        mOWLOntology.add(da);
    }
    public void addSubClassAxiom(String mAxiom, String subAxiom){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass axiom = df.getOWLClass(IRI.create("#" + mAxiom));
        OWLClass mSubAxiom = df.getOWLClass(IRI.create("#" + subAxiom));
        OWLSubClassOfAxiom mOWLSubClassOfAxiom = df.getOWLSubClassOfAxiom(mSubAxiom, axiom);
        mOWLOntology.add(mOWLSubClassOfAxiom);
    }
    public void addProperty(String mProperty, String mDomainAxiom, String mRangeAxiom){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass axiomDomain = df.getOWLClass(IRI.create("#" + mDomainAxiom));
        OWLClass axiomRange = df.getOWLClass(IRI.create("#" + mRangeAxiom));
        OWLObjectProperty R = df.getOWLObjectProperty(IRI.create("#"+ mProperty));
        OWLObjectPropertyDomainAxiom domainAxiom = df.getOWLObjectPropertyDomainAxiom(R, axiomDomain);
        OWLObjectPropertyRangeAxiom rangeAxiom = df.getOWLObjectPropertyRangeAxiom(R, axiomRange);
        mOWLOntology.addAxiom(domainAxiom);
        mOWLOntology.addAxiom(rangeAxiom);
    }
    public void addComplexExpressionSome(String mAxiom, String mProperty){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass mOWLClass = df.getOWLClass(IRI.create("#" + mAxiom));
        OWLObjectProperty mOWLObjectProperty = df.getOWLObjectProperty(IRI.create("#" + mProperty));
        OWLClassAxiom ax = df.getOWLSubClassOfAxiom(
                mOWLClass,
                df.getOWLObjectSomeValuesFrom(mOWLObjectProperty, mOWLClass)
        );
        mOWLOntology.addAxiom(ax);
    }
    public void addComplexExpressionExactCardinality(String mAxiom, String mProperty, int cardinality){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass mOWLClass = df.getOWLClass(IRI.create("#" + mAxiom));
        OWLObjectProperty mOWLObjectProperty = df.getOWLObjectProperty(IRI.create("#" + mProperty));
        OWLClassAxiom ax = df.getOWLSubClassOfAxiom(
                mOWLClass,
                df.getOWLObjectExactCardinality(cardinality, mOWLObjectProperty, mOWLClass)
        );
        mOWLOntology.addAxiom(ax);
    }
    public void addIndividual(String mAxiom, String mIndividual){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLNamedIndividual mOWLNamedIndividual = df.getOWLNamedIndividual(IRI.create("#" + mIndividual));
        OWLClass mOWLClass = df.getOWLClass( IRI.create("#" + mAxiom) );
        mOWLOntology.addAxiom(df.getOWLClassAssertionAxiom(mOWLClass, mOWLNamedIndividual));
    }
    public void removeAxiom(String mAxiom){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass axiom = df.getOWLClass(IRI.create("#" + mAxiom));
        Set<OWLAxiom> axiomsToRemove = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : mOWLOntology.getAxioms()) {
            if (ax.getSignature().contains(axiom)) {
                axiomsToRemove.add(ax);
            }
        }
        mOWLOntology.remove(axiomsToRemove);
    }
    public void LaunchReasoner(){
        OWLReasonerFactory mOWLReasonerFactory = new ReasonerFactory();
        this.mOWLReasoner = mOWLReasonerFactory.createReasoner(getmOWLOntology());
        this.mOWLReasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
    }
    public void inferredSubClass(){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        mOWLReasoner.getSubClasses(df.getOWLClass("#subtest"), false).forEach(System.out::println);

    }
    public void IterateOverAxioms() {
        mOWLOntology.logicalAxioms().forEach(System.out::println);
    }
    public OWLManager getmOWLManager() {
        return mOWLManager;
    }

    public OWLOntology getmOWLOntology() {
        return mOWLOntology;
    }


}
