package com.api_aventesfinance.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.api_aventesfinance.dto.relatorio.EvolucaoReceitaDespesaDTO;
import com.api_aventesfinance.dto.relatorio.FluxoCaixaDTO;
import com.api_aventesfinance.dto.relatorio.FluxoCaixaDiarioDTO;
import com.api_aventesfinance.dto.relatorio.ReceitaDespesaCategoriaDTO;
import com.api_aventesfinance.dto.relatorio.SituacaoEmprestimoDTO;
import com.api_aventesfinance.enums.TipoEmprestimo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RelatorioFinanceiroRepository {

    @PersistenceContext
    private EntityManager em;

    public List<FluxoCaixaDTO> buscarFluxoCaixaMensal(String competencia, Long id_cliente) {
        String sql = """
                WITH lancamento_temp AS (
                        SELECT
                        id_lancamento,
                        max(dt_movimento) as dt_movimento,
                        MAX(id_centrocusto) AS id_centrocusto,
                        SUM(vl_receita) AS vl_receita,
                        SUM(vl_despesa) AS vl_despesa,
                        SUM(vl_receita) - SUM(vl_despesa) AS vl_saldo,
                        MAX(dt_anomes) AS dt_anomes,
                         MAX(id_cliente) AS id_cliente
                        FROM (
                        SELECT
                            l.id_lancamento,
                            l.dt_lancamento AS dt_movimento,
                            l.id_centrocusto,
                            0.00 AS vl_receita,
                            0.00 AS vl_despesa,
                            l.dt_anomes,
                            l.id_cliente
                        FROM lancamento l

                        UNION ALL

                        SELECT
                            il.id_lancamento,
                            dt_itemlancamento AS dt_movimento,
                            NULL AS id_centrocusto,
                            il.vl_itemlancamento AS vl_receita,
                            0.00 AS vl_despesa,
                            NULL AS dt_anomes,
                             null as id_cliente
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
                            NULL AS dt_anomes,
                             null as id_cliente
                        FROM item_lancamento il
                        JOIN categoria c ON il.id_categoria = c.id_categoria
                        WHERE c.tp_categoria = 'DESPESA'

                        ) AS t

                        GROUP BY  id_lancamento
                    )

                SELECT
                    id_lancamento,
                  dt_anomes AS mesAno,
                  lt.id_centrocusto,
                  cc.nm_centrocusto,
                  SUM(lt.vl_receita) AS receita,
                  SUM(lt.vl_despesa) AS despesa,
                  SUM(lt.vl_receita) - SUM(lt.vl_despesa) AS saldo
                FROM lancamento_temp lt
                JOIN centro_custo cc ON cc.id_centrocusto = lt.id_centrocusto
                WHERE dt_anomes = :competencia
                and lt.id_cliente = :id_cliente
                GROUP BY lt.dt_anomes, lt.id_centrocusto, cc.nm_centrocusto, id_lancamento
                ORDER BY lt.dt_anomes
                                          """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("competencia", competencia)
                .setParameter("id_cliente", id_cliente)
                .getResultList();

        return results.stream().map(r -> new FluxoCaixaDTO(
                (Long) r[0],
                (String) r[1],
                (Long) r[2],
                (String) r[3],
                BigDecimal.valueOf(((Number) r[4]).doubleValue()),
                BigDecimal.valueOf(((Number) r[5]).doubleValue()),
                BigDecimal.valueOf(((Number) r[6]).doubleValue()))).toList();
    }

    public List<FluxoCaixaDiarioDTO> buscarFluxoCaixaDiario(Long id) {
        String sql = """
                           WITH lancamento_temp AS (
                    SELECT
                        il.id_lancamento,
                        il.dt_itemlancamento AS dt_movimento,
                        MAX(l.id_centrocusto) AS id_centrocusto,
                        SUM(CASE WHEN c.tp_categoria = 'RECEITA' THEN il.vl_itemlancamento ELSE 0 END) AS vl_receita,
                        SUM(CASE WHEN c.tp_categoria = 'DESPESA' THEN il.vl_itemlancamento ELSE 0 END) AS vl_despesa,
                        SUM(CASE WHEN c.tp_categoria = 'RECEITA' THEN il.vl_itemlancamento ELSE 0 END) -
                        SUM(CASE WHEN c.tp_categoria = 'DESPESA' THEN il.vl_itemlancamento ELSE 0 END) AS vl_saldo
                    FROM item_lancamento il
                    JOIN lancamento l ON il.id_lancamento = l.id_lancamento
                    JOIN categoria c ON il.id_categoria = c.id_categoria
                    WHERE il.id_lancamento = :idLancamento
                    GROUP BY il.id_lancamento, il.dt_itemlancamento
                )

                SELECT
                    id_lancamento,
                    dt_movimento,
                    SUM(vl_receita) AS receita,
                    SUM(vl_despesa) AS despesa,
                    SUM(vl_saldo) AS saldo
                FROM lancamento_temp
                GROUP BY id_lancamento, dt_movimento
                ORDER BY dt_movimento;

                                           """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("idLancamento", id)
                .getResultList();

        double[] saldoAcumulado = { 0.0 };

        return results.stream().map(r -> {
            Long idLancamento = (Long) r[0];
            LocalDate data = ((java.sql.Date) r[1]).toLocalDate();
            double receita = (Double) r[2];
            double despesa = (Double) r[3];
            double saldo = receita - despesa;

            saldoAcumulado[0] += saldo;

            return new FluxoCaixaDiarioDTO(
                    idLancamento,
                    data,
                    receita,
                    despesa,
                    saldoAcumulado[0]);
        }).toList();
    }

    public List<ReceitaDespesaCategoriaDTO> buscarReceitaDespesaCategoria(String competencia, Long id_cliente) {
        String sql = """


                  SELECT
                   c.nm_categoria,
                   c.tp_categoria,
                   SUM(il.vl_itemlancamento) AS total
                   FROM item_lancamento il
                   JOIN categoria c ON il.id_categoria = c.id_categoria
                   join lancamento l on il.id_lancamento = l.id_lancamento
                where dt_anomes = :competencia
                and l.id_cliente = :id_cliente
                   GROUP BY c.nm_categoria, c.tp_categoria
                   ORDER BY c.tp_categoria, total DESC

                                             """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("competencia", competencia)
                .setParameter("id_cliente", id_cliente)
                .getResultList();

        return results.stream().map(r -> new ReceitaDespesaCategoriaDTO(
                (String) r[0],
                (String) r[1],
                (Double) r[2]

        )).toList();
    }

    public List<SituacaoEmprestimoDTO> buscarSituacaoEmprestimo(String competencia, Long id_cliente) {
        String sql = """
                                SELECT
                  e.id_emprestimo,
                  e.cd_emprestimo,
                  e.tp_emprestimo,
                  e.dt_emprestimo,
                  e.tp_status,
                  e.vl_total AS total_emprestado,
                  COALESCE(SUM(CASE WHEN ie.tp_itemstatus = 'QUITADO' THEN ie.vl_emprestimo ELSE 0 END), 0) AS total_pago,
                  e.vl_total - COALESCE(SUM(CASE WHEN ie.tp_itemstatus = 'QUITADO' THEN ie.vl_emprestimo ELSE 0 END), 0) AS em_aberto
                FROM emprestimo e
                LEFT JOIN item_emprestimo ie ON ie.id_emprestimo = e.id_emprestimo
                where e.dt_anomes = :competencia
                and e.id_cliente = :id_cliente
                GROUP BY e.id_emprestimo
                ORDER BY e.dt_emprestimo DESC;


                                                                          """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("competencia", competencia)
                .setParameter("id_cliente", id_cliente)
                .getResultList();

        return results.stream().map(r -> new SituacaoEmprestimoDTO(
                (Long) r[0],
                (String) r[1],
                (String) r[2],
                ((java.sql.Date) r[3]).toLocalDate(),
                (String) r[4],
                (Double) r[5],
                (Double) r[6],
                (Double) r[7]

        )).toList();
    }

    public List<EvolucaoReceitaDespesaDTO> buscarEvolucaoReceitaDespesa( Long id_cliente, LocalDate dt_atual) {
        String sql = """
            select
                id_centrocusto ,
                mc.dt_movimento,
                id_cliente,
                vl_receita,
                vl_despesa
            from movimentacao_caixa mc
                where id_cliente = :id_cliente
                  AND EXTRACT(YEAR FROM mc.dt_movimento) = EXTRACT(YEAR FROM CAST(:dt_atual AS DATE));
                                                                                          """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("id_cliente", id_cliente)
                .setParameter("dt_atual", dt_atual)
                .getResultList();

          double[] receitaAcumulado = { 0.0 };
          double[] despesaAcumulado = { 0.0 };

       return results.stream().map(r -> {
            Long id_centrocusto = (Long) r[0];
            LocalDate dt_movimento = ((java.sql.Date) r[1]).toLocalDate();
            Long id_clientetemp = (Long) r[2];
            Double vl_receita = (Double) r[3];
            Double vl_despesa = (Double) r[4];

            receitaAcumulado[0] += vl_receita;
            despesaAcumulado[0] += vl_despesa;

            return new EvolucaoReceitaDespesaDTO(
                id_centrocusto,
                dt_movimento,
                id_clientetemp,
                receitaAcumulado[0],
                despesaAcumulado[0]
        );
        }).toList();
    }
}
