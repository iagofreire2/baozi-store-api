package com.baozi.store.cucumber.steps;

import com.baozi.store.cucumber.context.TestContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PedidoSteps {

    private final TestContext context;

    public PedidoSteps(TestContext context) {
        this.context = context;
    }

    @Dado("que preparo um pedido para esse cliente e esse produto com quantidade {int}")
    public void prepararDadosPedido(int quantidade) {
        context.setPayload(Map.of(
                "clienteId", context.getClienteId(),
                "produtoId", context.getProdutoId(),
                "quantidade", quantidade
        ));
    }

    @Dado("que já existe um pedido cadastrado para esse cliente e esse produto com quantidade {int}")
    public void cadastrarPedidoPreviamente(int quantidade) {
        Map<String, Object> novoPedido = Map.of(
                "clienteId", context.getClienteId(),
                "produtoId", context.getProdutoId(),
                "quantidade", quantidade
        );

        Response respostaCadastro = given()
                .contentType(ContentType.JSON)
                .body(novoPedido)
                .when()
                .post("/pedidos");

        respostaCadastro.then().statusCode(201);
        context.setPedidoId(respostaCadastro.jsonPath().getLong("id"));
    }

    @Quando("consulto esse pedido pelo seu ID")
    public void consultarPedidoPorId() {
        Response response = given()
                .pathParam("id", context.getPedidoId())
                .when()
                .get("/pedidos/{id}");

        context.setResponse(response);
    }

    @Quando("removo esse pedido pelo seu ID")
    public void removerPedidoPorId() {
        Response response = given()
                .pathParam("id", context.getPedidoId())
                .when()
                .delete("/pedidos/{id}");

        context.setResponse(response);
    }

    @E("o pedido retornado deve estar vinculado ao cliente e produto cadastrados")
    public void validarVinculoClienteEProduto() {
        context.getResponse().then()
                .body("clienteId", notNullValue())
                .body("produtoId", notNullValue());

        Long clienteIdRetornado = context.getResponse().jsonPath().getLong("clienteId");
        Long produtoIdRetornado = context.getResponse().jsonPath().getLong("produtoId");

        assertThat(clienteIdRetornado, equalTo(context.getClienteId()));
        assertThat(produtoIdRetornado, equalTo(context.getProdutoId()));
    }
}