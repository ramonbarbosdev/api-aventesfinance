package com.api_aventesfinance.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api_aventesfinance.dto.MovimentacaoLancamentoDTO;
import com.api_aventesfinance.model.MovimentacaoCaixa;
import com.api_aventesfinance.model.MovimentacaoLancamento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class MovimentacaoCaixaService {
  @PersistenceContext
  private EntityManager em;

  @Transactional
  public void gravarMovimentoCaixa(Long id_cliente, Long id_centrocusto, LocalDate dt_movimentacao) {
    String sql = """

               WITH movimento_temp AS (
            SELECT
                dt_movimento,
                id_centrocusto,
                id_cliente,
                dt_anomes,
                SUM(vl_receita) AS vl_receita,
                SUM(vl_despesa) AS vl_despesa,
                SUM(vl_emprestimo) AS vl_emprestimo,
                fl_ultimamovimentacao
            FROM (
                SELECT
                    dt_movimento,
                    id_centrocusto,
                    id_cliente,
                    dt_anomes,
                    vl_receita,
                    vl_despesa,
                    vl_emprestimo,
                    vl_saldo,
                    TRUE AS fl_ultimamovimentacao
                FROM movimentacao_caixa
                WHERE id_centrocusto = :id_centrocusto
                  AND dt_movimento < :dt_movimentacao
                  AND id_cliente = :id_cliente

                UNION ALL

                SELECT
                    il.dt_itemlancamento AS dt_movimento,
                    l.id_centrocusto,
                    l.id_cliente,
                    l.dt_anomes,
                    CASE WHEN c.tp_categoria = 'RECEITA' THEN il.vl_itemlancamento ELSE 0 END AS vl_receita,
                    CASE WHEN c.tp_categoria = 'DESPESA' THEN il.vl_itemlancamento ELSE 0 END AS vl_despesa,
                    0.00 AS vl_emprestimo,
                    0.00 AS vl_saldo,
                    FALSE AS fl_ultimamovimentacao
                FROM item_lancamento il
                JOIN lancamento l ON il.id_lancamento = l.id_lancamento
                JOIN categoria c ON il.id_categoria = c.id_categoria
                WHERE l.id_centrocusto = :id_centrocusto
                  AND il.dt_itemlancamento >= :dt_movimentacao
                  AND l.id_cliente = :id_cliente

                UNION ALL

                -- LINHA DEFAULT ZERADA CASO NÃO EXISTAM REGISTROS
                SELECT
                    :dt_movimentacao AS dt_movimento,
                    :id_centrocusto AS id_centrocusto,
                    :id_cliente AS id_cliente,
                    :dt_movimentacao AS dt_anomes,
                    0.00 AS vl_receita,
                    0.00 AS vl_despesa,
                    0.00 AS vl_emprestimo,
                    0.00 AS vl_saldo,
                    FALSE AS fl_ultimamovimentacao
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM movimentacao_caixa mc
                    WHERE mc.id_centrocusto = :id_centrocusto
                      AND mc.dt_movimento < :dt_movimentacao
                      AND mc.id_cliente = :id_cliente

                    UNION

                    SELECT 1
                    FROM item_lancamento il
                    JOIN lancamento l ON il.id_lancamento = l.id_lancamento
                    WHERE l.id_centrocusto = :id_centrocusto
                      AND il.dt_itemlancamento >= :dt_movimentacao
                      AND l.id_cliente = :id_cliente
                )
            ) t
            GROUP BY dt_movimento, id_centrocusto, id_cliente, dt_anomes, fl_ultimamovimentacao
        ),

        saldo_anterior_base AS (
            SELECT
                COALESCE(MAX(vl_saldo), 0) AS saldo_anterior
            FROM movimentacao_caixa
            WHERE id_centrocusto = :id_centrocusto
              AND id_cliente = :id_cliente
              AND dt_movimento < :dt_movimentacao
        ),

        movimento_com_saldo AS (
            SELECT
                mt.*,
                (SELECT saldo_anterior FROM saldo_anterior_base) +
                SUM(mt.vl_receita - mt.vl_despesa)
                OVER (PARTITION BY id_centrocusto, id_cliente ORDER BY dt_movimento) AS vl_saldo
            FROM movimento_temp mt
        ),

        exclusao AS (
            DELETE FROM movimentacao_caixa
            WHERE id_centrocusto = :id_centrocusto
              AND dt_movimento >= :dt_movimentacao
              AND id_cliente = :id_cliente
            RETURNING 1
        )

        INSERT INTO movimentacao_caixa (
            dt_movimento,
            id_centrocusto,
            id_cliente,
            dt_anomes,
            vl_receita,
            vl_despesa,
            vl_emprestimo,
            vl_saldo
        )
        SELECT
            dt_movimento,
            id_centrocusto,
            id_cliente,
            dt_anomes,
            vl_receita,
            vl_despesa,
            vl_emprestimo,
            vl_saldo
        FROM movimento_com_saldo;


                                                             """;

    em.createNativeQuery(sql)
        .setParameter("id_centrocusto", id_centrocusto)
        .setParameter("id_cliente", id_cliente)
        .setParameter("dt_movimentacao", dt_movimentacao)
        .executeUpdate();
  }

}
