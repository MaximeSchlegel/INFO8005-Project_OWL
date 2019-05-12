import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Factory {

    private OWLManager mOWLManager;
    private OWLOntology mOWLOntology;
    private OWLReasoner mOWLReasoner;
    public void createOntology() throws OWLException {
      OWLOntologyManager man = OWLManager.createOWLOntologyManager();
      this.mOWLOntology = man.createOntology();
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
    public void addComplexExpressionSome(String mProperty, String mDomain, String mRange){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass mOWLClassDomain = df.getOWLClass(IRI.create("#" + mDomain));
        OWLClass mOWLClassRange = df.getOWLClass(IRI.create("#" + mRange));
        OWLObjectProperty mOWLObjectProperty = df.getOWLObjectProperty(IRI.create("#" + mProperty));
        OWLClassAxiom ax = df.getOWLSubClassOfAxiom(
                mOWLClassDomain,
                df.getOWLObjectSomeValuesFrom(mOWLObjectProperty, mOWLClassRange)
        );
        mOWLOntology.addAxiom(ax);
    }
    public void addComplexExpressionExactCardinality(String mProperty, String mDomain, String mRange, int cardinality){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass mOWLClassDomain = df.getOWLClass(IRI.create("#" + mDomain));
        OWLClass mOWLClassRange = df.getOWLClass(IRI.create("#" + mRange));
        OWLObjectProperty mOWLObjectProperty = df.getOWLObjectProperty(IRI.create("#" + mProperty));
        OWLClassAxiom ax = df.getOWLSubClassOfAxiom(
                mOWLClassDomain,
                df.getOWLObjectExactCardinality(cardinality, mOWLObjectProperty, mOWLClassRange)
        );
        mOWLOntology.addAxiom(ax);
    }
    public void addIndividual(String mAxiom, String mIndividual){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLNamedIndividual mOWLNamedIndividual = df.getOWLNamedIndividual(IRI.create("#" + mIndividual));
        OWLClass mOWLClass = df.getOWLClass( IRI.create("#" + mAxiom) );
        mOWLOntology.addAxiom(df.getOWLClassAssertionAxiom(mOWLClass, mOWLNamedIndividual));
    }
    public void addIndividualObjectProperty(String mIndividual, String mProperty, String mIndividualRange){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLNamedIndividual mOWLNamedIndividual = df.getOWLNamedIndividual(IRI.create("#" + mIndividual));
        OWLNamedIndividual mOWLNamedIndividualRange = df.getOWLNamedIndividual(IRI.create("#" + mIndividualRange));
        OWLObjectProperty mOWLObjectProperty = df.getOWLObjectProperty(IRI.create("#" + mProperty));
        df.getOWLObjectHasValue(mOWLObjectProperty, mOWLNamedIndividual);
        mOWLOntology.addAxiom(
                df.getOWLObjectPropertyAssertionAxiom(mOWLObjectProperty, mOWLNamedIndividual, mOWLNamedIndividualRange)
        );

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

    public void InitialiseQuery() throws IOException {
        OWLReasoner reasoner = new Reasoner.ReasonerFactory().createReasoner(mOWLOntology);
        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
        // Create the DLQueryPrinter helper class. This will manage the
        // parsing of input and printing of results
        DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(reasoner,
                shortFormProvider), shortFormProvider);
        // Enter the query loop. A user is expected to enter class
        // expression on the command line.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        while (true) {
            System.out
                    .println("Type a class expression in Manchester Syntax and press Enter (or press x to exit):");
            String classExpression = br.readLine();
            // Check for exit condition
            if (classExpression == null || classExpression.equalsIgnoreCase("x")) {
                break;
            }
            dlQueryPrinter.askQuery(classExpression.trim());
            System.out.println();
        }
    }

}


class DLQueryEngine {
    private final OWLReasoner reasoner;
    private final DLQueryParser parser;

    public DLQueryEngine(OWLReasoner reasoner, ShortFormProvider shortFormProvider) {
        this.reasoner = reasoner;
        parser = new DLQueryParser(reasoner.getRootOntology(), shortFormProvider);
    }

