# language: pt
Funcionalidade: Gerenciamento de Clientes na Baozi Store
  Como usuário da API
  Quero gerenciar o cadastro de clientes
  Para manter o controle de quem compra na loja

  Cenário: Cadastrar um novo cliente com sucesso
    Dado que possuo os dados do cliente "João Silva - RU: 1234567" cadastrado em "2026-02-15"
    Quando envio uma requisição POST para "/clientes"
    Então o status da resposta deve ser 201
    E o corpo da resposta deve conter o campo "id" preenchido
    E o campo "nome" da resposta deve ser "João Silva - RU: 1234567"
    E o corpo da resposta deve respeitar o contrato "cliente-schema.json"

  Cenário: Listar todos os clientes cadastrados
    Dado que já existe um cliente cadastrado com nome "João Silva - RU: 1234567" e data "2026-02-15"
    Quando envio uma requisição GET para "/clientes"
    Então o status da resposta deve ser 200
    E a lista retornada deve conter 1 registro

  Cenário: Consultar um cliente por ID existente
    Dado que já existe um cliente cadastrado com nome "João Silva - RU: 1234567" e data "2026-02-15"
    Quando consulto esse cliente pelo seu ID
    Então o status da resposta deve ser 200
    E o campo "nome" da resposta deve ser "João Silva - RU: 1234567"
    E o corpo da resposta deve respeitar o contrato "cliente-schema.json"

  Cenário: Consultar um cliente com ID inexistente
    Quando envio uma requisição GET para "/clientes/999"
    Então o status da resposta deve ser 404

  Cenário: Remover um cliente existente
    Dado que já existe um cliente cadastrado com nome "João Silva - RU: 1234567" e data "2026-02-15"
    Quando removo esse cliente pelo seu ID
    Então o status da resposta deve ser 204