package com.cmic.sim.maap.core.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {



    @Bean
    public Docket api() {
        ParameterBuilder apiVersionPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        apiVersionPar.name("Api-Version").description("need api version")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        //根据每个方法名也知道当前方法在设置什么参数
        pars.add(apiVersionPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }
}
