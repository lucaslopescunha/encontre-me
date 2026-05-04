-- Selecionar índices de tabela


-- SELECT
--     indexname AS nome_do_indice,
--     indexdef AS definicao
-- FROM
--     pg_indexes
-- WHERE
--     tablename = 'tb_log';



-- select all dos índices
-- SELECT
--     schemaname AS esquema,
--     tablename AS tabela,
--     indexname AS nome_do_indice,
--     indexdef AS definicao
-- FROM
--     pg_indexes
-- WHERE
--     schemaname NOT IN ('pg_catalog', 'information_schema')
-- ORDER BY
--     tablename,
--     indexname;
