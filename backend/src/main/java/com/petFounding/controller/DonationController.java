package com.petFounding.controller;

import com.petFounding.entity.Donation;
import com.petFounding.entity.Shelter;
import com.petFounding.entity.User;
import com.petFounding.exception.UsuarioInexistenteException;
import com.petFounding.interfacee.DonationService;
import com.petFounding.interfacee.ShelterService;
import com.petFounding.interfacee.UserService;
import com.petFounding.valid.DonationValid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShelterService shelterService;

    @PostMapping
    public ResponseEntity<Donation> procesarDonacion(@RequestBody @Valid DonationValid datos, UriComponentsBuilder uriBuilder) throws UsuarioInexistenteException {

        User usuario = userService.obtenerUsuarioPorId(datos.idUsuario());
        Shelter refugio = shelterService.obtenerRefugioPorId(datos.idRefugio());

        Donation donacion = new Donation(datos.monto(), null, datos.metodoPago(), datos.comentario());
        donacion.setUsuario(usuario);
        donacion.setRefugio(refugio);

        Donation donacionProcesada = donationService.processar(donacion);

        URI uri = uriBuilder.path("/donations/{id}").buildAndExpand(donacionProcesada.getId()).toUri();
        return ResponseEntity.created(uri).body(donacionProcesada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> obtenerRecibo(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.generarRecibo(id));
    }

    @GetMapping("/total/shelter/{id}")
    public ResponseEntity<BigDecimal> obtenerTotalRecaudado(@PathVariable Long id) {
        Shelter refugio = shelterService.obtenerRefugioPorId(id);
        BigDecimal total = donationService.calcularTotalRecaudado(refugio);
        return ResponseEntity.ok(total);
    }
}