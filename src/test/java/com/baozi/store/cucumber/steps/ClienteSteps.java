package com.baozi.store.cucumber.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class ClienteSteps {

    private Map<String, Object> payload;
    private Response response;
    private Long clienteId;

    @Dado("que possuo os dados do cliente {string} cadastrado em {string}")
    public void prepararDadosCliente(String nome, String clienteDesde) {
        this.payload = Map.of(
                "nome", nome,
                "clienteDesde", clienteDesde
        );
    }

    @Dado("que já existe um cliente cadastrado com nome {string} e data {string}")
    public void cadastrarClientePreviamente(String nome, String clienteDesde) {
        Map<String, Object> novoCliente = Map.of(
                "nome", nome,
                "clienteDesde", clienteDesde
        );

        Response respostaCadastro = given()
                .contentType(ContentType.JSON)
                .body(novoCliente)
                .when()
                .post("/clientes");

        respostaCadastro.then().statusCode(201);
        this.clienteId = respostaCadastro.jsonPath().getLong("id");
    }

    @Quando("envio uma requisição POST para {string}")
    public void enviarRequisicaoPost(String endpoint) {
        this.response = given()
                .contentType(ContentType.JSON)
                .body(this.payload)
                .when()
                .post(endpoint);
    }

    @Quando("envio uma requisição GET para {string}")
    public void enviarRequisicaoGet(String endpoint) {
        this.response = given()
                .when()
                .get(endpoint);
    }

    @Quando("consulto esse cliente pelo seu ID")
    public void consultarClientePorId() {
        this.response = given()
                .pathParam("id", this.clienteId)
                .when()
                .get("/clientes/{id}");
    }

    @Quando("removo esse cliente pelo seu ID")
    public void removerClientePorId() {
        this.response = given()
                .pathParam("id", this.clienteId)
                .when()
                .delete("/clientes/{id}");
    }

    @Entao("o status da resposta deve ser {int}")
    public void validarStatus(int statusEsperado) {
        this.response.then().statusCode(statusEsperado);
    }

    @E("o corpo da resposta deve conter o campo {string} preenchido")
    public void validarCampoPreenchido(String campo) {
        this.response.then().body(campo, notNullValue());
    }

    @E("o campo {string} da resposta deve ser {string}")
    public void validarValorCampo(String campo, String valorEsperado) {
        this.response.then().body(campo, equalTo(valorEsperado));
    }

    @E("a lista retornada deve conter {int} registro(s)")
    public void validarTamanhoLista(int quantidadeEsperada) {
        this.response.then().body("$", hasSize(quantidadeEsperada));
    }
}