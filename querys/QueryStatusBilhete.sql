select
re.service_id_on_operator                                                       as "servico"
lo.id_on_operator                                                               as "origem",
ld.id_on_operator                                                               as "destino",
to_char( re.departure_at +
(SELECT utc_offset FROM pg_timezone_names where name = 'America/Sao_Paulo'),
'YYYY-MM-DD')                                                              as "data",
rs.number                                                                       as "poltrona",
rs.ticket_number                                                                as "ticketNumber",
rs.bpe->> 'numeroSistema'                                                       as "numeroSistema",
op.name                                                                         as "Operadora"
from
reservations as re
inner join operator_locations as lo on re.origin_location_id = lo.location_id and re.operator_id = lo.operator_id
inner join operator_locations as ld on re.destination_location_id = ld.location_id and re.operator_id = ld.operator_id
inner join reserved_seats as rs on re.id = rs.reservation_id
inner join operators as op on re.operator_id = op.id
where re.order_id = ?