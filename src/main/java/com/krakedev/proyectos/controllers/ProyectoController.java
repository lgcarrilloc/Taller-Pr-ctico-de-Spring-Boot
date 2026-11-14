package com.krakedev.proyectos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Proyecto proyecto) {
        try {
            return new ResponseEntity<>(
                    proyectoService.guardar(proyecto),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return new ResponseEntity<>(
                    proyectoService.listarTodos(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Proyecto proyecto = proyectoService.buscarPorId(id);

            if (proyecto == null) {
                return new ResponseEntity<>(
                        "Proyecto no encontrado",
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(proyecto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody Proyecto proyecto) {

        try {

            Proyecto existente =
                    proyectoService.buscarPorId(id);

            if (existente == null) {
                return new ResponseEntity<>(
                        "Proyecto no encontrado",
                        HttpStatus.NOT_FOUND);
            }

            proyecto.setId(id);

            return new ResponseEntity<>(
                    proyectoService.guardar(proyecto),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        try {

            proyectoService.eliminar(id);

            return new ResponseEntity<>(
                    "Proyecto eliminado",
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}