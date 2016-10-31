package org.tosca.docs.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.tosca.docs.model.AbstractModelEntity;
import org.tosca.docs.model.NodeType;
import org.tosca.docs.model.ToscaSpec;
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
    private Set<NodeType> nodeTypes;
    private Tree<NodeType> nodesTypesTree;

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

    @JsonIgnore
    @Override
    public Tree<NodeType> getNodeTypesTree() {
        if (nodesTypesTree == null) {
            nodesTypesTree = createTree(nodeTypes);
        }
        return nodesTypesTree;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getParent(T child) {
        if (child instanceof NodeType) {
            return (T) getNodeTypesTree().getParent((NodeType) child);
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
