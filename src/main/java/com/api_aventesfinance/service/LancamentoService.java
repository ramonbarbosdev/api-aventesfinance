package com.api_aventesfinance.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api_aventesfinance.dto.LancamentoDTO;
import com.api_aventesfinance.enums.TipoCategoria;
import com.api_aventesfinance.model.Categoria;
import com.api_aventesfinance.model.Emprestimo;
import com.api_aventesfinance.model.ItemEmprestimo;
import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.repository.CategoriaRepository;
import com.api_aventesfinance.repository.ItemLancamentoRepository;
import com.api_aventesfinance.repository.LancamentoRepository;
import com.api_aventesfinance.repository.MovimentacaoLancamentoRepository;
import com.api_aventesfinance.utils.DataUtils;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private ItemLancamentoRepository itemObjetoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MovimentacaoCaixaService movimentacaoCaixaService;

    @Autowired
    private CompetenciaService competenciaService;

    @Transactional(rollbackFor = Exception.class)
    public Lancamento salvarItens(Lancamento objeto, Long id_cliente) throws Exception {

        objeto.setVl_total(0.0);
        objeto.setId_cliente(id_cliente);
        Double vl_lancamento = 0.0;
        objeto.setDt_anomes(DataUtils.formatarAnoMes(objeto.getDt_lancamento()));

        validarObjeto(objeto);
        List<ItemLancamento> itens = objeto.getItens();
        objeto.setItens(null);

        objeto = repository.save(objeto);

        removerItens(objeto, itens);

        if (itens != null && itens.size() > 0) {
            for (ItemLancamento item : itens) {
                item.setId_lancamento(objeto.getId_lancamento());

                if (item.getId_itemlancamento() == null || item.getId_itemlancamento() == 0) {
                    item.setId_itemlancamento(null);
                }

                validacaoCadastrar(item, itens, objeto);
                item = itemObjetoRepository.save(item);
                vl_lancamento += item.getVl_itemlancamento();
            }
        }

        objeto.setVl_total(vl_lancamento);
        validarValorTotalItem(itens, vl_lancamento);
        objeto.setItens(itens);
        objeto = repository.save(objeto);

        movimentacaoCaixaService.gravarMovimentoCaixa(
                objeto.getId_cliente(),
                objeto.getId_centrocusto(),
                objeto.getDt_lancamento());

        return objeto;
    }

    public void removerItens(Lancamento objeto, List<ItemLancamento> itensAtualizados) {
        List<ItemLancamento> itensPersistidos = itemObjetoRepository.findbyIdItemLancamento(objeto.getId_lancamento());

        for (ItemLancamento itemPersistido : itensPersistidos) {
            boolean aindaExiste = itensAtualizados.stream()
                    .anyMatch(i -> i.getId_itemlancamento() != null
                            && i.getId_itemlancamento().equals(itemPersistido.getId_itemlancamento()));

            if (!aindaExiste) {
                if (itemPersistido.getId_itemlancamento() != null && itemPersistido.getId_itemlancamento() > 0) {
                    itemObjetoRepository.deleteById(itemPersistido.getId_itemlancamento());
                }
            }
        }
    }

    public void validarObjeto(Lancamento objeto) throws Exception {

        competenciaService.verificarStatusCompetencia(objeto.getDt_anomes(), objeto.getId_cliente());
        validarSequencia(objeto.getId_lancamento(), objeto.getCd_lancamento(), objeto.getDt_anomes(),
                objeto.getId_cliente());

        Optional<Lancamento> fl_existe = repository.existeLancamentoPorCentroCustoMes(
                objeto.getDt_anomes(),
                objeto.getId_centrocusto(),
                objeto.getId_lancamento(),
                objeto.getId_cliente());

        if (fl_existe.isPresent()) {
            throw new Exception("Já existe um lançamento no mês com o centro de custo informado.");
        }
    }

    public void validarSequencia(Long id_lancamento, String cd_lancamento, String dt_anomes, Long id_cliente)
            throws Exception {

        if (id_lancamento != null)
            return;
        Boolean fl_existe = repository.obterSequencialExistente(cd_lancamento, dt_anomes, id_cliente);

        if (fl_existe != null && fl_existe) {
            throw new Exception("Codigo sequencial do lançamento ja existente.");
        }
    }

    public void validacaoCadastrar(ItemLancamento item, List<ItemLancamento> listaItens, Lancamento objeto)
            throws Exception {

        // validarCategoria(item, listaItens, id_lancamento);
        validarValorMovimento(item, listaItens, objeto.getId_lancamento());
        validarCodigoSequencialItem(item, listaItens, objeto.getId_lancamento());

        if (item.getDt_itemlancamento().isBefore(objeto.getDt_lancamento())) {
            throw new Exception("A data do item não pode ser menor que a data do lançamento");

        }

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
            throws Exception {

        Long id_categoriaList = listaItens.get(0).getId_categoria(); // pega a primeira posicao
        Optional<Categoria> categorialist = categoriaRepository.findById(id_categoriaList);
        TipoCategoria tp_categorialist = categorialist.get().getTp_categoria();

        if (tp_categorialist.equals(TipoCategoria.DESPESA)) {
            throw new Exception("Não é possivel ter despesas sem ter receitas no lançamento.");
        }

    }

    public Long excluir(Long id, String competencia, Long id_cliente) throws Exception {

        Optional<Lancamento> objeto = repository.findById(id);
        competenciaService.verificarStatusCompetencia(objeto.get().getDt_anomes(), id_cliente);

        Long id_clientetemp = objeto.get().getId_cliente();
        Long id_centrocustotemp = objeto.get().getId_centrocusto();
        LocalDate dt_lancamentotemp = objeto.get().getDt_lancamento();

        // movimentacaoRepository.deleteByIdLancamento(id);
        itemObjetoRepository.deleteByIdLancamento(id, competencia);
        repository.deleteById(id);

        movimentacaoCaixaService.gravarMovimentoCaixa(
                id_clientetemp,
                id_centrocustotemp,
                dt_lancamentotemp);

        return id;
    }

}
