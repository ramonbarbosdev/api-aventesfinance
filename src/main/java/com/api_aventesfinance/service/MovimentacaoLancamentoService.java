package com.api_aventesfinance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;

@Service
public class MovimentacaoLancamentoService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void atualizarMovimentacaoLancamento(Long idLancamento) {
        entityManager
                .createNativeQuery("CALL public.movimentacao_lancamento(:id_lancamentoparam);")
                .setParameter("id_lancamentoparam", idLancamento)
                .executeUpdate();
    }
}
