
# Attormatus

Usando Spring boot, crie uma API simples para gerenciar Pessoas. Esta API deve permitir: 

- Criar uma pessoa

    Link para acessar o endpoint: http://localhost:8080/pessoas/createPessoa

    Modelo Json para testar: {
    "nome": "Francisca Brenda da Mata",
    "dtNascimento": "1967-09-12",
    "endereco": [
        {
            "cep": "59114-047",
            "logradouro": "Travessa da Alegria",
            "numero": "572",
            "cidade": "Natal"
            }
        ]
    }

- Editar uma pessoa

    Link para acessar o endpoint: http://localhost:8080/pessoas/editPessoa/1

    Modelo Json para testar: {
    "nome": "Francisca Brenda da Mata",
    "dtNascimento": "1967-09-12",
    "endereco": [
            {
            "cep": "99010-052",
            "logradouro": "Rua Coronel Chicuta",
            "numero": "159",
            "cidade": "Passo Fundo"
            }
        ]
    }

- Consultar uma pessoa: 
    
    Link para acessar o endpoint: http://localhost:8080/pessoas/consultarPessoa/1

- Listar pessoas: 

    Link para acessar o endpoint: http://localhost:8080/pessoas/listarPessoa

- Criar endereço para pessoa: 

    Link para acessar o endpoint: http://localhost:8080/pessoas/1/createEndereco

    Modelo Json para testar: {   
        "cep": "65052-040",
        "logradouro": "Residencial Begônias",
        "numero": "435",
        "cidade": "São Luís" 
    }

- Listar endereços da pessoa

    Link para acessar o endpoint: http://localhost:8080/pessoas/1/listarEndereco

- Poder informar qual endereço é o principal da pessoa: 

    Link para acessar o endpoint: http://localhost:8080/pessoas/1/principalAddress?cep=59114-047
