import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.*;

public class Factory {
    public void createOntology() throws OWLException {

        OWLOntologyManager om = OWLManager.createOWLOntologyManager();
        IRI pressinnov_iri = IRI.create("https://contentside.com/misc/exemple.owl");
        OWLOntology pressInnovOntology = om.createOntology(pressinnov_iri);
    }

    public void loadOntology(String nameOfOntology) throws OWLException {

        OWLOntologyManager om = OWLManager.createOWLOntologyManager();
        OWLOntologyDocumentSource source = new StreamDocumentSource(getClass().
                getResourceAsStream(nameOfOntology));
        om.loadOntologyFromOntologyDocument(source);
        OWLOntology pressInnovOntology = om.createOntology();
    }

    public void addClass() throws OWLException {

        OWLOntologyManager om = OWLManager.createOWLOntologyManager();
        IRI pressinnov_iri = IRI.create("https://contentside.com/misc/personne.owl");
        OWLOntology pressinnovOntology = om.createOntology(pressinnov_iri);
        OWLDataFactory factory = om.getOWLDataFactory();

        OWLClass Sportif = factory.getOWLClass(IRI.create(pressinnov_iri + "#Sportif"));
        OWLClass Footballeur = factory.getOWLClass(IRI.create(pressinnov_iri  + "#Footballeur"));

        OWLAxiom axiom = factory.getOWLSubClassOfAxiom(Footballeur, Sportif);

        AddAxiom addAxiom = new AddAxiom(pressinnovOntology, axiom);
        om.applyChange(addAxiom);

    }

}
