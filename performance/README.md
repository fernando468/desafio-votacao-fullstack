## Executar o teste de carga com k6

No diretório do projeto ou na pasta onde o arquivo `voting.js` está localizado, execute:

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
