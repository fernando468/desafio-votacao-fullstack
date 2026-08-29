# Votação

- Para acessar as informações sobre o desafio abra o arquivo: DESAFIO.md

## Tecnologias utilizadas no backend

- Java 21
- Spring Boot 4.1.1
- Maven
- JUnit
- Swagger
- Validation

## Tecnologias utilizadas para banco de dados

- PostgreSQL

## Tecnologias utilizadas no frontend

- React Versão: 19.2.8
- Typescript
- Material UI Versão: 9.3.1
- Axios
- Vite
- Zod
- React Hook Form

## Teconlogias utilizadas para infraestrutura

- Docker
- Docker Compose

---

# Como rodar a aplicação

## Backend

Requisitos: Java 21, Maven 3.9.12 e Docker

```bash
cd backend
```

```bash
docker-compose up -d
```

```bash
mvn clean install
```

```bash
mvn spring-boot:run
```

- Para rodar os testes: `mvn test`
- Para acessar a API: [http://localhost:8080/api](http://localhost:8080/api)
- Paraa acessar o Swagger: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

## Frontend

Requisitos: Node.js >= v24.13.0

```bash
cd frontend
```

```bash
npm install
```

```bash
npm run dev
```

- Para acessar o frontend: [http://localhost:5173/](http://localhost:5173/)

## Teste de Performance - Votação

## Executar o backend em modo de performance

```bash
cd backend
```

```bash
docker-compose up -d
```

```bash
./mvnw spring-boot:run "-Dspring-boot.run.profiles=performance"
```

Esse comando inicia a aplicação com a configuração do perfil do banco de dados de performance.

## Executar o teste de carga com k6

No diretório do projeto ou na pasta onde o arquivo `voting.js` está localizado, execute:

```bash
cd.. (Se não estiver na pasta raiz do projeto)
```

```bash
docker run --rm -i -v "/$PWD:/k6" -w //k6 grafana/k6 run - < voting.js
```

Esse comando:

- monta o diretório atual dentro do container do k6
- executa o script `voting.js`
- envia o relatório de resultado para o arquivo `summary.html`

## Observações

- O script envia requisições para a API em `http://host.docker.internal:8081/api/votos`.
- O cenário configurado simula `200 usuários virtuais` e `50 iterações por usuário`.
- O teste também define thresholds para:
  - `http_req_duration` com p(95) menor que `1000ms`
  - `http_req_failed` com taxa menor que `5%`

## Arquivos relevantes

- `voting.js` — cenário de teste de performance
- `summary.html` — relatório gerado pelo k6

## Dica

Para rodar tudo em sequência, mantenha o backend aberto em um terminal e execute o comando do Docker em outro terminal.
