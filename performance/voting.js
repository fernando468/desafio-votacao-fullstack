import { check } from 'k6';
import http from 'k6/http';


export const options = {
    scenarios: {
        votacao: {
            executor: 'per-vu-iterations',
            vus: 200,
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
        'http://host.docker.internal:8081/api/votos/v1',
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