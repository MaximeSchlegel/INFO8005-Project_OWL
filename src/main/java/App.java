import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.FileNotFoundException;


public class App {
    public static void main(String[] args) throws OWLException, FileNotFoundException {
        Factory mFactory = new Factory();

        //Load OWL File
        mFactory.loadOntologyLocal("src/main/resources/JOV2.owl");

        //Modification Add un Axiom
        mFactory.addDeclarationAxiom("test");

        //Modificaton Add un Sub Axiom
        mFactory.addSubClassAxiom("test", "subtest");

        //Remove Axiom
        mFactory.removeAxiom("test");

        //Launch Reasoner
        mFactory.LaunchReasoner();

        mFactory.inferredSubClass();
        //Save OWL File
        mFactory.saveOntologyLocal("src/main/resources/JOVTEST2.owl");


    }
}
