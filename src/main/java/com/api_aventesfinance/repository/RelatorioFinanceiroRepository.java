package com.api_aventesfinance.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.api_aventesfinance.dto.relatorio.FluxoCaixaDTO;
import com.api_aventesfinance.dto.relatorio.FluxoCaixaDiarioDTO;
import com.api_aventesfinance.dto.relatorio.ReceitaDespesaCategoriaDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RelatorioFinanceiroRepository {

    @PersistenceContext
    private EntityManager em;

    public List<FluxoCaixaDTO> buscarFluxoCaixaMensal() {
        String sql = """
                WITH lancamento_temp AS (
                        SELECT
                        id_lancamento,
                        max(dt_movimento) as dt_movimento,
                        MAX(id_centrocusto) AS id_centrocusto,
                        SUM(vl_receita) AS vl_receita,
                        SUM(vl_despesa) AS vl_despesa,
                        SUM(vl_receita) - SUM(vl_despesa) AS vl_saldo,
                        MAX(dt_anomes) AS dt_anomes
                        FROM (
                        SELECT
                            l.id_lancamento,
                            l.dt_lancamento AS dt_movimento,
                            l.id_centrocusto,
                            0.00 AS vl_receita,
                            0.00 AS vl_despesa,
                            l.dt_anomes
                        FROM lancamento l

                        UNION ALL

                        SELECT
                            il.id_lancamento,
                            dt_itemlancamento AS dt_movimento,
                            NULL AS id_centrocusto,
                            il.vl_itemlancamento AS vl_receita,
                            0.00 AS vl_despesa,
                            NULL AS dt_anomes
                        FROM item_lancamento il
                        JOIN categoria c ON il.id_categoria = c.id_categoria
                        WHERE c.tp_categoria = 'RECEITA'

                        UNION ALL

                        SELECT
                            il.id_lancamento,
                            dt_itemlancamento AS dt_movimento,
                            NULL AS id_centrocusto,
                            0.00 AS vl_receita,
                            il.vl_itemlancamento AS vl_despesa,
                            NULL AS dt_anomes
                        FROM item_lancamento il
                        JOIN categoria c ON il.id_categoria = c.id_categoria
                        WHERE c.tp_categoria = 'DESPESA'

                        ) AS t

                        GROUP BY  id_lancamento
                    )

                SELECT
                  dt_anomes AS mesAno,
                  lt.id_centrocusto,
                  cc.nm_centrocusto,
                  SUM(lt.vl_receita) AS receita,
                  SUM(lt.vl_despesa) AS despesa,
                  SUM(lt.vl_receita) - SUM(lt.vl_despesa) AS saldo
                FROM lancamento_temp lt
                JOIN centro_custo cc ON cc.id_centrocusto = lt.id_centrocusto
                GROUP BY lt.dt_anomes, lt.id_centrocusto, cc.nm_centrocusto
                ORDER BY lt.dt_anomes

                                          """;

        List<Object[]> results = em.createNativeQuery(sql).getResultList();

        return results.stream().map(r -> new FluxoCaixaDTO(
                (String) r[0],
                (Long) r[1],
                (String) r[2],
                BigDecimal.valueOf(((Number) r[3]).doubleValue()),
                BigDecimal.valueOf(((Number) r[4]).doubleValue()),
                BigDecimal.valueOf(((Number) r[5]).doubleValue()))).toList();
    }

    public List<FluxoCaixaDiarioDTO> buscarFluxoCaixaDiario() {
        String sql = """
               WITH lancamento_temp AS (
                    SELECT
                        id_lancamento,
                        dt_movimento,
                        MAX(id_centrocusto) AS id_centrocusto,
                        SUM(vl_receita) AS vl_receita,
                        SUM(vl_despesa) AS vl_despesa,
                        SUM(vl_receita) - SUM(vl_despesa) AS vl_saldo
                    FROM (
                        SELECT
                        l.id_lancamento,
                        l.dt_lancamento AS dt_movimento,
                        l.id_centrocusto,
                        0.00 AS vl_receita,
                        0.00 AS vl_despesa
                        FROM lancamento l

                        UNION ALL

                        SELECT
                        il.id_lancamento,
                        il.dt_itemlancamento AS dt_movimento,
                        NULL AS id_centrocusto,
                        il.vl_itemlancamento AS vl_receita,
                        0.00 AS vl_despesa
                        FROM item_lancamento il
                        JOIN categoria c ON il.id_categoria = c.id_categoria
                        WHERE c.tp_categoria = 'RECEITA'

                        UNION ALL

                        SELECT
                        il.id_lancamento,
                        il.dt_itemlancamento AS dt_movimento,
                        NULL AS id_centrocusto,
                        0.00 AS vl_receita,
                        il.vl_itemlancamento AS vl_despesa
                        FROM item_lancamento il
                        JOIN categoria c ON il.id_categoria = c.id_categoria
                        WHERE c.tp_categoria = 'DESPESA'
                    ) t
                    GROUP BY id_lancamento, dt_movimento
                    )

                    SELECT 
                    id_lancamento,
                    dt_movimento,
                    SUM(vl_receita) AS receita,
                    SUM(vl_despesa) AS despesa,
                    SUM(vl_saldo) AS saldo
                    FROM lancamento_temp
                    GROUP BY dt_movimento, id_lancamento
                    ORDER BY dt_movimento;

                                          """;

        List<Object[]> results = em.createNativeQuery(sql).getResultList();

        return results.stream().map(r -> new FluxoCaixaDiarioDTO(
                (Long) r[0],
                ((java.sql.Date) r[1]).toLocalDate(),
                (Double) r[2],
                (Double) r[3],
                (Double) r[4]
                )).toList();
    }


    public List<ReceitaDespesaCategoriaDTO> buscarReceitaDespesaCategoria() {
        String sql = """
                WITH lancamento_temp AS (
                        SELECT
                        id_lancamento,
                        max(dt_movimento) as dt_movimento,
                        MAX(id_centrocusto) AS id_centrocusto,
                        SUM(vl_receita) AS vl_receita,
                        SUM(vl_despesa) AS vl_despesa,
                        SUM(vl_receita) - SUM(vl_despesa) AS vl_saldo,
                        MAX(dt_anomes) AS dt_anomes
                        FROM (
                        SELECT
                            l.id_lancamento,
                            l.dt_lancamento AS dt_movimento,
                            l.id_centrocusto,
                            0.00 AS vl_receita,
                            0.00 AS vl_despesa,
                            l.dt_anomes
                        FROM lancamento l

                        UNION ALL

                        SELECT
                            il.id_lancamento,
                            dt_itemlancamento AS dt_movimento,
                            NULL AS id_centrocusto,
                            il.vl_itemlancamento AS vl_receita,
                            0.00 AS vl_despesa,
                            NULL AS dt_anomes
                        FROM item_lancamento il
                        JOIN categoria c ON il.id_categoria = c.id_categoria
                        WHERE c.tp_categoria = 'RECEITA'

                        UNION ALL

                        SELECT
                            il.id_lancamento,
                            dt_itemlancamento AS dt_movimento,
                            NULL AS id_centrocusto,
                            0.00 AS vl_receita,
                            il.vl_itemlancamento AS vl_despesa,
                            NULL AS dt_anomes
                        FROM item_lancamento il
                        JOIN categoria c ON il.id_categoria = c.id_categoria
                        WHERE c.tp_categoria = 'DESPESA'

                        ) AS t

                        GROUP BY  id_lancamento
                    )

               SELECT 
                c.nm_categoria,
                c.tp_categoria,
                SUM(il.vl_itemlancamento) AS total
                FROM item_lancamento il
                JOIN categoria c ON il.id_categoria = c.id_categoria
                GROUP BY c.nm_categoria, c.tp_categoria
                ORDER BY c.tp_categoria, total DESC

                                          """;

        List<Object[]> results = em.createNativeQuery(sql).getResultList();

        return results.stream().map(r -> new ReceitaDespesaCategoriaDTO(
                (String) r[0],
                (String) r[1],
                (Double) r[2]
                
                )).toList();
    }
}
