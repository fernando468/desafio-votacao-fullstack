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
- Sonner
- Date-fns
- React Router Dom

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
- Para acessar o Swagger: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
- Para acessar o banco de dados: [http://localhost:5432/votacao](http://localhost:5432/votacao)

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
cd performance
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

# Explicações das decisões

### Versionamento da API [Tarefa bônus 3]

- A API utiliza o versionamento na URL, por exemplo: `/api/XXXXX/v1/` para facilitar a manutenção do projeto. Conforme a API evolui, a versão pode ser incrementada e o cliente autaliza para consumir a nova versão. Essa estratégia facilita para manter a compatibilidade com o cliente.

### Estrutura do Backend

- O backend utiliza o padrão de pastas com controllers, services, models, repository, mappers, configs e DTOs. A regra de negoócio fica no serviço e o controller é responsável por chamar o serviço que contem a lógica de negócio.

- Para testes foi utilizado o framework JUnit. Testes foram realizados no service para validar a lógica de
  negócio e também testes nos DTOs para validar se os objetos estão com as anotações de validação corretas.

- Para documentar a API foi utilizado o Swagger. O swagger facilita a documentação da API e permite o cliente ver os endpoints e o que precisa ser passado para cada endpoint.

### Tratamento de exceções

- Para tratar as exeções foi utilizado o `@RestControllerAdvice`. Essa classe é responsável por centralizar o tratamento de exeções para retornar uma mensagem de erro padrão.

### Estrutura do Frontend

- O frontend utiliza o padrão de pastas com components, pages, services, types, utils, hooks, context e styles. Os componentes são para permitir a reutilização de código, enquanto as regras ficam separadas em páginas.

### Estrutura do Banco de Dados

- O banco de dados utilizado é o PostgreSQL. Foi utilizado pelo bom desempenho e por ser um banco de dados relacional a integridade dos dados é garantida por chaves primarias e chaves estrangeiras .

### Estrutura do Teste de Performance [Tarefa bônus 2]

- Para realizar o teste de performance, foi utilizado o framework k6. O cenário testa com 200 usuários virtuais e 50 iterações por usuário e após o teste é gerado um relatório html com os resultados. Foi criado também uma base de dados para o teste de performance. Quando é iniciada a aplicação são criados associados, pautas e sessões para quando o testes for executado ele não precisar criar esses dados só para realizar a votação.
