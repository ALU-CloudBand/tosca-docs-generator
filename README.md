# tosca-docs-generator

TOSCA Documents Generator

Generates TOSCA documentation for the various TOSCA entities (Node types, Relationship types, Capability types, etc.)

The generator tool requires the following inputs:

1. TOSCA spec in JSON format (An example TOSCA spec JSON file [tosca_spec.json.example](https://rawgit.com/ALU-CloudBand/tosca-docs-generator/master/src/test/resources/tosca_spec.json.example))
2. Internalization (i18n) messages file (Optional. Defaults to [messages.properties](https://github.com/ALU-CloudBand/tosca-docs-generator/blob/master/src/main/resources/org/tosca/docs/messages.properties))
3. CSS style file (Optional. Defaults to [default.css](https://github.com/ALU-CloudBand/tosca-docs-generator/blob/master/src/main/resources/org/tosca/docs/default.css))

This generates an HTML 5 document (which resembles a Javadoc document when using the default style).

## Example
An example of a generated HTML can be found [here](https://cdn.rawgit.com/ALU-CloudBand/tosca-docs-generator/master/src/test/resources/expected_tosca_spec.html)

---

## Executing the tosca-docs-generator


~~~~
java -jar tosca-docs-generator-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/tosca_entities_input.json > /tmp/tosca_entities_output.html
~~~~


#### An example tosca_entities_input.json

~~~~
{
  "title" : "TOSCA Simple Profile v1.0",
  "nodeTypes" : [ {
    "typeUri" : "tosca.nodes.Root",
    "description" : "The TOSCA Root Node Type is the default type that all other TOSCA base Node Types derive from.  This allows for all TOSCA nodes to have a consistent set of features for modeling and management (e.g., consistent definitions for requirements, capabilities and lifecycle interfaces).",
    "properties" : [ ],
    "attributes" : [ {
      "extensions" : { },
      "name" : "tosca_id",
      "required" : true,
      "type" : "string",
      "description" : "A unique identifier of the realized instance of a Node Template that derives from any TOSCA normative type.",
      "default" : null
    }, {
      "extensions" : { },
      "name" : "tosca_name",
      "required" : true,
      "type" : "string",
      "description" : "This attribute reflects the name of the Node Template as defined in the TOSCA service template.  This name is not unique to the realized instance model of corresponding deployed application as each template in the model can result in one or more instances (e.g., scaled) when orchestrated to a provider environment.",
      "default" : null
    } ],
    "derivedFrom" : null
  } ]
}
~~~~

### Customization

#### Localization (i18n)

Custom localization enables changing the texts from the default English language to other languages.
A locale can be selected by providing the JVM property ```user.language```. For example:
 
~~~~
java -Duser.language=es -jar tosca-docs-generator-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/tosca_entities_input.json > /tmp/tosca_entities_output.html
~~~~

#### Style (CSS)

Style customization enables changing the colors, fonts and layout of the generated document. 
A custom CSS style file can be used by providing the JVM property ```org.tosca.docs.generators.html.style```. For example:
  
~~~~
java -Dorg.tosca.docs.generators.html.style="/tmp/custom.css" -jar tosca-docs-generator-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/tosca_entities_input.json > /tmp/tosca_entities_output.html
~~~~

In case this property is not provided - the default style is [default.css](https://github.com/ALU-CloudBand/tosca-docs-generator/blob/master/src/main/resources/org/tosca/docs/default.css))