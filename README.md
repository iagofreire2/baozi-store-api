# Baozi Store API

API REST desenvolvida em **Java com Spring Boot** para a atividade prática da disciplina de **Desenvolvimento Web Back-End** do curso de **Análise e Desenvolvimento de Sistemas (UNINTER)**.

O sistema informatiza o controle básico de **clientes**, **produtos** e **pedidos** da **Baozi Store**, uma pequena loja local especializada na venda de pãozinho chinês artesanal (*baozi*).

---

## Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **Spring Web** (API REST / JSON)
- **Spring Data JPA** (Mapeamento ORM e persistência)
- **H2 Database** (Banco de dados relacional em memória)
- **Maven** (Gerenciamento de dependências)
- **Postman** (Testes de endpoints)

---

## Arquitetura do Projeto

O projeto segue o padrão **MVC do Spring**, organizado nos seguintes pacotes principais:

```text
src/main/java/com/baozi/store
├── controller/
│   ├── ClienteController.java
│   ├── PedidoController.java
│   └── ProdutoController.java
├── model/
│   ├── Cliente.java
│   ├── Pedido.java
│   └── Produto.java
├── repository/
│   ├── ClienteRepository.java
│   ├── PedidoRepository.java
│   └── ProdutoRepository.java
└── BaoziStoreApplication.java