package com.Ambientese.Empresa.Controller;

import com.Ambientese.Empresa.DTO.EmpresaRequest;
import com.Ambientese.Empresa.Model.Empresa;
import com.Ambientese.Empresa.EmpresaRepository.EmpresaRepository;
import com.Ambientese.Empresa.Services.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Empresa")
@Validated
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ValidacaoService validacaoService;

    @GetMapping
    public List<Empresa> listarEmpresa() {
        return empresaRepository.findAll();
    }

    @PostMapping
    public Empresa criarEmpresa(@Valid @RequestBody EmpresaRequest empresaRequest) {
        return validacaoService.criarEmpresa(empresaRequest);
    }

    @PutMapping("/{id}")
    public Empresa atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaRequest empresaRequest) {
        return validacaoService.atualizarEmpresa(id, empresaRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
        return empresaRepository.findById(id).map(empresa -> {
            empresaRepository.delete(empresa);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
