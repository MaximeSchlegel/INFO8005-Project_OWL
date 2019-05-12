import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.FileNotFoundException;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws OWLException, IOException {
        Factory mFactory = new Factory();

        //Create OWL File
        mFactory.createOntology();

        //Load OWL File
//        mFactory.loadOntologyLocal("src/main/resources/JOV4.owl");

        //Modification Add un Axiom
        mFactory.addDeclarationAxiom("Person");
        mFactory.addDeclarationAxiom("University");

        //Modificaton Add un Sub Axiom
        mFactory.addSubClassAxiom("Person", "Woman");
        mFactory.addSubClassAxiom("Person", "Man");

        //Remove Axiom
//        mFactory.removeAxiom("test");

        //Add Property
        mFactory.addProperty("isEnrolledIn", "Person", "University");

        //Add ObjectValueSome
        mFactory.addComplexExpressionSome("isEnrolledIn", "Person", "University");

        //Add ObjectValueExactlyCardinality
        mFactory.addComplexExpressionExactCardinality("isEnrolledIn", "Person","University", 1);

        //Add Individual
        mFactory.addIndividual("Man", "Maxime");
        mFactory.addIndividual("Man", "Fabien");
        mFactory.addIndividual("Man", "Mourad");
        mFactory.addIndividual("Woman", "Marine");

        mFactory.addIndividual("University", "Uliege");

        mFactory.addIndividualObjectProperty("Maxime", "isEnrolledIn", "Uliege");

        //Launch Reasoner
        mFactory.LaunchReasoner();

//        mFactory.inferredSubClass();

        //Save OWL File
        mFactory.saveOntologyLocal("src/main/resources/JOVTEST4.owl");
        //Show all Axioms
        mFactory.IterateOverAxioms();

        //Execute some DL Query
        //TODO Test DL Query : Person and isEnrolledIn University
        mFactory.InitialiseQuery();


    }
}
