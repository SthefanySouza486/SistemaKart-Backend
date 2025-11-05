package br.com.kartclub.kartapi.controller;

import br.com.kartclub.kartapi.dto.usuario.UsuarioRequestDTO;
import br.com.kartclub.kartapi.dto.usuario.UsuarioResponseDTO;
import br.com.kartclub.kartapi.entity.Usuario;
import br.com.kartclub.kartapi.service.UsuarioService;
import br.com.kartclub.kartapi.service.GestorService;
import br.com.kartclub.kartapi.dto.gestor.GestorRequestDTO;
import br.com.kartclub.kartapi.dto.gestor.GestorResponseDTO;
import br.com.kartclub.kartapi.entity.Gestor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private GestorService gestorService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto){
        // Verifica se o tipoUsuario foi enviado e é GESTOR
        if (dto.getTipoUsuario() != null && "GESTOR".equalsIgnoreCase(dto.getTipoUsuario())) {
            // Cria um GestorRequestDTO a partir do UsuarioRequestDTO
            GestorRequestDTO gestorDto = new GestorRequestDTO();
            gestorDto.setNome(dto.getNome());
            gestorDto.setEmail(dto.getEmail());
            gestorDto.setSenha(dto.getSenha());
            
            Gestor novoGestor = gestorService.cadastrarGestor(gestorDto);
            // Retorna uma resposta adequada para gestor
            // Você pode criar um método mapToGestorResponseDto ou retornar um DTO genérico
            return new ResponseEntity<>(mapToGestorResponseDto(novoGestor), HttpStatus.CREATED);
        }
        
        // Se for CLIENTE, usa o fluxo normal
        Usuario novoUsuario = service.cadastrarUsuario(dto);
        return new ResponseEntity<>(mapToUsuarioResponseDto(novoUsuario), HttpStatus.CREATED);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioRequestDTO dto){
        Usuario usuarioAtualizado = service.atualizarUsuario(idUsuario, dto);
        return ResponseEntity.ok(mapToUsuarioResponseDto(usuarioAtualizado));
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

    private GestorResponseDTO mapToGestorResponseDto(Gestor gestor) {
        GestorResponseDTO dto = new GestorResponseDTO();
        dto.setIdGestor(gestor.getIdGestor());
        dto.setNome(gestor.getNome());
        dto.setEmail(gestor.getEmail());
        dto.setTipoUsuario(gestor.getTipoUsuario());
        return dto;
    }
}
