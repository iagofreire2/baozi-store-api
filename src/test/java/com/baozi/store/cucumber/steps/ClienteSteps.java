package com.baozi.store.cucumber.steps;

import com.baozi.store.cucumber.context.TestContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ClienteSteps {

    private final TestContext context;

    public ClienteSteps(TestContext context) {
        this.context = context;
    }

    @Dado("que possuo os dados do cliente {string} cadastrado em {string}")
    public void prepararDadosCliente(String nome, String clienteDesde) {
        context.setPayload(Map.of(
                "nome", nome,
                "clienteDesde", clienteDesde
        ));
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
        context.setClienteId(respostaCadastro.jsonPath().getLong("id"));
    }

    @Quando("consulto esse cliente pelo seu ID")
    public void consultarClientePorId() {
        Response response = given()
                .pathParam("id", context.getClienteId())
                .when()
                .get("/clientes/{id}");

        context.setResponse(response);
    }

    @Quando("removo esse cliente pelo seu ID")
    public void removerClientePorId() {
        Response response = given()
                .pathParam("id", context.getClienteId())
                .when()
                .delete("/clientes/{id}");

        context.setResponse(response);
    }
}