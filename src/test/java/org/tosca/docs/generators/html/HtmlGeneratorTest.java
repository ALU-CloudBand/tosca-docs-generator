package org.tosca.docs.generators.html;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.tosca.docs.model.*;
import org.tosca.docs.model.impl.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

public class HtmlGeneratorTest {

    /**
     * Sends the generated HTML to an external HTML validator tool and verifies there are no errors or warnings
     *
     * @throws IOException if exception occurred reading the expected HTML file or sending the HTTP request to the validator
     */
    @Test
    public void validateHtml() throws IOException {
        String expectedHtml;
        try (InputStream expectedInputStream = HtmlGeneratorTest.class.getResourceAsStream("/expected_tosca_spec.html")) {
            expectedHtml = IOUtils.toString(expectedInputStream, Charset.defaultCharset());
        }

        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addTextBody("content", expectedHtml)
                .build();

        Content content = Request.Post("https://validator.w3.org/nu/")
                .body(entity)
                .execute()
                .returnContent();

        String responseBody = content.asString();
        if (!responseBody.contains("The document validates according to the specified schema(s).")) {
            String reportFile = "/tmp/html_validator_report.html";
            writeStringToFile(responseBody, reportFile);
            Assert.fail("HTML validation failed - report at " + reportFile);
        }
    }

    @Test
    public void testToscaSpec() throws IOException {

        ToscaSpec spec = new ToscaSpecImpl();
        spec.setNodeTypes(ImmutableSet.of(
                createCustomVnf(),
                createToscaNodesRoot(),
                createToscaNodesNfvVnf(),
                createEmptyNodeType()
        ));

        compareToscaSpecWithExpected(spec, "/expected_tosca_spec.html");
    }

    /**
     * Tests custom style and locale.
     * @throws IOException In case exception comparing the actual and expected
     */
    @Test
    public void testCustomization() throws IOException {
        ToscaSpec spec = new ToscaSpecImpl();
        spec.setNodeTypes(ImmutableSet.of(
                createToscaNodesRoot()
        ));

        Locale origDefaultLocale = Locale.getDefault();
        try {
            System.setProperty(HtmlGenerator.STYLE_SYSTEM_PROPERTY, "/test.css");
            Locale.setDefault(new Locale("es"));

            compareToscaSpecWithExpected(spec, "/expected_customized_tosca_spec.html");
        } finally {
            System.clearProperty(HtmlGenerator.STYLE_SYSTEM_PROPERTY);
            Locale.setDefault(origDefaultLocale);
        }
    }

    private void compareToscaSpecWithExpected(ToscaSpec spec, String expectedFilePath) throws IOException {
        spec = transform(spec, ToscaSpec.class);

        StringWriter writer = new StringWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator(spec);
        htmlGenerator.write(writer);

        String actualHtml = writer.toString();
        writeStringToFile(actualHtml, "/tmp/generated.html");

        try (InputStream expectedStream = HtmlGeneratorTest.class.getResourceAsStream(expectedFilePath)) {
            Assert.assertEquals(IOUtils.toString(expectedStream, Charset.defaultCharset()), actualHtml);
        }
    }

