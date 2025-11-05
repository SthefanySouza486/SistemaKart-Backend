package br.com.kartclub.kartapi.service;

import br.com.kartclub.kartapi.entity.Agendamento;
import br.com.kartclub.kartapi.entity.Pagamento;
import br.com.kartclub.kartapi.entity.enums.MetodoPagamento;
import br.com.kartclub.kartapi.entity.enums.StatusAgendamento;
import br.com.kartclub.kartapi.entity.enums.StatusPagamento;
import br.com.kartclub.kartapi.exception.RegraNegocioException;
import br.com.kartclub.kartapi.repository.AgendamentoRepository;
import br.com.kartclub.kartapi.repository.PagamentoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Transactional
    public Pagamento processarPagamento(Long idAgendamento, MetodoPagamento metodoPagamento) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RegraNegocioException("Agendamento com ID " + idAgendamento + " não encontrado!"));

        if (agendamento.getStatusAgendamento() != StatusAgendamento.PENDENTE) {
            throw new RegraNegocioException("Este agendamento não pode ser pago pois seu status é " + agendamento.getStatusAgendamento());
        }

        Pagamento novoPagamento = new Pagamento();
        novoPagamento.setAgendamento(agendamento);
        novoPagamento.setValor(agendamento.getValorTotal());
        novoPagamento.setMetodoPagamento(metodoPagamento);
        novoPagamento.setDataPagamento(LocalDateTime.now());

        //simulação
        boolean pagamentoAprovado = true; //Implementar GATEWAY DE PAGAMENTO

        if (pagamentoAprovado){
            novoPagamento.setStatusPagamento(StatusPagamento.APROVADO);
            agendamento.setStatusAgendamento(StatusAgendamento.CONFIRMADO);
        } else {
            novoPagamento.setStatusPagamento(StatusPagamento.RECUSADO);
        }
        agendamentoRepository.save(agendamento);
        return repository.save(novoPagamento);
    }
}
