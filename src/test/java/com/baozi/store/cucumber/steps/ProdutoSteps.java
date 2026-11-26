package com.baozi.store.cucumber.steps;

import com.baozi.store.cucumber.context.TestContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProdutoSteps {

    private final TestContext context;

    public ProdutoSteps(TestContext context) {
        this.context = context;
    }

    @Dado("que possuo os dados do produto {string} com preço {string} e estoque {word}")
    public void prepararDadosProduto(String nome, String preco, String estoque) {
        context.setPayload(Map.of(
                "nome", nome,
                "preco", new BigDecimal(preco),
                "estoque", Boolean.parseBoolean(estoque)
        ));
    }

    @Dado("que já existe um produto cadastrado com nome {string}, preço {string} e estoque {word}")
    public void cadastrarProdutoPreviamente(String nome, String preco, String estoque) {
        Map<String, Object> novoProduto = Map.of(
                "nome", nome,
                "preco", new BigDecimal(preco),
                "estoque", Boolean.parseBoolean(estoque)
        );

        Response respostaCadastro = given()
                .contentType(ContentType.JSON)
                .body(novoProduto)
                .when()
                .post("/produtos");

        respostaCadastro.then().statusCode(201);
        context.setProdutoId(respostaCadastro.jsonPath().getLong("id"));
    }

    @Quando("consulto esse produto pelo seu ID")
    public void consultarProdutoPorId() {
        Response response = given()
                .pathParam("id", context.getProdutoId())
                .when()
                .get("/produtos/{id}");

        context.setResponse(response);
    }

    @Quando("removo esse produto pelo seu ID")
    public void removerProdutoPorId() {
        Response response = given()
                .pathParam("id", context.getProdutoId())
                .when()
                .delete("/produtos/{id}");

        context.setResponse(response);
    }
}