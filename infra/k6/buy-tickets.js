// 1,000 buyers, each tries to buy one ticket for the same 100-seat event at the same moment.
// Run: docker compose -f infra/docker-compose.yml --profile loadtest run --rm k6
import http from 'k6/http';
import { check } from 'k6';
import { Counter } from 'k6/metrics';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8081';
const EVENT_ID = __ENV.EVENT_ID || '1';

// 409 "sold out" is a correct answer, not a failed request.
http.setResponseCallback(http.expectedStatuses(201, 409));

const sold = new Counter('tickets_sold');
const soldOut = new Counter('sold_out_responses');

export const options = {
    scenarios: {
        ticket_drop: {
            executor: 'per-vu-iterations',
            vus: 1000,
            iterations: 1,
            maxDuration: '1m',
        },
    },
};

export default function () {
    const res = http.post(
        `${BASE_URL}/events/${EVENT_ID}/reservations`,
        JSON.stringify({ quantity: 1 }),
        { headers: { 'Content-Type': 'application/json' } },
    );

    if (res.status === 201) sold.add(1);
    if (res.status === 409) soldOut.add(1);

    check(res, { 'bought or sold out': (r) => r.status === 201 || r.status === 409 });
}

export function teardown() {
    const report = http.get(`${BASE_URL}/events/${EVENT_ID}/sales`).json();
    console.log(`capacity=${report.capacity} sold=${report.sold} oversold=${report.oversold} available=${report.available}`);
}
