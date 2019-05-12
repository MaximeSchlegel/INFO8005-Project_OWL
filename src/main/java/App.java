import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.FileNotFoundException;


public class App {
    public static void main(String[] args) throws OWLException, FileNotFoundException {
        Factory mFactory = new Factory();

        //Load OWL File
        mFactory.loadOntologyLocal("src/main/resources/JOV4.owl");

        //Modification Add un Axiom
        mFactory.addDeclarationAxiom("test");

        //Modificaton Add un Sub Axiom
        mFactory.addSubClassAxiom("test", "subtest");

        //Remove Axiom
//        mFactory.removeAxiom("test");

        //Add Property
        mFactory.addProperty("hasTest", "test", "result");

        //Add ObjectValueSome
        mFactory.addComplexExpressionSome("test", "hasTest");

        //Add ObjectValueExactlyCardinality
        mFactory.addComplexExpressionExactCardinality("test", "hasTest", 1);

        //Add Individual
        mFactory.addIndividual("test", "Test1");

        //Launch Reasoner
        mFactory.LaunchReasoner();

        mFactory.inferredSubClass();
        //Save OWL File
        mFactory.saveOntologyLocal("src/main/resources/JOVTEST4.owl");

        mFactory.IterateOverAxioms();


    }
}
