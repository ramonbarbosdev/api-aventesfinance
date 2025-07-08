package com.api_aventesfinance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_aventesfinance.dto.LancamentoDTO;
import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.repository.ItemLancamentoRepository;
import com.api_aventesfinance.repository.LancamentoRepository;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository objetoRepository;

    @Autowired
    private ItemLancamentoRepository itemObjetoRepository;

    // @Autowired
    // private ItemLancamentoService itemObjetoService;

    public Lancamento salvarItens(Lancamento objeto) {

        objeto.setVl_total(0.0);
     
        // validacaoCadastrar(objeto);
        objeto = objetoRepository.save(objeto);

        Double vl_lancamento = 0.0;

        List<ItemLancamento> itens = objeto.getItemlancamento();

        if (itens != null && itens.size() > 0) {
            for (ItemLancamento item : itens) {
                item.setId_lancamento(objeto.getId_lancemento());
                // itemObjetoService.validacaoCadastrar(item, itens, objeto.getId_lancemento());
                item = itemObjetoRepository.save(item);
                vl_lancamento += item.getVl_itemlancamento();
            }
        }

        objeto.setVl_total(vl_lancamento);
        // validarValorTotalItem(itens, vl_lancamento);
        objeto = objetoRepository.save(objeto);

        return objeto;
    }
}
