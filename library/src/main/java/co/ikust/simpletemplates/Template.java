package co.ikust.simpletemplates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ivan on 26/01/15.
 */
public class Template {

    public static final String TOKEN_FORMAT = "${%s}";

    private static final String INDENTATION_CHECK_PATTERN = "[^\\n\\r]*\\$\\{%s\\}";

    protected String content;

    public static String getToken(String name) {
        return String.format(TOKEN_FORMAT, name);
    }

    /**
     * Maps the replacement values to the template tokens.
     */
    protected HashMap<String, ArrayList<Replacement>> replacements = new HashMap<>();

    Template(String content) {
        this.content = content;
    }

    private int getTokenIndentation(String token) {
        Pattern pattern = Pattern.compile(String.format(INDENTATION_CHECK_PATTERN, token));
        Matcher matcher = pattern.matcher(content);

        if(matcher.find()) {
            return matcher.group(0).length() - getToken(token).length();
        } else {
            return -1;
        }
    }

    private void appendSpaces(StringBuilder builder, int amount) {
        for(int i = 0; i < amount; i++) {
            builder.append(" ");
        }
    }

    private boolean addReplacement(String tokenName, String replacement, boolean newLine) {
        if(!content.contains(getToken(tokenName))) {
            return false;
        }

        if(replacements.get(tokenName) == null) {
            replacements.put(tokenName, new ArrayList<Replacement>());
        }

        replacements.get(tokenName).add(new Replacement(replacement, newLine));

        return true;
    }

    private boolean addReplacement(String tokenName, Template replacement, boolean newLine) {
        String lines[] = replacement.toString().split("\\r?\\n");

        boolean replacementAdded = false;

        for(int i = 0; i < lines.length; i++) {
            replacementAdded = addReplacement(tokenName, lines[i], (i < lines.length - 1) || newLine);
        }

        return replacementAdded;
    }

    public boolean addReplacement(String tokenName, String replacement) {
        return addReplacement(tokenName, replacement, false);
    }

    public boolean addReplacement(String tokenName, Template replacement) {
        return addReplacement(tokenName, replacement, false);
    }

    public boolean addReplacementLine(String tokenName, String replacement) {
        return addReplacement(tokenName, replacement, true);
    }

    public boolean addReplacementLine(String tokenName, Template replacement) {
        return addReplacement(tokenName, replacement, true);
    }

    @Override
    public String toString() {
        String result = content;
        int indentation;

        for(String key : replacements.keySet()) {
            indentation = getTokenIndentation(key);
            StringBuilder builder = new StringBuilder();
            boolean indent = false;

            for(Replacement replacement : replacements.get(key)) {
                if(indent) {
                    appendSpaces(builder, indentation);
                }

                builder.append(replacement.value);
                indent = replacement.newLine;

                if(replacement.newLine) {
                    builder.append("\n");
                }
            }

            result = result.replace(getToken(key), builder.toString());
        }

        return result;
    }


}
