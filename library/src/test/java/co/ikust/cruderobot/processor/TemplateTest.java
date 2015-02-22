package co.ikust.cruderobot.processor;

import org.junit.Test;

import co.ikust.simpletemplates.Template;
import co.ikust.simpletemplates.Templates;

import static co.ikust.simpletemplates.ResourceUtils.readFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by ivan on 29/01/15.
 */
public class TemplateTest {

    @Test
    public void testSimpleReplace() {
        Template testTemplate = Templates.getInstance().read("/co/ikust/simpletemplates/templates/simple.tpl");
        testTemplate.addReplacement("test", "Some replacement text");
        assertThat(testTemplate.toString(), equalTo(readFile("/co/ikust/simpletemplates/templates/simple_result.txt")));
    }

    @Test
    public void testMultipleTagInstances() {
        Template testTemplate = Templates.getInstance().read("/co/ikust/simpletemplates/templates/two_equal_tags.tpl");
        testTemplate.addReplacement("test", "Some replacement text");
        assertThat(testTemplate.toString(), equalTo(readFile("/co/ikust/simpletemplates/templates/two_equal_tags_result.txt")));
    }

    @Test
    public void testMultipleTags() {
        Template testTemplate = Templates.getInstance().read("/co/ikust/simpletemplates/templates/two_equal_tags.tpl");
        testTemplate.addReplacement("test", "Some replacement text");
        testTemplate.addReplacement("second", "Second replacement text");
        assertThat(testTemplate.toString(), equalTo(readFile("/co/ikust/simpletemplates/templates/two_equal_tags_result.txt")));
    }

    @Test
    public void testNonExistingTag() {
        Template testTemplate = Templates.getInstance().read("/co/ikust/simpletemplates/templates/two_equal_tags.tpl");
        boolean result = testTemplate.addReplacement("nonExistingTag", "Some replacement text");
        assertThat(testTemplate.toString(), equalTo(readFile("/co/ikust/simpletemplates/templates/two_equal_tags.tpl")));
        assertThat(result, equalTo(false));
    }

    @Test
    public void testIndentation() {
        Template testTemplate = Templates.getInstance().read("/co/ikust/simpletemplates/templates/indentation.tpl");
        testTemplate.addReplacement("test", "Some replacement text. ");
        testTemplate.addReplacementLine("test", "Second replacement line");
        testTemplate.addReplacement("test", "Third replacemenet line");
        testTemplate.addReplacementLine("second", "Second replacement text");
        testTemplate.addReplacement("second", "Another replacement line");
        assertThat(testTemplate.toString(), equalTo(readFile("/co/ikust/simpletemplates/templates/indentation_result.txt")));
    }
}
