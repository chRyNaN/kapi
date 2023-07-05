package com.chrynan.kapi.server.graphql.core.model

/**
 * Errors in graphql-java can have a classification to help with the processing
 * of errors.  Custom [graphql.GraphQLError] implementations could use
 * custom classifications.
 *
 *
 * graphql-java ships with a standard set of error classifications via [graphql.ErrorType]
 */
interface ErrorClassification {

    /**
     * This is called to create a representation of the error classification
     * that can be put into the `extensions` map of the graphql error under the key 'classification'
     * when [GraphQLError.toSpecification] is called
     *
     * @param error the error associated with this classification
     *
     * @return an object representation of this error classification
     */
    @Suppress("unused")
    fun toSpecification(error: GraphQLError?): Any? = this.toString()
}
