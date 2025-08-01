package com.api_aventesfinance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api_aventesfinance.enums.StatusEmprestimo;
import com.api_aventesfinance.model.Emprestimo;
import com.api_aventesfinance.model.ItemEmprestimo;
import com.api_aventesfinance.model.ItemLancamento;
import com.api_aventesfinance.model.Lancamento;
import com.api_aventesfinance.repository.EmprestimoRepository;
import com.api_aventesfinance.repository.ItemEmprestimoRepository;
import com.api_aventesfinance.repository.ItemLancamentoRepository;
import com.api_aventesfinance.utils.DataUtils;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private ItemEmprestimoRepository itemRepository;

    @Autowired
    private CompetenciaService competenciaService;

    @Transactional(rollbackFor = Exception.class)
    public Emprestimo salvarItens(Emprestimo objeto, Long id_cliente) throws Exception {

        objeto.setVl_total(0.0);
        Double valor_total = 0.0;
        objeto.setDt_anomes(DataUtils.formatarAnoMes(objeto.getDt_emprestimo()));
        objeto.setId_cliente(id_cliente);

        List<ItemEmprestimo> itens = objeto.getItens();
        objeto.setItens(null);

        validarObjeto(objeto);
        objeto = repository.save(objeto);

        removerItens(objeto, itens);

        if (itens != null && itens.size() > 0) {
            for (ItemEmprestimo item : itens) {
                item.setId_emprestimo(objeto.getId_emprestimo());

                if (item.getId_itememprestimo() == null || item.getId_itememprestimo() == 0) {
                    item.setId_itememprestimo(null);
                }

                validacaoItem(item, itens, objeto.getId_emprestimo());
                item = itemRepository.save(item);
                valor_total += item.getVl_emprestimo();
            }
        }

        validacaoGeral(itens, objeto, valor_total);

        objeto.setVl_total(valor_total);
        objeto.setItens(itens);
        objeto = repository.save(objeto);

        return objeto;
    }

    public void validacaoGeral(List<ItemEmprestimo> itens, Emprestimo objeto, Double valor_total) throws Exception {
        validarValorTotalItem(itens, valor_total);
        validarStatus(itens, objeto);

    }

    public void validarStatus(List<ItemEmprestimo> itens, Emprestimo objeto) throws Exception {

        boolean todosQuitados = itens.stream().allMatch(i -> i.getTp_itemstatus() == StatusEmprestimo.QUITADO);

        if (objeto.getId_emprestimo() != null) {
            StatusEmprestimo statusNovo = todosQuitados ? StatusEmprestimo.QUITADO : StatusEmprestimo.PENDENTE;
            objeto.setTp_status(statusNovo);
            repository.save(objeto);
        }

    }

    public void validarValorTotalItem(List<ItemEmprestimo> listaItens, Double valor) throws Exception {
        Double vl_total = 0.0;

        for (ItemEmprestimo item : listaItens) {
            vl_total += item.getVl_emprestimo();
        }

        if (vl_total == null || vl_total == 0.0) {
            throw new Exception("Valor do lançamento não pode ser nulo ou zero");
        }
        final double EPSILON = 0.00001;

        if (Math.abs(vl_total - valor) > EPSILON) {
            throw new Exception("Valor total dos itens não corresponde ao valor do lançamento");
        }
    }

    public void removerItens(Emprestimo objeto, List<ItemEmprestimo> itensAtualizados) {
        List<ItemEmprestimo> itensPersistidos = itemRepository.findByIdMestre(objeto.getId_emprestimo());

        for (ItemEmprestimo itemPersistido : itensPersistidos) {
            boolean aindaExiste = itensAtualizados.stream()
                    .anyMatch(i -> i.getId_itememprestimo() != null
                            && i.getId_itememprestimo().equals(itemPersistido.getId_itememprestimo()));

            if (!aindaExiste) {
                if (itemPersistido.getId_itememprestimo() != null && itemPersistido.getId_itememprestimo() > 0) {
                    itemRepository.deleteById(itemPersistido.getId_itememprestimo());
                }
            }
        }
    }

    public void validarObjeto(Emprestimo objeto ) throws Exception {

        validarSequencia(objeto.getId_emprestimo(), objeto.getCd_emprestimo(), objeto.getDt_anomes(), objeto.getId_cliente());
        competenciaService.verificarStatusCompetencia(objeto.getDt_anomes(), objeto.getId_cliente());

    }

    public void validacaoItem(ItemEmprestimo item, List<ItemEmprestimo> listaItens, Long id)
            throws Exception {

        validarCodigoSequencialItem(item, listaItens, id);

    }

    public void validarCodigoSequencialItem(ItemEmprestimo item, List<ItemEmprestimo> listaItens, Long id)
            throws Exception {

        if (id == null) {
            String codigo = item.getCd_itememprestimo();
            Boolean fl_existe = itemRepository.obterSequencialExistente(codigo, id);

            if (fl_existe != null && fl_existe) {
                throw new Exception("Codigo sequencial do item está repetindo.");
            }
        }

    }

    public void excluir(Long id) throws Exception {

        Optional<Emprestimo> objeto = repository.findById(id);
        competenciaService.verificarStatusCompetencia(objeto.get().getDt_anomes(), objeto.get().getId_cliente());

        itemRepository.deleteByIdMestre(id);
        repository.deleteById(id);

    }

    public void validarSequencia(Long id, String codigo, String competencia, Long id_cliente) throws Exception {

        if (id != null)
            return;

        Boolean fl_existe = repository.obterSequencialExistente(codigo,competencia, id_cliente);

        if (fl_existe != null && fl_existe) {
            throw new Exception("Codigo sequencial ja existente.");
        }
    }

}
