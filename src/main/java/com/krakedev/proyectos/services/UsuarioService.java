package com.krakedev.proyectos.services;

import com.krakedev.proyectos.dto.LoginDTO;
import com.krakedev.proyectos.dto.RegistroDTO;
import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrar(RegistroDTO dto) {

        Optional<Usuario> usuarioExiste =
                usuarioRepository.findByUsername(dto.getUsername());

        if(usuarioExiste.isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuario = new Usuario();

        usuario.setUsername(dto.getUsername());

        String passwordHash =
                BCrypt.hashpw(dto.getPassword(),
                BCrypt.gensalt());

        usuario.setPassword(passwordHash);

        usuario.setRol(dto.getRol());

        return usuarioRepository.save(usuario);
    }

    public boolean login(LoginDTO dto) {

        Optional<Usuario> usuario =
                usuarioRepository.findByUsername(dto.getUsername());

        if(usuario.isEmpty()) {
            return false;
        }

        return BCrypt.checkpw(
                dto.getPassword(),
                usuario.get().getPassword()
        );
    }
}