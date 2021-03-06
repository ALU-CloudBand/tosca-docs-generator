package org.tosca.docs.generators.html;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Locale;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tosca.docs.model.CapabilityType;
import org.tosca.docs.model.Constraint;
import org.tosca.docs.model.NodeType;
import org.tosca.docs.model.Property;
import org.tosca.docs.model.RelationshipType;
import org.tosca.docs.model.ToscaSpec;
import org.tosca.docs.model.Type;
import org.tosca.docs.model.impl.AttributeImpl;
import org.tosca.docs.model.impl.CapabilityImpl;
import org.tosca.docs.model.impl.CapabilityTypeImpl;
import org.tosca.docs.model.impl.ConstraintImpl;
import org.tosca.docs.model.impl.InterfaceImpl;
import org.tosca.docs.model.impl.InterfaceMethodImpl;
import org.tosca.docs.model.impl.NodeTypeImpl;
import org.tosca.docs.model.impl.PropertyImpl;
import org.tosca.docs.model.impl.RelationshipTypeImpl;
import org.tosca.docs.model.impl.ToscaSpecImpl;

@SuppressWarnings({"java:S3740", "rawtypes"})
public class HtmlGeneratorTest {

    private static final String GREATER_OR_EQUAL = "greater_or_equal";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Sends the generated HTML to an external HTML validator tool and verifies there are no errors or
     * warnings
     *
     * @throws IOException if exception occurred reading the expected HTML file or sending the HTTP
     *                     request to the validator
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

    /**
     * Compare the JSON generated from the up-to-date ToscaSpec class against an example file.
     * <br/>
     * This method is not a real test but this will make sure that we always have a correct example file
     * that shows how a TOSCA spec JSON should look like.
     *
     * @throws IOException in case exception reading the example file.
     */
    @Test
    public void ensureToscaSpecExampleIsUpToDate() throws IOException {
        ToscaSpec spec = createFullToscaSpec();
        String specAsJson = ToscaSpecImpl.OBJECT_MAPPER.writeValueAsString(spec);
        // The file name extension is "example" and not "json" to prevent the IDE from reformatting the file
        // and break the comparison
        try (InputStream expectedStream = HtmlGeneratorTest.class.getResourceAsStream("/tosca_spec.json.example")) {
            Assert.assertEquals(IOUtils.toString(expectedStream, Charset.defaultCharset()).trim(), specAsJson.trim());
        }
    }

    @Test
    public void testToscaSpec() throws IOException {

        ToscaSpec spec = createFullToscaSpec();

        compareToscaSpecWithExpected(spec, "/expected_tosca_spec.html");
    }

    private ToscaSpec createFullToscaSpec() {
        ToscaSpec spec = new ToscaSpecImpl();
        spec.setNodeTypes(ImmutableSet.of(
                createCustomNodesCompute(),
                createToscaNodesRoot(),
                createToscaNodesCompute(),
                createToscaNodesSoftwareComponent(),
                createEmptyNodeType()));

        spec.setRelationshipTypes(ImmutableSet.of(
                createToscaRelationshipRoot(),
                createEmptyRelationshipType()
        ));
        spec.setCapabilityTypes(ImmutableSet.of(
                createToscaCapabilityRoot(),
                createToscaCapabilityContainer()
        ));
        return spec;
    }

    private CapabilityType createToscaCapabilityRoot() {
        return new CapabilityTypeImpl()
                .setTypeUri("tosca.capabilities.Root")
                .setDescription("This is the default (root) TOSCA Capability Type definition that all other TOSCA Capability Types derive from.");

    }

