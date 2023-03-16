package com.natan.restdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    public static final String DESCRICAO_INTERNAL_ERROR = "Erro interno no servidor";
    public static final String DESCRICAO_NOT_ACCEPTABLE = "Recurso nao possui representacao que poderia ser aceita pelo consumidor";
    public static final String DESCRICAO_BAD_REQUEST = "Requisicao invalida (erro no cliente)";
    public static final String DESCRICAO_UNSUPORTED_MEDIA = "Requisição recusada porque o corpo está em um formato não suportado";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.natan.restdemo"))
                .paths(PathSelectors.any()).build()
                .globalResponses(HttpMethod.GET, getResponseMessages())
                .globalResponses(HttpMethod.PUT, putPostResponseMessages())
                .globalResponses(HttpMethod.POST, putPostResponseMessages())
                .globalResponses(HttpMethod.DELETE, deleteResponseMessages())
                .apiInfo(apiInfo())
                .tags(new Tag("Veiculo", "api de veiculos"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("REST DEMO")
                .description("API para estudo de REST")
                .version("v1.0")
                .contact(new Contact("Natan Fagundes", "https://www.linkedin.com/in/natan-fagundes/", "natan.fags@gmail.com"))
                .build();
    }

    private List<Response> getResponseMessages() {

        Response internalServerError = getResponse(HttpStatus.INTERNAL_SERVER_ERROR, DESCRICAO_INTERNAL_ERROR);
        Response notAcceptable = getResponse(HttpStatus.NOT_ACCEPTABLE, DESCRICAO_NOT_ACCEPTABLE);

        return Arrays.asList(internalServerError, notAcceptable);
    }

    private List<Response> putPostResponseMessages() {

        Response badRequest = getResponse(HttpStatus.BAD_REQUEST, DESCRICAO_BAD_REQUEST);
        Response internalServerError = getResponse(HttpStatus.INTERNAL_SERVER_ERROR, DESCRICAO_INTERNAL_ERROR);
        Response notAcceptable = getResponse(HttpStatus.NOT_ACCEPTABLE, DESCRICAO_NOT_ACCEPTABLE);
        Response unsuportedMedia = getResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, DESCRICAO_UNSUPORTED_MEDIA);

        return Arrays.asList(badRequest, internalServerError, notAcceptable, unsuportedMedia);
    }

    private List<Response> deleteResponseMessages() {

        Response badRequest = getResponse(HttpStatus.BAD_REQUEST, DESCRICAO_BAD_REQUEST);
        Response internalServerError = getResponse(HttpStatus.INTERNAL_SERVER_ERROR, DESCRICAO_INTERNAL_ERROR);

        return Arrays.asList(badRequest, internalServerError);
    }

    private Response getResponse(HttpStatus status, String description) {
        return new ResponseBuilder()
                .code(status.name())
                .description(description)
                .build();
    }
}