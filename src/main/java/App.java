import org.semanticweb.owlapi.model.OWLException;



public class App {
    public static void main(String[] args) throws OWLException {
        Factory mFactory = new Factory();
        mFactory.loadOntology("./JOV2.owl");

    }
}
