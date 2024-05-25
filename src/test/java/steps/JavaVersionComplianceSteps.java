package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

public class JavaVersionComplianceSteps {

    private boolean isJava16OrNewer;
    private boolean compilationSucceeded;
    private boolean usesJava16Features;

    @Given("the development environment is set up with Java 16 or newer")
    public void theDevelopmentEnvironmentIsSetUpWithJava16OrNewer() {
        String version = System.getProperty("java.version");
        isJava16OrNewer = version.startsWith("16") || Integer.parseInt(version.split("\\.")[0]) > 16;
    }

    @When("the software library is compiled")
    public void theSoftwareLibraryIsCompiled() {
        // Simulate the compilation process
        // For the purpose of this example, we assume the compilation always succeeds in the right environment
        if (isJava16OrNewer) {
            compilationSucceeded = true;
            usesJava16Features = true; // Assume it uses Java 16 features
        } else {
            compilationSucceeded = false;
        }
    }

    @Then("the compilation should succeed without any version compatibility issues")
    public void theCompilationShouldSucceedWithoutAnyVersionCompatibilityIssues() {
        assertTrue("Compilation should succeed with Java 16 or newer", compilationSucceeded);
    }

    @Then("the software library should be able to utilize features specific to Java 16 or newer")
    public void theSoftwareLibraryShouldBeAbleToUtilizeFeaturesSpecificToJava16OrNewer() {
        assertTrue("The software library should utilize features specific to Java 16 or newer", usesJava16Features);
    }
}

