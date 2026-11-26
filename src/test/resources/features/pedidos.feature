# language: pt
Funcionalidade: Gerenciamento de Pedidos na Baozi Store
  Como usuário da API
  Quero registrar e gerenciar pedidos
  Para controlar as vendas realizadas aos clientes

  Contexto:
    Dado que já existe um cliente cadastrado com nome "Iago de Jesus Freire - RU: 5174517" e data "2026-02-15"
    E que já existe um produto cadastrado com nome "Baozi Tradicional de Carne", preço "8.50" e estoque true

  Cenário: Registrar um novo pedido com sucesso
    Dado que preparo um pedido para esse cliente e esse produto com quantidade 4
    Quando envio uma requisição POST para "/pedidos"
    Então o status da resposta deve ser 201
    E o corpo da resposta deve conter o campo "id" preenchido
    E o pedido retornado deve estar vinculado ao cliente e produto cadastrados
    E o campo inteiro "quantidade" da resposta deve ser 4
    E o corpo da resposta deve respeitar o contrato "pedido-schema.json"

  Cenário: Listar todos os pedidos registrados
    Dado que já existe um pedido cadastrado para esse cliente e esse produto com quantidade 4
    Quando envio uma requisição GET para "/pedidos"
    Então o status da resposta deve ser 200
    E a lista retornada deve conter 1 registro

  Cenário: Consultar um pedido por ID existente
    Dado que já existe um pedido cadastrado para esse cliente e esse produto com quantidade 4
    Quando consulto esse pedido pelo seu ID
    Então o status da resposta deve ser 200
    E o pedido retornado deve estar vinculado ao cliente e produto cadastrados
    E o campo inteiro "quantidade" da resposta deve ser 4
    E o corpo da resposta deve respeitar o contrato "pedido-schema.json"

  Cenário: Consultar um pedido com ID inexistente
    Quando envio uma requisição GET para "/pedidos/999"
    Então o status da resposta deve ser 404

  Cenário: Remover um pedido existente e confirmar que não é mais encontrado
    Dado que já existe um pedido cadastrado para esse cliente e esse produto com quantidade 4
    Quando removo esse pedido pelo seu ID
    Então o status da resposta deve ser 204
    Quando consulto esse pedido pelo seu ID
    Então o status da resposta deve ser 404

  Cenário: Tentar remover um pedido inexistente
    Quando envio uma requisição DELETE para "/pedidos/999"
    Então o status da resposta deve ser 404