# Baozi Store API

API REST desenvolvida em **Java com Spring Boot** para a atividade prática da disciplina de **Desenvolvimento Web Back-End** do curso de **Análise e Desenvolvimento de Sistemas (UNINTER)**.

O sistema informatiza o controle básico de **clientes**, **produtos** e **pedidos** da **Baozi Store**, uma pequena loja local especializada na venda de pãozinho chinês artesanal (*baozi*), contando também com uma suíte completa de testes automatizados de API (BDD e validação de contrato).

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Web** (API REST / JSON)
- **Spring Data JPA** (Mapeamento ORM e persistência)
- **H2 Database** (Banco de dados relacional em memória)
- **Maven** (Gerenciamento de dependências)
- **Postman** (Testes manuais de endpoints)
- **Cucumber** (Testes automatizados BDD em Gherkin)
- **Rest Assured** (Automação de requisições e validações HTTP/JSON)
- **JSON Schema Validator** (Validação de contrato das respostas da API)

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
```

---

## Modelo de Dados

### Cliente (`clientes`)
| Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `Long` | Identificador único gerado automaticamente |
| `nome` | `String` | Nome completo do cliente |
| `clienteDesde` | `LocalDate` | Data de cadastro do cliente |

### Produto (`produtos`)
| Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `Long` | Identificador único gerado automaticamente |
| `nome` | `String` | Nome do produto |
| `preco` | `BigDecimal` | Preço unitário |
| `estoque` | `Boolean` | Disponibilidade em estoque (`true` / `false`) |

### Pedido (`pedidos`)
| Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `Long` | Identificador único gerado automaticamente |
| `clienteId` | `Long` | ID do cliente que realizou a compra |
| `produtoId` | `Long` | ID do produto comprado |
| `quantidade` | `Integer` | Quantidade de unidades solicitadas |

---

## Endpoints da API

| Recurso | Método | Rota | Descrição |
| :--- | :--- | :--- | :--- |
| **Clientes** | `POST` | `/clientes` | Cadastra um novo cliente |
| | `GET` | `/clientes` | Lista todos os clientes |
| | `GET` | `/clientes/{id}` | Consulta um cliente por ID |
| | `DELETE` | `/clientes/{id}` | Remove um cliente por ID |
| **Produtos** | `POST` | `/produtos` | Cadastra um novo produto |
| | `GET` | `/produtos` | Lista todos os produtos |
| | `GET` | `/produtos/{id}` | Consulta um produto por ID |
| | `DELETE` | `/produtos/{id}` | Remove um produto por ID |
| **Pedidos** | `POST` | `/pedidos` | Registra um novo pedido |
| | `GET` | `/pedidos` | Lista todos os pedidos |
| | `GET` | `/pedidos/{id}` | Consulta um pedido por ID |
| | `DELETE` | `/pedidos/{id}` | Remove um pedido por ID |

---

## Exemplos de Requisições (JSON)

### Cadastrar Cliente (`POST /clientes`)
```json
{
  "nome": "Iago de Jesus Freire - RU: 5174517",
  "clienteDesde": "2026-02-15"
}
```

### Cadastrar Produto (`POST /produtos`)
```json
{
  "nome": "Baozi Tradicional de Carne",
  "preco": 8.50,
  "estoque": true
}
```

### Registrar Pedido (`POST /pedidos`)
```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 4
}
```

---

## Como Executar Localmente

1. Clone o repositório:
   ```bash
   git clone [https://github.com/iagofreire2/baozi-store-api.git](https://github.com/iagofreire2/baozi-store-api.git)
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd baozi-store-api
   ```
3. Abra o projeto na IDE de sua preferência (como o **IntelliJ IDEA**) e execute a classe `BaoziStoreApplication.java`, ou rode via terminal com Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(No Windows PowerShell, utilize `.\mvnw.cmd spring-boot:run`.)*
4. A API estará disponível em `http://localhost:8080`.
5. Para inspecionar as tabelas no banco em memória, acesse o console do H2 em `http://localhost:8080/h2-console`:
    - **JDBC URL:** `jdbc:h2:mem:baozidb`
    - **User Name:** `sa`
    - **Password:** *(em branco)*

---

## Testes Automatizados (BDD + Contrato)

O projeto conta com uma suíte de testes de integração e contrato de API utilizando **Cucumber**, **Rest Assured** e **JSON Schema Validator**, integrada ao ciclo de vida do Spring Boot (`@SpringBootTest` com porta aleatória e banco H2 limpo antes de cada cenário).

### Estrutura dos Testes

```text
src/test
├── java/com/baozi/store/cucumber
│   ├── context/
│   │   └── TestContext.java                # Estado compartilhado por cenário (@ScenarioScope)
│   ├── hooks/
│   │   └── Hooks.java                      # Configuração do Rest Assured e limpeza do H2 (@Before)
│   ├── steps/
│   │   ├── ClienteSteps.java               # Passos específicos de Clientes
│   │   ├── CommonSteps.java                # Passos reutilizáveis (HTTP, status, campos e contrato)
│   │   ├── PedidoSteps.java                # Passos integrados de Pedidos
│   │   └── ProdutoSteps.java               # Passos específicos de Produtos
│   ├── CucumberSpringConfiguration.java    # Integração do Cucumber com o contexto Spring
│   └── RunCucumberTest.java                # Runner JUnit Platform Suite
└── resources
    ├── features/
    │   ├── clientes.feature                # Cenários CRUD de Clientes
    │   ├── pedidos.feature                 # Cenários CRUD integrados de Pedidos (com Contexto)
    │   └── produtos.feature                # Cenários CRUD de Produtos (com Esquema do Cenário)
    └── schemas/
        ├── cliente-schema.json             # Contrato JSON Draft-07 de Cliente
        ├── pedido-schema.json              # Contrato JSON Draft-07 de Pedido
        ├── produto-schema.json             # Contrato JSON Draft-07 de Produto
        └── produtos-lista-schema.json      # Contrato JSON Draft-07 da listagem de Produtos
```

### Cobertura dos Cenários (19 cenários)

- **Clientes (`clientes.feature`):** cadastro (`201`), listagem (`200`), consulta por ID (`200`), consulta inexistente (`404`), remoção (`204`) e validação de contrato.
- **Produtos (`produtos.feature`):** cadastro parametrizado com `Esquema do Cenário` (`201`), listagem múltipla (`200`), consulta por ID (`200`), consulta e remoção inexistentes (`404`), remoção (`204`) e validação de contrato (item e lista).
- **Pedidos (`pedidos.feature`):** fluxo integrado com `Contexto` (criação prévia de cliente e produto), cadastro (`201`), listagem (`200`), consulta por ID (`200`), verificação de vínculo (`clienteId` e `produtoId`), remoção com confirmação pós-exclusão (`204` seguido de `404`), cenários inexistentes (`404`) e validação de contrato.

### Como Executar os Testes

Execute via Maven na raiz do projeto (ou pela aba **Maven > Lifecycle > test** no IntelliJ):

```bash
./mvnw test
```

*(No Windows PowerShell, utilize `.\mvnw.cmd test`.)*

Após a execução, o relatório visual em HTML do Cucumber é gerado em:
`target/cucumber-reports/report.html`

---

## Autor

**Iago de Jesus Freire**
Estudante de Análise e Desenvolvimento de Sistemas — UNINTER