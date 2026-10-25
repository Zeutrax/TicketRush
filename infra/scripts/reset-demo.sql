-- Puts the demo event back to 100 free seats so the load test can run again.
DELETE FROM reservations WHERE event_id = 1;
UPDATE events SET available = capacity WHERE id = 1;
SELECT id, name, capacity, available FROM events WHERE id = 1;
