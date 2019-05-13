import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.FileNotFoundException;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws OWLException, IOException {
        Factory mFactory = new Factory();

        /*
         * this is example to illustrate the use of owl api
         */
        ////Create OWL File
//        mFactory.createOntology();
        ////Add un Axiom
//        mFactory.addDeclarationAxiom("Person");
//        mFactory.addDeclarationAxiom("University");

        ////Add un Sub Axiom
//        mFactory.addSubClassAxiom("Person", "Woman");
//        mFactory.addSubClassAxiom("Person", "Man");

        ////Remove Axiom
//        mFactory.removeAxiom("Person");

        ////Add Property
//        mFactory.addProperty("isEnrolledIn", "Person", "University");

        ////Add ObjectValueSome
//        mFactory.addComplexExpressionSome("isEnrolledIn", "Person", "University");

        ////Add ObjectValueExactlyCardinality
//        mFactory.addComplexExpressionExactCardinality("isEnrolledIn", "Person","University", 1);

        ////Add Individual
//        mFactory.addIndividual("Man", "Maxime");
//        mFactory.addIndividual("Man", "Fabien");
//        mFactory.addIndividual("Man", "Mourad");
//        mFactory.addIndividual("Woman", "Marine");
//        mFactory.addIndividual("University", "Uliege");
        ////Add IndividualObjectProperty
//        mFactory.addIndividualObjectProperty("Maxime", "isEnrolledIn", "Uliege");

        ////Launch Reasoner
//        mFactory.LaunchReasoner();

        ////Save OWL File
//        mFactory.saveOntologyLocal("src/main/resources/JOVTEST4.owl");
        ////Show all Axioms
//        mFactory.IterateOverAxioms();

        ////Execute some DL Query
        ////Test DL Query : Person and isEnrolledIn University
//        mFactory.InitialiseQuery();
        /*
         * fin example
         */



        //Load OWL File
        mFactory.loadOntologyLocal("src/main/resources/JOV5.owl");

        mFactory.addDeclarationAxiom("World_Record");

        mFactory.addSubClassAxiom("Record", "World_Record");

        mFactory.addProperty("WorldRecordParticipant", "Participant", "World_Record", true);

        mFactory.addComplexExpressionSome("WorldRecordParticipant", "Participant", "World_Record");

        mFactory.addIndividual("Participant", "Usain_Bolt_example");

        mFactory.addIndividual("World_Record", "Athletics_200M");

        mFactory.addIndividualObjectProperty("Usain_Bolt_example", "WorldRecordParticipant", "Athletics_200M");

        mFactory.LaunchReasoner();

        mFactory.saveOntologyLocal("src/main/resources/JOVTEST5.owl");

        mFactory.InitialiseQuery();

        //DL Query => "World_Record and WorldRecordParticipant value Usain_Bolt_example"
    }
}
