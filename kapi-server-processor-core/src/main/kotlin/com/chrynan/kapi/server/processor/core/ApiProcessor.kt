package com.chrynan.kapi.server.processor.core

import com.chrynan.kapi.server.processor.core.model.ApiDefinition

/**
 * A component that processes [ApiDefinition]s and performs some functionality related to them.
 *
 * This component and the related models provides an abstraction layer over lower-level annotation processors and
 * compiler plugins by providing already formed models containing data related to API generation to be processed. This
 * abstraction layer separates the logic of generating API related components from processing lower-level, utility
 * specific, and complex components, such as abstract syntax trees. The abstraction layer promotes separation of
 * concerns, single responsibility, easier testability, and greater flexibility and scalability.
 *
 * An [ApiProcessor] is instantiated by an annotation processor like KSP (or compiler plugin) and its [process]
 * function is invoked for each processing round of that processor. It should be noted that no guarantee is made that
 * the [ApiProcessor] instance used between rounds will be the same instance used in previous rounds, because of this,
 * an [ApiProcessor] should be stateless. All round and API state information is passed to the [process] function.
 */
interface ApiProcessor {

    fun process(
        round: Int,
        currentApis: List<ApiDefinition>,
        allApis: List<ApiDefinition>
    )
}
