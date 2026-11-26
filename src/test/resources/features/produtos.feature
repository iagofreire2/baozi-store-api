# language: pt
Funcionalidade: Gerenciamento de Produtos na Baozi Store
  Como usuário da API
  Quero gerenciar o catálogo de produtos
  Para manter o controle de preços e disponibilidade em estoque

  Esquema do Cenário: Cadastrar produtos com diferentes preços e disponibilidades
    Dado que possuo os dados do produto "<nome>" com preço "<preco>" e estoque <estoque>
    Quando envio uma requisição POST para "/produtos"
    Então o status da resposta deve ser 201
    E o corpo da resposta deve conter o campo "id" preenchido
    E o campo "nome" da resposta deve ser "<nome>"
    E o campo numérico "preco" da resposta deve ser "<preco>"
    E o campo booleano "estoque" da resposta deve ser <estoque>

    Exemplos:
      | nome                       | preco | estoque |
      | Baozi Tradicional de Carne | 8.50  | true    |
      | Baozi de Frango Teriyaki   | 9.00  | true    |
      | Baozi Doce de Feijão       | 7.50  | false   |

  Cenário: Listar todos os produtos cadastrados
    Dado que já existe um produto cadastrado com nome "Baozi Tradicional de Carne", preço "8.50" e estoque true
    E que já existe um produto cadastrado com nome "Baozi de Frango Teriyaki", preço "9.00" e estoque true
    Quando envio uma requisição GET para "/produtos"
    Então o status da resposta deve ser 200
    E a lista retornada deve conter 2 registros

  Cenário: Consultar um produto por ID existente
    Dado que já existe um produto cadastrado com nome "Baozi Tradicional de Carne", preço "8.50" e estoque true
    Quando consulto esse produto pelo seu ID
    Então o status da resposta deve ser 200
    E o campo "nome" da resposta deve ser "Baozi Tradicional de Carne"
    E o campo numérico "preco" da resposta deve ser "8.50"
    E o campo booleano "estoque" da resposta deve ser true

  Cenário: Consultar um produto com ID inexistente
    Quando envio uma requisição GET para "/produtos/999"
    Então o status da resposta deve ser 404

  Cenário: Remover um produto existente
    Dado que já existe um produto cadastrado com nome "Baozi Tradicional de Carne", preço "8.50" e estoque true
    Quando removo esse produto pelo seu ID
    Então o status da resposta deve ser 204

  Cenário: Tentar remover um produto inexistente
    Quando envio uma requisição DELETE para "/produtos/999"
    Então o status da resposta deve ser 404