    private void writeStringToFile(String content, String targetFileFullPath) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(targetFileFullPath);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            byte[] bytes = content.getBytes();
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }

    private NodeType createEmptyNodeType() {
        return new NodeTypeImpl()
                .setTypeUri("test.nodes.Empty")
                .setDerivedFrom("tosca.nodes.Root");
    }

    private NodeType createToscaNodesRoot() {
        return new NodeTypeImpl()
                .setTypeUri("tosca.nodes.Root")
                .setDescription("The TOSCA Root Node Type is the default type that all other TOSCA base Node Types derive from.  This allows for all TOSCA nodes to have a consistent set of features for modeling and management (e.g., consistent definitions for requirements, capabilities and lifecycle interfaces).")
                .setAttributes(ImmutableList.of(
                        new AttributeImpl()
                                .setName("tosca_id")
                                .setRequired(true)
                                .setType(Type.STRING.toString())
                                .setDescription("A unique identifier of the realized instance of a Node Template that derives from any TOSCA normative type."),

                        new AttributeImpl()
                                .setName("tosca_name")
                                .setRequired(true)
                                .setType(Type.STRING.toString())
                                .setDescription("This attribute reflects the name of the Node Template as defined in the TOSCA service template.  This name is not unique to the realized instance model of corresponding deployed application as each template in the model can result in one or more instances (e.g., scaled) when orchestrated to a provider environment.")
                        ,

                        new AttributeImpl()
                                .setName("state")
                                .setRequired(true)
                                .setType(Type.STRING.toString())
                                .setDefaultValue("initial")
                                .setDescription("This attribute reflects the name of the Node Template as defined in the TOSCA service template.  This name is not unique to the realized instance model of corresponding deployed application as each template in the model can result in one or more instances (e.g., scaled) when orchestrated to a provider environment.")
                        )
                );
    }

    private NodeType createToscaNodesNfvVnf() {

        return new NodeTypeImpl()
                .setTypeUri("tosca.nodes.nfv.VNF")
                .setDescription("The NFV VNF Node Type represents a Virtual Network Function as defined by [ETSI GS NFV-MAN 001 v1.1.1].  It is the default type that all other VNF Node Types derive from.  This allows for all VNF nodes to have a consistent set of features for modeling and management (e.g., consistent definitions for requirements, capabilities and lifecycle interfaces).")
                .setDerivedFrom("tosca.nodes.Root")
                .setProperties(ImmutableList.<Property>of(
                        new PropertyImpl()
                                .setName("vendor")
                                .setRequired(true)
                                .setType(Type.STRING.toString())
                                .setStatus(Status.SUPPORTED.toString())
                                .setDescription("VNF vendor")
                                .setConstraints(ImmutableList.<Constraint>of(
                                        new ConstraintImpl(Constraint.Operator.VALID_VALUES.toString(), Arrays.asList("Nokia", "Other")),
                                        new ConstraintImpl(Constraint.Operator.MAX_LENGTH.toString(), 5)
                                ))
                        ,
                        new PropertyImpl()
                                .setName("version")
                                .setRequired(false)
                                .setDefaultValue(1)
                                .setType(Type.INTEGER.toString())
                                .setStatus(Status.DEPRECATED.toString())
                                .setConstraints(ImmutableList.<Constraint>of(
                                        new ConstraintImpl(Constraint.Operator.PATTERN.toString(), "\\d\\.\\d")
                                ))
                ))
                .setAttributes(ImmutableList.of(
                        new AttributeImpl()
                                .setName("vnfr_id")
                                .setRequired(true)
                                .setType(Type.STRING.toString())
                                .setDescription("A unique identifier of the realized instance of a Node Template that derives from any TOSCA normative type.")
                                .setExtensions(ImmutableMap.<String, Object>of("Extension1", "Value1")),

                        new AttributeImpl()
                                .setName("outputs")
                                .setRequired(false)
                                .setType("json")
                        ,

                        new AttributeImpl()
                                .setName("placement")
                                .setRequired(true)
                                .setType("json")
                        )
                )
                .setCapabilities(ImmutableList.<Capability>of(
                        new CapabilityImpl("feature", "tosca.capabilities.Feature")
                ));
    }

    private NodeType createCustomVnf() {
        return new NodeTypeImpl()
                .setTypeUri("custom.nodes.VNF")
                .setShorthandName("CustomVnf")
                .setTypeQualifiedName("TypeQualifiedName")
                .setDerivedFrom("tosca.nodes.nfv.VNF")
                .setProperties(ImmutableList.<Property>of(
                        new PropertyImpl()
                                .setName("custom_prop")
                                .setType(Type.STRING.toString())
                ))
                .setCapabilities(ImmutableList.<Capability>of(
                        new CapabilityImpl("dependable", "tosca.capabilities.Dependable")
                ));
    }

    private <T> T transform(Object modelEntity, Class<T> aClass) throws IOException {
        StringWriter stringWriter = new StringWriter();
        ToscaSpecImpl.OBJECT_MAPPER.writeValue(stringWriter, modelEntity);
        return ToscaSpecImpl.OBJECT_MAPPER.readValue(stringWriter.toString(), aClass);
    }
}