    private CapabilityType createToscaCapabilityContainer() {
        return new CapabilityTypeImpl()
                .setTypeUri("tosca.capabilities.Container")
                .setDerivedFrom("tosca.capabilities.Root")
                .setValidSourceTypes(ImmutableList.of(
                        "tosca.nodes.SoftwareComponent"
                ))
                .setProperties(ImmutableList.<Property>of(
                        new PropertyImpl()
                                .setName("num_cpus")
                                .setType(Type.INTEGER.toString())
                                .setDefaultValue("http")
                                .setConstraints(ImmutableList.<Constraint>of(
                                    new ConstraintImpl()
                                        .setOperator(GREATER_OR_EQUAL)
                                                .setValue(1)
                                )),
                        new PropertyImpl()
                                .setName("cpu_frequency")
                                .setType("scalar-unit.frequency")
                                .setConstraints(ImmutableList.<Constraint>of(
                                    new ConstraintImpl()
                                        .setOperator(GREATER_OR_EQUAL)
                                                .setValue(0.1)
                                )),
                        new PropertyImpl()
                                .setName("disk_size")
                                .setType("scalar-unit.size")
                                .setDefaultValue(false)
                                .setConstraints(ImmutableList.<Constraint>of(
                                    new ConstraintImpl()
                                        .setOperator(GREATER_OR_EQUAL)
                                                .setValue(0)
                                )),
                        new PropertyImpl()
                                .setName("mem_size")
                                .setType("scalar-unit.size")
                                .setDefaultValue(false)
                                .setConstraints(ImmutableList.<Constraint>of(
                                    new ConstraintImpl()
                                        .setOperator(GREATER_OR_EQUAL)
                                                .setValue(0)
                                ))

                ));
    }

    private RelationshipType createToscaRelationshipRoot() {
        return new RelationshipTypeImpl()
                .setTypeUri("tosca.relationships.Root")
                .setAttributes(ImmutableList.of(
                        new AttributeImpl()
                                .setName("tosca_id")
                                .setRequired(true)
                                .setType(Type.STRING.toString()),
                        new AttributeImpl()
                                .setName("tosca_name")
                                .setRequired(true)
                                .setType(Type.STRING.toString())
                ))
                .setInterfaces(ImmutableList.of(
                        new InterfaceImpl().setMethods(ImmutableMap.of(
                                "pre_configure_source",
                                new InterfaceMethodImpl()
                                        .setName("pre_configure_source")
                                        .setDescription("Operation to pre-configure the source endpoint")
                                        .setImplementation("pre_configure_source_impl"),
                                "pre_configure_target",
                                new InterfaceMethodImpl()
                                        .setName("pre_configure_target")
                                        .setDescription("Operation to pre-configure the target endpoint")
                                        .setImplementation("pre_configure_target_impl")
                        ))
                                .setName("tosca.interfaces.relationship.Configure")

                        )
                );
    }

    private RelationshipType createEmptyRelationshipType() {
        return new RelationshipTypeImpl()
                .setTypeUri("test.relationships.Empty")
                .setDerivedFrom("tosca.relationships.Root");
    }

    @Test
    public void testEmptyToscaSpec() throws IOException {
        compareToscaSpecWithExpected(new ToscaSpecImpl(), "/expected_empty_tosca_spec.html");
    }

