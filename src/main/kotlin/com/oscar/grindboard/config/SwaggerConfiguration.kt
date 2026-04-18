package com.oscar.grindboard.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("GrindBoard - to track your progress")
                    .version("v1.0.0")
                    .contact(Contact().name("OskarCh29").email("oskarcharytoniuk29@gmail.com"))
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/license/LICENCE-2.0"),
                    ).description(
                        """
                                        # TITLE DESCRIPTION

                                        ## TECHNICAL

                                        ### DESCRIPTION

                                        ### ERROR CONTESTS:
                                        <table>
                                        <tr>
                                        <th>Code</th>
                                        <th>HTTP status code</th>
                                        <th>Description</th>
                                        </tr>
                                        <tr>
                                         <td>STATUS</td>
                                         <td>STATUS CODE</td>
                                         <td>DESCRIPTION</td>
                                        </tr>
                                        </table>
                                        """,
                    ),
            ).servers(
                listOf(Server().url("http://localhost:8080").description("Local Development Server")),
            )
}
