select distinct
o.id as "ordem_id",
ol.id_on_operator as "origem",
dl.id_on_operator as "destino",
    to_char(
        r.departure_at +
            (SELECT utc_offset
            FROM pg_timezone_names
            where name = 'America/Sao_Paulo'),
        'YYYY-MM-DD'
    ) as "data",
r.service_id_on_operator as "servico",
r.operator_id as "operadora",
rs.name as "nome",
rs.id_on_operator as "transacao_id",
rs.ticket_number as "bilhete",
rs.number as "poltrona",
o.token as "token",
rs.rg as "rg"
from orders as o
inner join reservations as r on r.order_id = o.id
inner join reserved_seats as rs on rs.reservation_id = r.id
inner join operator_locations as ol on ol.location_id = r.origin_location_id and ol.operator_id = r.operator_id
inner join operator_locations as dl on dl.location_id = r.destination_location_id and dl.operator_id = r.operator_id
inner join operators as op on r.operator_id = op.id
where o.id = ?