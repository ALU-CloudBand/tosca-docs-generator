package org.tosca.docs;

import org.tosca.docs.generators.html.HtmlGenerator;
import org.tosca.docs.model.ToscaSpec;
import org.tosca.docs.model.impl.ToscaSpecImpl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Generator {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: tosca-docs-generator-jar-with-dependencies.jar /path/to/tosca_spec.json");
            System.exit(1);
        }

        String toscaSpecFilePath = args[0];
        File toscaSpecFile = new File(toscaSpecFilePath);
        ToscaSpec toscaSpec;
        try {
            toscaSpec = ToscaSpecImpl.OBJECT_MAPPER.readValue(toscaSpecFile, ToscaSpecImpl.class);
        } catch (IOException e) {
            throw new IOException("Failed to read JSON file [" + toscaSpecFile + "]", e);
        }

        HtmlGenerator htmlGenerator = new HtmlGenerator(toscaSpec);
        try (OutputStreamWriter writer = new OutputStreamWriter(System.out)) {
            htmlGenerator.write(writer);
            writer.flush();
        }
    }
}
