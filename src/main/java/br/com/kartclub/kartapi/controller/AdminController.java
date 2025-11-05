package br.com.kartclub.kartapi.controller;

import br.com.kartclub.kartapi.dto.gestor.GestorRequestDTO;
import br.com.kartclub.kartapi.dto.gestor.GestorResponseDTO;
import br.com.kartclub.kartapi.dto.kart.KartResponseDTO;
import br.com.kartclub.kartapi.entity.Gestor;
import br.com.kartclub.kartapi.entity.Kart;
import br.com.kartclub.kartapi.entity.enums.StatusKart;
import br.com.kartclub.kartapi.service.GestorService;
import br.com.kartclub.kartapi.service.KartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('GESTOR')")
public class AdminController {

    @Autowired
    private KartService kartService;

    @Autowired
    private GestorService gestorService;

    @GetMapping("/karts")
    public ResponseEntity<List<KartResponseDTO>> listarKarts() {
        List<Kart> karts = kartService.buscarTodos();
        List<KartResponseDTO> dtos = karts.stream()
                                          .map(this::mapToKartResponseDto)
                                          .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/karts")
    public ResponseEntity<KartResponseDTO> cadastrarKart(@RequestParam Integer numero){
        Kart novoKart = kartService.cadastrarKart(numero);
        return new ResponseEntity<>(mapToKartResponseDto(novoKart), HttpStatus.CREATED);
    }

    @PutMapping("/karts/{id}/status")
    public ResponseEntity<KartResponseDTO> atualizarStatusKart(@PathVariable Long id, @RequestParam StatusKart novoStatus){
        Kart kartAtualizado = kartService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(mapToKartResponseDto(kartAtualizado));
    }

    private KartResponseDTO mapToKartResponseDto(Kart kart){
        KartResponseDTO dto = new KartResponseDTO();
        dto.setIdKart(kart.getIdKart());
        dto.setNumero(kart.getNumero());
        dto.setStatusKart(kart.getStatus());
        return dto;
    }

    private GestorResponseDTO mapToGestorResponseDto(Gestor gestor){
        GestorResponseDTO dto = new GestorResponseDTO();
        dto.setIdGestor(gestor.getIdGestor());
        dto.setNome(gestor.getNome());
        dto.setEmail(gestor.getEmail());
        dto.setTipoUsuario(gestor.getTipoUsuario());
        return dto;
    }

    @PostMapping("/gestores")
    public ResponseEntity<GestorResponseDTO> cadastrarGestor(@Valid @RequestBody GestorRequestDTO dto){
        Gestor novoGestor = gestorService.cadastrarGestor(dto);
        return new ResponseEntity<>(mapToGestorResponseDto(novoGestor), HttpStatus.CREATED);
    }

    @PutMapping("/gestores/{id}")
    public ResponseEntity<GestorResponseDTO> atualizarGestor(@PathVariable Long id,
                                                             @Valid @RequestBody GestorRequestDTO dto){
        Gestor gestorAtualizado = gestorService.atualizarGestor(id, dto);
        return ResponseEntity.ok(mapToGestorResponseDto(gestorAtualizado));
    }

    @GetMapping("/gestores/{id}")
    public ResponseEntity<GestorResponseDTO> buscarGestorPorId(@PathVariable Long id){
        Gestor gestor = gestorService.buscarPorId(id);
        return ResponseEntity.ok(mapToGestorResponseDto(gestor));
    }
}