    public Set<OWLClass> getSuperClasses(String classExpressionString, boolean direct) {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        NodeSet<OWLClass> superClasses = reasoner
                .getSuperClasses(classExpression, direct);
        return superClasses.getFlattened();
    }

    public Set<OWLClass> getEquivalentClasses(String classExpressionString) {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        Node<OWLClass> equivalentClasses = reasoner.getEquivalentClasses(classExpression);
        Set<OWLClass> result = null;
        if (classExpression.isAnonymous()) {
            result = equivalentClasses.getEntities();
        } else {
            result = equivalentClasses.getEntitiesMinus(classExpression.asOWLClass());
        }
        return result;
    }

    public Set<OWLClass> getSubClasses(String classExpressionString, boolean direct) {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        NodeSet<OWLClass> subClasses = reasoner.getSubClasses(classExpression, direct);
        return subClasses.getFlattened();
    }

    public Set<OWLNamedIndividual> getInstances(String classExpressionString,
                                                boolean direct) {
        if (classExpressionString.trim().length() == 0) {
            return Collections.emptySet();
        }
        OWLClassExpression classExpression = parser
                .parseClassExpression(classExpressionString);
        NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(classExpression,
                direct);
        return individuals.getFlattened();
    }
}

class DLQueryParser {
    private final OWLOntology rootOntology;
    private final BidirectionalShortFormProvider bidiShortFormProvider;

    public DLQueryParser(OWLOntology rootOntology, ShortFormProvider shortFormProvider) {
        this.rootOntology = rootOntology;
        OWLOntologyManager manager = rootOntology.getOWLOntologyManager();
        Set<OWLOntology> importsClosure = rootOntology.getImportsClosure();
        // Create a bidirectional short form provider to do the actual mapping.
        // It will generate names using the input
        // short form provider.
        bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(manager,
                importsClosure, shortFormProvider);
    }

    public OWLClassExpression parseClassExpression(String classExpressionString) {
        OWLDataFactory dataFactory = rootOntology.getOWLOntologyManager()
                .getOWLDataFactory();
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                dataFactory, classExpressionString);
        parser.setDefaultOntology(rootOntology);
        OWLEntityChecker entityChecker = new ShortFormEntityChecker(bidiShortFormProvider);
        parser.setOWLEntityChecker(entityChecker);
        return parser.parseClassExpression();
    }
}

class DLQueryPrinter {
    private final DLQueryEngine dlQueryEngine;
    private final ShortFormProvider shortFormProvider;

    public DLQueryPrinter(DLQueryEngine engine, ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
        dlQueryEngine = engine;
    }

    public void askQuery(String classExpression) {
        if (classExpression.length() == 0) {
            System.out.println("No class expression specified");
        } else {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("\nQUERY:   ").append(classExpression).append("\n\n");
                Set<OWLClass> superClasses = dlQueryEngine.getSuperClasses(
                        classExpression, false);
                printEntities("SuperClasses", superClasses, sb);
                Set<OWLClass> equivalentClasses = dlQueryEngine
                        .getEquivalentClasses(classExpression);
                printEntities("EquivalentClasses", equivalentClasses, sb);
                Set<OWLClass> subClasses = dlQueryEngine.getSubClasses(classExpression,
                        true);
                printEntities("SubClasses", subClasses, sb);
                Set<OWLNamedIndividual> individuals = dlQueryEngine.getInstances(
                        classExpression, true);
                printEntities("Instances", individuals, sb);
                System.out.println(sb.toString());
            } catch (ParserException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printEntities(String name, Set<? extends OWLEntity> entities,
                               StringBuilder sb) {
        sb.append(name);
        int length = 50 - name.length();
        for (int i = 0; i < length; i++) {
            sb.append(".");
        }
        sb.append("\n\n");
        if (!entities.isEmpty()) {
            for (OWLEntity entity : entities) {
                sb.append("\t").append(shortFormProvider.getShortForm(entity))
                        .append("\n");
            }
        } else {
            sb.append("\t[NONE]\n");
        }
        sb.append("\n");
    }
}