    /**
     * Tests custom style and locale.
     *
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
            System.setProperty(HtmlGenerator.STYLE_SYSTEM_PROPERTY, "/org/tosca/docs/test.css");
            Locale.setDefault(new Locale("es"));

            compareToscaSpecWithExpected(spec, "/expected_customized_tosca_spec.html");
        } finally {
            System.clearProperty(HtmlGenerator.STYLE_SYSTEM_PROPERTY);
            Locale.setDefault(origDefaultLocale);
        }
    }

    @Test
    public void testInvalidStyleFile() throws IOException {

        String styleFilePath = "non_existing_style.css";

        thrown.expectMessage(styleFilePath);
        thrown.expect(IOException.class);

        ToscaSpec spec = new ToscaSpecImpl();
        spec.setNodeTypes(ImmutableSet.of(
                createToscaNodesRoot()
        ));

        try {
            System.setProperty(HtmlGenerator.STYLE_SYSTEM_PROPERTY, styleFilePath);
            generateHtml(spec);
        } finally {
            System.clearProperty(HtmlGenerator.STYLE_SYSTEM_PROPERTY);
        }
    }

    private void compareToscaSpecWithExpected(ToscaSpec spec, String expectedFilePath) throws IOException {
        StringWriter writer = generateHtml(spec);

        String actualHtml = writer.toString();
        File actualHtmlFile = new File("/tmp", new File(expectedFilePath).getName().replaceFirst("expected", "actual"));
        writeStringToFile(actualHtml, actualHtmlFile.getAbsolutePath());

        try (InputStream expectedStream = HtmlGeneratorTest.class.getResourceAsStream(expectedFilePath)) {
            Assert.assertEquals(IOUtils.toString(expectedStream, Charset.defaultCharset()).trim(), actualHtml.trim());
        }
    }

    private StringWriter generateHtml(ToscaSpec spec) throws IOException {
        spec = transform(spec, ToscaSpec.class);

        StringWriter writer = new StringWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator(spec);
        htmlGenerator.write(writer);
        return writer;
    }

    private void writeStringToFile(String content, String targetFileFullPath) throws IOException {
        final File file = new File(targetFileFullPath);
        final File dir = file.getParentFile();
        if (!dir.mkdirs() && !dir.exists()) {
            throw new RuntimeException("Cannot create directory " + dir.getAbsolutePath());
        }

        try (
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            byte[] bytes = content.getBytes();
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        }
    }

    private NodeType createToscaNodesSoftwareComponent() {
        return new NodeTypeImpl()
                .setTypeUri("tosca.nodes.SoftwareComponent")
                .setDerivedFrom("tosca.nodes.Root");
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
                )
                .setInterfaces(ImmutableList.of(
                        new InterfaceImpl().setMethods(ImmutableMap.of(
                                "create",
                                new InterfaceMethodImpl()
                                        .setName("create")
                                        .setDescription("Description for create operation")
                                        .setImplementation("create_impl"),
                                "configure",
                                new InterfaceMethodImpl()
                                        .setName("configure")
                                        .setDescription("Description for configure operation")
                                        .setImplementation("configure_impl")
                        ))
                        .setName("tosca.interfaces.node.lifecycle.Standard")

                )
                );


    }

    private NodeType createToscaNodesCompute() {

        return new NodeTypeImpl()
            .setTypeUri("tosca.nodes.Compute")
            .setDescription(
                "The TOSCA Compute node represents one or more real or virtual processors of software applications or services along with other essential local resources.  Collectively, the resources the compute node represents can logically be viewed as a (real or virtual) “server”.")
            .setDerivedFrom("tosca.nodes.Root")
            .setCapabilities(ImmutableList.of(
                new CapabilityImpl("host", "tosca.capabilities.Container")
                ))
                .setAttributes(ImmutableList.of(
                        new AttributeImpl()
                                .setName("private_address")
                                .setRequired(true)
                                .setType(Type.STRING.toString())
                                .setDescription("Private IP address"),

                        new AttributeImpl()
                                .setName("public_address")
                                .setDescription("Public IP address")
                                .setRequired(false)
                                .setType(Type.STRING.toString())
                        ,

                        new AttributeImpl()
                                .setName("networks")
                                .setRequired(true)
                                .setType(Type.MAP.toString()),

                        new AttributeImpl()
                                .setName("ports")
                                .setRequired(true)
                                .setType(Type.MAP.toString())
                        )
                )
                .setInterfaces(ImmutableList.of(
                        new InterfaceImpl().setMethods(ImmutableMap.of(
                                "create",
                                new InterfaceMethodImpl()
                                        .setName("create")
                                        .setDescription("Description for create operation")
                                        .setImplementation("create_customized_impl"),
                                "delete",
                                new InterfaceMethodImpl()
                                        .setName("delete")
                                        .setDescription("Description for delete operation")
                                        .setImplementation("delete_impl")
                        )).setName("tosca.interfaces.node.lifecycle.Standard")));



    }

    private NodeType createCustomNodesCompute() {
        return (NodeType) new NodeTypeImpl()
            .setTypeUri("custom.nodes.Compute")
            .setShorthandName("CustomVnf")
            .setTypeQualifiedName("TypeQualifiedName")
            .setDerivedFrom("tosca.nodes.Compute")
            .setCapabilities(ImmutableList.of(
                new CapabilityImpl("dependable", "tosca.capabilities.Dependable")
                ))
                .setProperties(ImmutableList.<Property>of(
                        new PropertyImpl()
                                .setName("custom_prop")
                                .setType(Type.STRING.toString())
                ))
                .setInterfaces(ImmutableList.of(
                        new InterfaceImpl().setMethods(ImmutableMap.of(
                                "stop",
                                new InterfaceMethodImpl()
                                        .setName("stop")
                                        .setDescription("Description for stop operation")
                                        .setImplementation("stop_customized_impl")
                                )).setName("tosca.interfaces.node.lifecycle.Standard")));

    }

    private <T> T transform(Object modelEntity, Class<T> aClass) throws IOException {
        StringWriter stringWriter = new StringWriter();
        ToscaSpecImpl.OBJECT_MAPPER.writeValue(stringWriter, modelEntity);
        return ToscaSpecImpl.OBJECT_MAPPER.readValue(stringWriter.toString(), aClass);
    }
}
