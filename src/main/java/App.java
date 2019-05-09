import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.FileNotFoundException;


public class App {
    public static void main(String[] args) throws OWLException, FileNotFoundException {
        Factory mFactory = new Factory();

        //Load OWL File
        mFactory.loadOntologyLocal("src/main/resources/JOV2.owl");
//        System.out.println(mFactory.getmOWLOntology());

        //Modification Add un Axiom
        mFactory.addDeclarationAxiom("test");

        //Modificaton Add un Sub Axiom
        mFactory.addSubClassAxiom("test", "subtest");

        mFactory.removeAxiom("test");

//        Save OWL File
        mFactory.saveOntologyLocal("src/main/resources/JOVTEST2.owl");


    }
}
