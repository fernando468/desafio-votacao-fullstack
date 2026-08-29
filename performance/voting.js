import { check } from 'k6';
import http from 'k6/http';
// Importa o gerador de relatório HTML via CDN
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';

export const options = {
    scenarios: {
        votacao: {
            executor: 'per-vu-iterations',
            vus: 200,
            iterations: 500,
            maxDuration: '5m',
        },
    },

    thresholds: {
        http_req_duration: ['p(95)<1000'],
        http_req_failed: ['rate<0.05'],
    },
};

export default function () {
    const associadoId = ((__VU - 1) * 500) + __ITER + 1;

    const payload = JSON.stringify({
        associadoId: associadoId,
        sessaoId: 1,
        tipoVoto: Math.random() > 0.5 ? 'SIM' : 'NAO',
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(
        'http://host.docker.internal:8081/api/votos',
        payload,
        params
    );

    if (response.status !== 200 && response.status !== 201) {
        console.log(
            `STATUS=${response.status} BODY=${response.body}`
        );
    }

    check(response, {
        'status é sucesso': (r) =>
            r.status === 200 || r.status === 201,
    });
}

// Hook executado no final do teste para salvar os relatórios
export function handleSummary(data) {
    return {
        'summary.html': htmlReport(data), // Gera o arquivo HTML
    };
}