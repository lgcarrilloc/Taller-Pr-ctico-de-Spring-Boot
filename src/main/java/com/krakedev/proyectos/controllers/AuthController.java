package com.krakedev.proyectos.controllers;

import com.krakedev.proyectos.dto.LoginDTO;
import com.krakedev.proyectos.dto.RegistroDTO;
import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(
            @RequestBody RegistroDTO dto){

        Usuario usuario =
                usuarioService.registrar(dto);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDTO dto){

        boolean resultado =
                usuarioService.login(dto);

        if(resultado){
            return ResponseEntity.ok("Login exitoso");
        }

        return ResponseEntity
                .badRequest()
                .body("Credenciales incorrectas");
    }
}