package org.tosca.docs.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.*;
import org.tosca.docs.utils.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ToscaSpecImpl implements ToscaSpec {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String title;

    @JsonDeserialize(as = TreeSet.class)
    private Set<NodeType> nodeTypes = new TreeSet<>();

    @JsonDeserialize(as = TreeSet.class)
    private Set<RelationshipType> relationshipTypes = new TreeSet<>();

    @JsonDeserialize(as = TreeSet.class)
    private Set<CapabilityType> capabilityTypes = new TreeSet<>();

    @JsonIgnore
    private Tree<NodeType> nodeTypesTree;
    @JsonIgnore
    private Tree<RelationshipType> relationshipTypesTree;
    @JsonIgnore
    private Tree<CapabilityType> capabilityTypesTree;


    /**
     * @return {@link #title}
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @param title {@link #title}
     * @return <source>this</source>
     */
    @Override
    public ToscaSpecImpl setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @return {@link #nodeTypes}
     */
    @Override
    public Set<NodeType> getNodeTypes() {
        return nodeTypes;
    }

    /**
     * @param nodeTypes {@link #nodeTypes}
     * @return <source>this</source>
     */
    @Override
    public ToscaSpec setNodeTypes(Set<NodeType> nodeTypes) {
        this.nodeTypes = nodeTypes;
        return this;
    }

    /**
     * @return {@link #relationshipTypes}
     */
    @Override
    public Set<RelationshipType> getRelationshipTypes() {
        return relationshipTypes;
    }

    /**
     * @param relationshipTypes {@link #relationshipTypes}
     * @return <source>this</source>
     */
    @Override
    public ToscaSpecImpl setRelationshipTypes(Set<RelationshipType> relationshipTypes) {
        this.relationshipTypes = relationshipTypes;
        return this;
    }

    /**
     * @return {@link #capabilityTypes}
     */
    @Override
    public Set<CapabilityType> getCapabilityTypes() {
        return capabilityTypes;
    }

    /**
     * @param capabilityTypes {@link #capabilityTypes}
     */
    @Override
    public ToscaSpecImpl setCapabilityTypes(Set<CapabilityType> capabilityTypes) {
        this.capabilityTypes = capabilityTypes;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getParent(T child) {
        if (child instanceof NodeType) {
            if (nodeTypesTree == null) {
                nodeTypesTree = createTree(nodeTypes);
            }
            return (T) nodeTypesTree.getParent((NodeType) child);
        } else if (child instanceof RelationshipType) {
            if (relationshipTypesTree == null) {
                relationshipTypesTree = createTree(relationshipTypes);
            }
            return (T) relationshipTypesTree.getParent((RelationshipType) child);
        } else if (child instanceof CapabilityType) {
            if (capabilityTypesTree == null) {
                capabilityTypesTree = createTree(capabilityTypes);
            }
            return (T) capabilityTypesTree.getParent((CapabilityType) child);
        }
        throw new IllegalArgumentException("Child of type " + child.getClass().getName() + " is not supported");
    }

    private <T extends AbstractModelEntity> Tree<T> createTree(Iterable<T> entities) {
        Map<String, T> entityById = new HashMap<>();
        for (T entity : entities) {
            entityById.put(entity.getId(), entity);
        }

        Tree<T> tree = new Tree<>();
        for (T entity : entities) {
            T parent;
            String parentId = entity.getDerivedFrom();
            if (parentId != null) {
                parent = entityById.get(parentId);
                if (parent == null) {
                    throw new IllegalStateException("Parent '" + parentId + "' of child '" + entity + "' does not exist");
                }

                tree.add(parent, entity);
            }
        }

        return tree;
    }
}
