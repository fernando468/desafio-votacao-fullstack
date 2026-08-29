import { check } from 'k6';
import http from 'k6/http';

import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';

export const options = {
    scenarios: {
        votacao: {
            executor: 'per-vu-iterations',

            // 200 usuários virtuais
            vus: 200,

            // 50 votos por usuário
            iterations: 50,

            maxDuration: '5m',
        },
    },

    thresholds: {
        http_req_duration: [
            'p(95)<1000',
            'p(99)<2000',
        ],
        http_req_failed: ['rate<0.001'],
    },
};

export default function () {

    // Gera IDs únicos de 1 até 10.000
    const associadoId = ((__VU - 1) * 50) + __ITER + 1;

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

export function handleSummary(data) {
    return {
        'summary.html': htmlReport(data),
    };
}