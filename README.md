Simulador de API de Garagem
API RESTful para gerenciamento de estacionamento e garagem, com funcionalidades para controle de entradas e saídas de veículos, monitoramento de vagas, cálculo dinâmico de preços e relatórios de receita.

Índice
Funcionalidades

Tecnologias Utilizadas

Pré-requisitos

Configuração e Execução

Endpoints

Entrada de Veículos (Batch)

Saída de Veículos (Batch)

Registro de Veículos Estacionados (Batch)

Consulta de Status de Vaga

Consulta de Receita

Consulta de Status da Placa

Importação de Dados

Tratamento de Erros

Swagger / OpenAPI

Contribuição

Licença

Funcionalidades
Registro de entrada de veículos: Recebe listas de veículos entrando, valida capacidade dos setores e calcula preço dinâmico baseado na ocupação.

Registro de saída de veículos: Recebe listas de saídas, atualiza status da vaga e calcula o tempo estacionado.

Registro de veículos estacionados: Atualiza o status dos veículos já estacionados (ex: tempo ou posição).

Consulta de status de vaga: Permite consultar se uma vaga específica (latitude/longitude) está ocupada ou livre.

Consulta de receita: Gera relatórios de receita por setor e por dia.

Consulta do status da placa: Verifica se um veículo está ativo na garagem.

Importação de dados: Carrega dados iniciais ou atualiza dados da garagem.

Tecnologias Utilizadas
Java 21

Spring Boot 3.5.0

Spring Data JPA

MySQL como banco de dados

Springdoc OpenAPI (Swagger UI) para documentação interativa

Maven para gerenciamento de dependências e build

Pré-requisitos
JDK 21 instalado

MySQL rodando e configurado

Maven instalado

Configurar o banco e variáveis no application.properties ou application.yml

Configuração e Execução
Clone o repositório:

git clone https://github.com/marcossssantos1/TesteApi.git
cd simulador-de-api
Configure o banco MySQL (crie o schema garagem):

CREATE DATABASE bancoteste;
Configure o arquivo src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/bancoteste?useSSL=false&serverTimezone=UTC
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Compile e rode a aplicação:

mvn clean install
mvn spring-boot:run
Acesse a API:

Swagger UI: http://localhost:8080/swagger-ui/index.html

JSON Docs: http://localhost:8080/v3/api-docs

Endpoints
Entrada de Veículos (Batch)
POST /entry

Recebe uma lista de veículos que estão entrando.

Calcula o preço dinamicamente baseado na ocupação dos setores.

Retorna status 200 se tudo ok, ou 207 para processamento parcial.

Exemplo de request:

json
[
  {
    "license_plate": "ABC1234",
    "entry_time": "2025-06-16T14:00:00",
    "event_type": "ENTRY"
  },
  {
    "license_plate": "XYZ9876",
    "entry_time": "2025-06-16T14:05:00",
    "event_type": "ENTRY"
  }
]
Exemplo de response (parcial com erros):

json
[
  {
    "license_plate": "ABC1234",
    "status": "Entrada registrada com sucesso"
  },
  {
    "license_plate": "XYZ9876",
    "error": "Veículo já está dentro da garagem!",
    "statusCode": 409
  }
]

Saída de Veículos (Batch)
POST /exit

Recebe uma lista de veículos saindo.

Atualiza vaga e calcula o tempo de permanência.

Retorna status 200 ou 207 conforme resultado.

Exemplo de request:

json
[
  {
    "license_plate": "ABC1234",
    "exit_time": "2025-06-16T16:00:00",
    "event_type": "EXIT"
  }
]

Registro de Veículos Estacionados (Batch)
POST /parked

Atualiza status dos veículos estacionados, podendo registrar tempo, posição, etc.

Consulta de Status de Vaga
POST /spot/status

Recebe latitude e longitude.

Retorna status da vaga (ocupada/livre) e detalhes.

Exemplo de request:

json
{
  "lat": -23.5505,
  "lng": -46.6333
}

Consulta de Receita
POST /revenue

Recebe data e setor.

Retorna receita calculada para o setor naquele dia.

Consulta de Status da Placa
POST /plate/status

Recebe placa do veículo.

Retorna se o veículo está ativo (dentro da garagem) ou não.

Importação de Dados
POST /import

Aciona importação dos dados da garagem a partir de arquivo ou fonte configurada.

Retorna mensagem de sucesso.

Tratamento de Erros
Código	Significado	Descrição
200	OK	Tudo processado com sucesso
207	Multi-Status	Processamento parcial com erros em alguns itens
400	Bad Request	Requisição mal formatada
409	Conflict	Conflito, como veículo já registrado ou setor cheio
500	Internal Server Error	Erro inesperado no servidor

Swagger / OpenAPI
A documentação automática está disponível em:

http://localhost:8080/swagger-ui/index.html
Permite testar todos os endpoints com exemplos e visualizar schemas das requisições e respostas.


