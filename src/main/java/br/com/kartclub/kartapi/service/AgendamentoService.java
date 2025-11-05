package br.com.kartclub.kartapi.service;

import br.com.kartclub.kartapi.dto.agendamento.AgendamentoRequestDTO;
import br.com.kartclub.kartapi.entity.*;
import br.com.kartclub.kartapi.entity.enums.StatusAgendamento;
import br.com.kartclub.kartapi.entity.enums.StatusKart;
import br.com.kartclub.kartapi.exception.RegraNegocioException;
import br.com.kartclub.kartapi.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private KartRepository kartRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AgendamentoKartsRepository agendamentoKartsRepository;

    @Autowired
    private PrecoRepository precoRepository;

    @Transactional
    public Agendamento criarAgendamento(AgendamentoRequestDTO agendamentodto, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado!"));

        LocalDateTime horarioSolicitado = agendamentodto.getDataHora();
        DayOfWeek diaDaSemana = horarioSolicitado.getDayOfWeek();
        LocalTime horaSolicitada = horarioSolicitado.toLocalTime();

        boolean horarioValido = false;

        switch (diaDaSemana) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                if(horaSolicitada.equals(LocalTime.of(19,30))){
                    horarioValido = true;
                }
                break;
            case SATURDAY:
                LocalTime inicioSabado = LocalTime.of(15,0);
                LocalTime fimSabado = LocalTime.of(17,0);
                if (!horaSolicitada.isBefore(inicioSabado) && !horaSolicitada.isAfter(fimSabado) && horaSolicitada.getMinute() == 0) {
                    horarioValido = true;
                }
                break;
            case SUNDAY:
                LocalTime inicioDomingo = LocalTime.of(9,0);
                LocalTime fimDomingo = LocalTime.of(11,0);
                if(!horaSolicitada.isBefore(inicioDomingo) && !horaSolicitada.isAfter(fimDomingo) && horaSolicitada.getMinute() == 0) {
                    horarioValido = true;
                }
                break;
            default:
                horarioValido = true;
        }
        if (!horarioValido) {
            throw new RegraNegocioException("Horário de agendamento inválido para o dia selecionado. Consulte os horários disponíveis.");
        }

        long kartsDisponiveis = kartRepository.countByStatus(StatusKart.DISPONIVEL);
        if (agendamentodto.getNumeroParticipantes() > kartsDisponiveis){
            throw new RegraNegocioException("Capacidade máxima excedida. Karts disponíveis: " +  kartsDisponiveis);
        }

        LocalDateTime inicioJanela = horarioSolicitado.minusMinutes(59);
        LocalDateTime terminoJanela = horarioSolicitado.plusMinutes(59);
        if (repository.existsAgendamentoNoIntervalo(inicioJanela, terminoJanela)){
          throw new RegraNegocioException("Este horário já esta ocupado ou muito próximo de outro agendamento.");
        }

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setUsuario(usuario);
        novoAgendamento.setDataHora(horarioSolicitado);
        novoAgendamento.setNumeroParticipantes(agendamentodto.getNumeroParticipantes());
        novoAgendamento.setStatusAgendamento(StatusAgendamento.PENDENTE);

        Preco precoAtivo = precoRepository.findFirstByAtivoTrue()
                .orElseThrow(() -> new RegraNegocioException("Nenhum preço ativo configurado no sistema."));
        BigDecimal valorTotal = precoAtivo.getValor().multiply(new BigDecimal(agendamentodto.getNumeroParticipantes()));
        novoAgendamento.setValorTotal(valorTotal);

        Agendamento agendamentoSalvo = repository.save(novoAgendamento);

        List<Kart> kartsParaAlocar = kartRepository.findAllByStatus(StatusKart.DISPONIVEL).subList(0, agendamentodto.getNumeroParticipantes());

        for (Kart kart : kartsParaAlocar) {
            AgendamentoKartsId agendamentoKartsId = new AgendamentoKartsId();
            agendamentoKartsId.setIdAgendamento(agendamentoSalvo.getIdAgendamento());
            agendamentoKartsId.setKartId(kart.getIdKart());

            AgendamentoKarts associacao =  new AgendamentoKarts();
            associacao.setId(agendamentoKartsId);
            associacao.setAgendamento(agendamentoSalvo);
            associacao.setKart(kart);

            agendamentoKartsRepository.save(associacao);

            // Marca o kart como reservado para evitar overbooking
            kart.setStatus(StatusKart.RESERVADO);
            kartRepository.save(kart);
        }
        return agendamentoSalvo;
    }

    @Transactional
    public List<Agendamento> buscarPorEmailUsuario(String emailUsuario) {
        return repository.findByUsuarioEmailOrderByDataHoraDesc(emailUsuario);
    }
}
