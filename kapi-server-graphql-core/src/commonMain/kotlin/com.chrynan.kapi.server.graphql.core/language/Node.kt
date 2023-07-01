package com.chrynan.kapi.server.graphql.core.language

import graphql.language.*
import java.io.Serializable

/**
 * The base interface for virtually all graphql language elements
 *
 * Every Node is immutable
 */
sealed interface Node : Serializable {

    /**
     * @return a list of the children of this node
     */
    val children: List<Node>

    /**
     * @return the source location where this node occurs
     */
    val sourceLocation: SourceLocation?

    /**
     * Nodes can have comments made on them, the following is one comment per line before a node.
     *
     * @return the list of comments or an empty list if there are none
     */
    val comments: List<Comment>

    /**
     * The chars which are ignored by the parser. (Before and after the current node)
     *
     * @return the ignored chars
     */
    val ignoredChars: IgnoredChars

    /**
     * A node can have a map of additional data associated with it.
     *
     * NOTE: The reason this is a map of strings is so the Node
     * can stay an immutable object, which Map&lt;String,Object&gt; would not allow
     * say.
     *
     * @return the map of additional data about this node
     */
    val additionalData: Map<String, String>

    /**
     * Compares just the content and not the children.
     *
     * @param node the other node to compare to
     *
     * @return isEqualTo
     */
    fun isContentEqualTo(node: Node): Boolean = this == node

    /**
     * Double-dispatch entry point.
     * A node receives a Visitor instance and then calls a method on a Visitor
     * that corresponds to an actual type of this Node. This binding however happens
     * at the compile time and therefore it allows to save on rather expensive
     * reflection based `instanceOf` check when decision based on the actual
     * type of Node is needed, which happens redundantly during traversing AST.
     *
     * Additional advantage of this pattern is to decouple tree traversal mechanism
     * from the code that needs to be executed when traversal "visits" a particular Node
     * in the tree. This leads to a better code re-usability and maintainability.
     *
     * @param context TraverserContext bound to this Node object
     * @param visitor Visitor instance that performs actual processing on the Nodes(s)
     *
     * @return Result of Visitor's operation.
     * Note! Visitor's operation might return special results to control traversal process.
     */
    // TODO: fun accept(context: TraverserContext<Node<*>?>?, visitor: NodeVisitor?): TraversalControl?
}
