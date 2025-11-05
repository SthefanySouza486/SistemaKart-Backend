package br.com.kartclub.kartapi.controller;

import br.com.kartclub.kartapi.dto.agendamento.AgendamentoRequestDTO;
import br.com.kartclub.kartapi.dto.agendamento.AgendamentoResponseDTO;
import br.com.kartclub.kartapi.dto.usuario.UsuarioResponseDTO;
import br.com.kartclub.kartapi.entity.Agendamento;
import br.com.kartclub.kartapi.entity.Usuario;
import br.com.kartclub.kartapi.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@Valid @RequestBody AgendamentoRequestDTO dto,
                                                                   Authentication authentication) {

        User userDetails = (User) authentication.getPrincipal();
        String emailDoUsuarioLogado = userDetails.getUsername();
        Agendamento novoAgendamento = service.criarAgendamento(dto, emailDoUsuarioLogado);
        return new ResponseEntity<>(mapToAgendamentoResponseDto(novoAgendamento), HttpStatus.CREATED);
    }

    private AgendamentoResponseDTO mapToAgendamentoResponseDto(Agendamento agendamento){
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setIdAgendamento(agendamento.getIdAgendamento());
        dto.setDataHora(agendamento.getDataHora());
        dto.setStatus(agendamento.getStatusAgendamento());
        dto.setValorTotal(agendamento.getValorTotal());
        dto.setNumeroParticipantes(agendamento.getNumeroParticipantes());
        dto.setUsuario(mapToUsuarioResponseDto(agendamento.getUsuario()));
        return dto;
    }

    private UsuarioResponseDTO mapToUsuarioResponseDto(Usuario usuario){
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        return dto;
    }

    @GetMapping("/meus")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarMeusAgendamentos(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        String emailDoUsuarioLogado = userDetails.getUsername();

        List<Agendamento> agendamentos = service.buscarPorEmailUsuario(emailDoUsuarioLogado);

        List<AgendamentoResponseDTO> dtos = agendamentos.stream()
                .map(this::mapToAgendamentoResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
