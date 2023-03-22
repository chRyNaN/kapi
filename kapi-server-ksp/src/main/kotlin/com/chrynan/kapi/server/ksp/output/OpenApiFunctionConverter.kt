package com.chrynan.kapi.server.ksp.output

import com.chrynan.kapi.openapi.*
import com.chrynan.kapi.server.processor.core.model.ApiFunction
import com.chrynan.kapi.server.processor.core.model.HeaderParameter
import com.chrynan.kapi.server.processor.core.model.PathParameter
import com.chrynan.kapi.server.processor.core.model.QueryParameter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

class OpenApiFunctionConverter {

    operator fun invoke(function: ApiFunction) {
        function.path to PathItem(
            get = Operation(

            ),
            parameters = listOf(
                ReferenceOrType(
                    Parameter(
                        name = "",
                        inValue = Parameter.InValue.QUERY,
                        schema = Schema()
                    )
                )
            )
        )
    }

    private fun ApiFunction.toOperation(): Operation {
        val operationParameters =
            this.parameters.filter { it is PathParameter || it is HeaderParameter || it is QueryParameter }
                .map { parameter ->
                    Parameter(
                        name = parameter.value?.takeIf { it.isNotBlank() } ?: parameter.declaration.name,
                        inValue = when (parameter) {
                            is PathParameter -> Parameter.InValue.PATH
                            is HeaderParameter -> Parameter.InValue.HEADER
                            is QueryParameter -> Parameter.InValue.QUERY
                            else -> Parameter.InValue.QUERY
                        },
                        required = parameter is PathParameter || !parameter.declaration.hasDefaultValue,
                    )
                }.map { ReferenceOrType(it) }

        return Operation(
            operationId = this.kotlinFunction.name.short,
            description = this.kotlinFunction.documentation,
            deprecated = this.isDeprecated,
            parameters = operationParameters
        )
    }


}
