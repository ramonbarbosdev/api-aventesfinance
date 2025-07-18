package com.api_aventesfinance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_aventesfinance.dto.LancamentoDTO;
import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.repository.ItemLancamentoRepository;
import com.api_aventesfinance.repository.LancamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private ItemLancamentoRepository itemObjetoRepository;

    // @Autowired
    // private ItemLancamentoService itemObjetoService;

    @Transactional
    public Lancamento salvarItens(Lancamento objeto) throws Exception {

        objeto.setVl_total(0.0);

        validarSequencial(objeto.getId_lancamento(), objeto.getCd_lancamento(), objeto.getDt_anomes());
        objeto = repository.save(objeto);

        Double vl_lancamento = 0.0;

        List<ItemLancamento> itens = objeto.getItens();

        if (itens != null && itens.size() > 0) {
            for (ItemLancamento item : itens) {
                item.setId_lancamento(objeto.getId_lancamento());
                validacaoCadastrar(item, itens, objeto.getId_lancamento());
                item = itemObjetoRepository.save(item);
                vl_lancamento += item.getVl_itemlancamento();
            }
        }

        objeto.setVl_total(vl_lancamento);
        validarValorTotalItem(itens, vl_lancamento);
        objeto = repository.save(objeto);

        return objeto;
    }



      public Long excluir(Long id)
    {
        // itemObjetoRepository.deleteByIdLancamento( id);
        // objetoRepository.deleteById(id);

        return id;
    }


    public void validarSequencial(Long id_lancamento, String cd_lancamento, String dt_anomes) throws Exception {

        if (id_lancamento != null)
            return;
        Boolean fl_existe = repository.obterSequencialExistente(cd_lancamento, dt_anomes);

        if (fl_existe != null && fl_existe) {
            throw new Exception("Codigo sequencial do lançamento ja existente.");
        }
    }

    public void validacaoCadastrar(ItemLancamento item, List<ItemLancamento> listaItens, Long id_lancamento)
            throws Exception {

        validarCategoria(item, listaItens, id_lancamento);
        validarValorMovimento(item, listaItens, id_lancamento);
        validarCodigoSequencialItem(item, listaItens, id_lancamento);

    }

    public void validarCodigoSequencialItem(ItemLancamento item, List<ItemLancamento> listaItens, Long id_lancamento)
            throws Exception {

        if (id_lancamento == null) {
            String codigo = item.getCd_itemlancamento();
            Boolean fl_existe = itemObjetoRepository.obterSequencialExistente(codigo, id_lancamento);

            if (fl_existe != null && fl_existe) {
                throw new Exception("Codigo sequencial do item está repetindo.");
            }
        }

    }

    public void validarValorMovimento(ItemLancamento item, List<ItemLancamento> listaItens, Long id_lancamento)
            throws Exception {
        Double vl_movimento = item.getVl_itemlancamento();

        if (vl_movimento == null || vl_movimento == 0.0) {
            throw new Exception("Valor do item não pode ser nulo ou zero");
        }
    }

    public void validarValorTotalItem(List<ItemLancamento> listaItens, Double vl_lancamento) throws Exception {
        Double vl_total = 0.0;

        for (ItemLancamento item : listaItens) {
            vl_total += item.getVl_itemlancamento();
        }

        if (vl_total == null || vl_total == 0.0) {
            throw new Exception("Valor do lançamento não pode ser nulo ou zero");
        }
        final double EPSILON = 0.00001; // Ajuste conforme necessário

        if (Math.abs(vl_total - vl_lancamento) > EPSILON) {
            throw new Exception("Valor total dos itens não corresponde ao valor do lançamento");
        }
    }

    public void validarCategoria(ItemLancamento item, List<ItemLancamento> listaItens, Long id_lancamento)
            throws RuntimeException {
        // Long id_categoria = item.getId_categoria();
        // Long id_tipooperacao = item.getId_tipooperacao();

        // if(id_tipooperacao != 1) //diferente de saldo inicial
        // {
        // Boolean fl_existeCategoria =
        // categoriaRepository.existeCategoria(id_categoria);

        // if(fl_existeCategoria == null || !fl_existeCategoria)
        // {
        // throw new RuntimeException("Categoria não encontrada");
        // }
        // }
    }

}
