import jdk.nashorn.internal.objects.annotations.Function;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLClassLiteralCollector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

public class Factory {

    private OWLManager mOWLManager;
    private OWLOntology mOWLOntology;
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
        OWLClass axiom = df.getOWLClass("#" + mAxiom);
        OWLDeclarationAxiom da = df.getOWLDeclarationAxiom(axiom);
        mOWLOntology.add(da);
    }
    public void addSubClassAxiom(String mAxiom, String subAxiom){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass axiom = df.getOWLClass("#" + mAxiom);
        OWLClass mSubAxiom = df.getOWLClass("#" + subAxiom);
        OWLSubClassOfAxiom mOWLSubClassOfAxiom = df.getOWLSubClassOfAxiom(mSubAxiom, axiom);
        mOWLOntology.add(mOWLSubClassOfAxiom);
    }

    public void removeAxiom(String mAxiom){
        OWLDataFactory df = mOWLOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass axiom = df.getOWLClass("#" + mAxiom);
        Set<OWLAxiom> axiomsToRemove = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : mOWLOntology.getAxioms()) {
            if (ax.getSignature().contains(axiom)) {
                axiomsToRemove.add(ax);
            }
        }
        mOWLOntology.remove(axiomsToRemove);
    }
    public OWLManager getmOWLManager() {
        return mOWLManager;
    }

    public OWLOntology getmOWLOntology() {
        return mOWLOntology;
    }
}
