package br.com.kartclub.kartapi.controller;

import br.com.kartclub.kartapi.dto.pagamento.PagamentoRequestDTO;
import br.com.kartclub.kartapi.dto.pagamento.PagamentoResponseDTO;
import br.com.kartclub.kartapi.entity.Pagamento;
import br.com.kartclub.kartapi.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping("/{idAgendamento}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<PagamentoResponseDTO> processarPagamento(@PathVariable Long idAgendamento,
                                                                   @Valid @RequestBody PagamentoRequestDTO dto) {
        Pagamento pagamentoProcessado = service.processarPagamento(idAgendamento, dto.getMetodoPagamento());
        PagamentoResponseDTO responseDTO = mapToPagamentoResponseDTO(pagamentoProcessado);
        return ResponseEntity.ok(responseDTO);
    }

    private  PagamentoResponseDTO mapToPagamentoResponseDTO(Pagamento pagamento) {
        PagamentoResponseDTO dto = new PagamentoResponseDTO();
        dto.setIdPagamento(pagamento.getIdPagamento());
        if(pagamento.getAgendamento() != null){
            dto.setIdAgendamento(pagamento.getAgendamento().getIdAgendamento());
        }
        dto.setValor(pagamento.getValor());
        dto.setMetodoPagamento(pagamento.getMetodoPagamento());
        dto.setStatusPagamento(pagamento.getStatusPagamento());
        dto.setDataPagamento(pagamento.getDataPagamento());
        return dto;
    }
}
