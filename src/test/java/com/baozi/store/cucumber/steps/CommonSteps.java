package com.baozi.store.cucumber.steps;

import com.baozi.store.cucumber.context.TestContext;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class CommonSteps {

    private final TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Quando("envio uma requisição POST para {string}")
    public void enviarRequisicaoPost(String endpoint) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(context.getPayload())
                .when()
                .post(endpoint);

        context.setResponse(response);
    }

    @Quando("envio uma requisição GET para {string}")
    public void enviarRequisicaoGet(String endpoint) {
        Response response = given()
                .when()
                .get(endpoint);

        context.setResponse(response);
    }

    @Entao("o status da resposta deve ser {int}")
    public void validarStatus(int statusEsperado) {
        context.getResponse().then().statusCode(statusEsperado);
    }

    @E("o corpo da resposta deve conter o campo {string} preenchido")
    public void validarCampoPreenchido(String campo) {
        context.getResponse().then().body(campo, notNullValue());
    }

    @E("o campo {string} da resposta deve ser {string}")
    public void validarValorCampoTexto(String campo, String valorEsperado) {
        context.getResponse().then().body(campo, equalTo(valorEsperado));
    }

    @E("a lista retornada deve conter {int} registro(s)")
    public void validarTamanhoLista(int quantidadeEsperada) {
        context.getResponse().then().body("$", hasSize(quantidadeEsperada));
    }
}