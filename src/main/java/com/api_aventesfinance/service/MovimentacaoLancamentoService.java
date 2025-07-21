package com.api_aventesfinance.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api_aventesfinance.dto.MovimentacaoLancamentoDTO;
import com.api_aventesfinance.model.MovimentacaoLancamento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class MovimentacaoLancamentoService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void atualizarMovimentacaoLancamento(Long idLancamento) {

        String sql = """
                WITH lancamento_temp AS (
                    SELECT
                      id_lancamento,
                      dt_movimento,
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
                    WHERE id_lancamento = :idLancamento
                    GROUP BY dt_movimento, id_lancamento
                )

                select * from lancamento_temp;

                """;

        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("idLancamento", idLancamento)
                .getResultList();

        List<MovimentacaoLancamentoDTO> dtos = new ArrayList<>();

        for (Object[] row : results) {
            Long idL = ((Number) row[0]).longValue();
            LocalDate dtMov = ((java.sql.Date) row[1]).toLocalDate();
            Long idCC = row[2] != null ? ((Number) row[2]).longValue() : null;
            Double vlRec = row[3] != null ? ((Number) row[3]).doubleValue() : 0d;
            Double vlDesp = row[4] != null ? ((Number) row[4]).doubleValue() : 0d;
            Double vlSal = row[5] != null ? ((Number) row[5]).doubleValue() : 0d;
            String dtAno = (String) row[6];
            dtos.add(new MovimentacaoLancamentoDTO(idL, dtMov, idCC, vlRec, vlDesp, vlSal, dtAno));

            TypedQuery<MovimentacaoLancamento> q = entityManager.createQuery(
                    "SELECT m FROM MovimentacaoLancamento m WHERE m.id_lancamento = :id", MovimentacaoLancamento.class);
            q.setParameter("id", idL);

            List<MovimentacaoLancamento> lista = q.getResultList();
            MovimentacaoLancamento entity = lista.isEmpty() ? null : lista.get(0);

            if (entity == null) {
                entity = new MovimentacaoLancamento();
                entity.setId_lancamento(idL);
            }

            entity.setDt_movimento(dtMov);
            entity.setId_centrocusto(idCC);
            entity.setVl_receita(vlRec);
            entity.setVl_despesa(vlDesp);
            entity.setVl_saldo(vlSal);
            entity.setDt_anomes(dtAno);

            entityManager.merge(entity);
        }

        // for (MovimentacaoLancamentoDTO dto : dtos) {
        // // Buscar entidade existente
        // MovimentacaoLancamento entity =
        // entityManager.find(MovimentacaoLancamento.class, dto.getIdLancamento());

        // if (entity == null) {
        // // Criar nova entidade
        // entity = new MovimentacaoLancamento();
        // entity.setId_lancamento(dto.getIdLancamento());
        // }

        // // Atualizar os campos
        // entity.setDt_movimento(dto.getDtMovimento());
        // entity.setId_centrocusto(dto.getIdCentroCusto());
        // entity.setVl_receita(dto.getVlReceita());
        // entity.setVl_despesa(dto.getVlDespesa());
        // entity.setVl_saldo(dto.getVlSaldo());
        // entity.setDt_anomes(dto.getDtAnomes());

        // // Salvar ou atualizar
        // entityManager.merge(entity);
        // }

    }
}
