import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "summary", "html:build/target/cucumber-report.html"},
    snippets = CucumberOptions.SnippetType.UNDERSCORE,
    features = {"src/test/resources"}
)
public class CucumberConfig {
  // This is needed to run the features via gradle/junit
}
