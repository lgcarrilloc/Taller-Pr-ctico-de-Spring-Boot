package com.krakedev.proyectos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Empleado empleado) {

        try {

            return new ResponseEntity<>(
                    empleadoService.guardar(empleado),
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
                    empleadoService.listarTodos(),
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Integer id) {

        try {

            Empleado empleado =
                    empleadoService.buscarPorId(id);

            if (empleado == null) {
                return new ResponseEntity<>(
                        "Empleado no encontrado",
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(
                    empleado,
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody Empleado empleado) {

        try {

            Empleado existente =
                    empleadoService.buscarPorId(id);

            if (existente == null) {
                return new ResponseEntity<>(
                        "Empleado no encontrado",
                        HttpStatus.NOT_FOUND);
            }

            empleado.setId(id);

            return new ResponseEntity<>(
                    empleadoService.guardar(empleado),
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Integer id) {

        try {

            empleadoService.eliminar(id);

            return new ResponseEntity<>(
                    "Empleado eliminado",
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}