package com.baozi.store.cucumber.steps;

import com.baozi.store.cucumber.context.TestContext;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
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

    @Quando("envio uma requisição DELETE para {string}")
    public void enviarRequisicaoDelete(String endpoint) {
        Response response = given()
                .when()
                .delete(endpoint);

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

    @E("o campo numérico {string} da resposta deve ser {string}")
    public void validarValorCampoNumerico(String campo, String valorEsperado) {
        context.getResponse().then().body(campo, notNullValue());
        BigDecimal valorRetornado = new BigDecimal(context.getResponse().jsonPath().getString(campo));
        assertThat(valorRetornado, comparesEqualTo(new BigDecimal(valorEsperado)));
    }

    @E("o campo booleano {string} da resposta deve ser {word}")
    public void validarValorCampoBooleano(String campo, String valorEsperado) {
        context.getResponse().then().body(campo, equalTo(Boolean.parseBoolean(valorEsperado)));
    }

    @E("a lista retornada deve conter {int} registro(s)")
    public void validarTamanhoLista(int quantidadeEsperada) {
        context.getResponse().then().body("$", hasSize(quantidadeEsperada));
    }

    @E("o campo inteiro {string} da resposta deve ser {int}")
    public void validarValorCampoInteiro(String campo, int valorEsperado) {
        context.getResponse().then().body(campo, equalTo(valorEsperado));
    }
}