    package com.Ambientese.Empresa.Controller;

    import com.Ambientese.Empresa.DTO.EmpresaRequest;
    import com.Ambientese.Empresa.Exception.ValidacaoException;
    import com.Ambientese.Empresa.Model.Empresas;
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
        public List<Empresas> listarEmpresa() {
            return empresaRepository.findAll();
        }

        @PostMapping
        public Empresas criarEmpresa(@Valid @RequestBody EmpresaRequest empresaRequest) {
            validacaoService.validarCamposObrigatorios(empresaRequest);

            // Convertendo EmpresaRequest para Empresas e salvando no banco de dados
            Empresas empresa = converteEmpresaRequestParaEmpresas(empresaRequest);
            return empresaRepository.save(empresa);
        }

        @PutMapping("/{id}")
        public Empresas atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaRequest empresaRequest) {
            validacaoService.validarCamposObrigatorios(empresaRequest);

            // Convertendo EmpresaRequest para Empresas
            Empresas empresa = empresaRepository.findById(id)
                    .orElseThrow(() -> new ValidacaoException("Empresa não encontrada com o ID: " + id));
            atualizarDadosEmpresa(empresa, empresaRequest);

            return empresaRepository.save(empresa);
        }

        // Método para converter EmpresaRequest para Empresas
        private Empresas converteEmpresaRequestParaEmpresas(EmpresaRequest empresaRequest) {
            Empresas empresa = new Empresas();
            empresa.setNomeFantasia(empresaRequest.getNomeFantasia());
            empresa.setNomeSolicitante(empresaRequest.getNomeSolicitante());
            empresa.setTelefoneSolicitante(empresaRequest.getTelefoneSolicitante());
            empresa.setRazaoSocial(empresaRequest.getRazaoSocial());
            empresa.setCnpj(empresaRequest.getCnpj());
            empresa.setInscricaoSocial(empresaRequest.getInscricaoSocial());
            empresa.setEmail(empresaRequest.getEmail());
            empresa.setTelefoneEmpresas(empresaRequest.getTelefoneEmpresas());
            empresa.setRamo(empresaRequest.getRamo());
            empresa.setPorteEmpresas(empresaRequest.getPorteEmpresas());
            empresa.setEndereco(empresaRequest.getEndereco());
            return empresa;
        }

        // Método para atualizar os dados da empresa com base no EmpresaRequest
        private void atualizarDadosEmpresa(Empresas empresa, EmpresaRequest empresaRequest) {
            empresa.setNomeFantasia(empresaRequest.getNomeFantasia());
            empresa.setNomeSolicitante(empresaRequest.getNomeSolicitante());
            empresa.setTelefoneSolicitante(empresaRequest.getTelefoneSolicitante());
            empresa.setRazaoSocial(empresaRequest.getRazaoSocial());
            empresa.setCnpj(empresaRequest.getCnpj());
            empresa.setInscricaoSocial(empresaRequest.getInscricaoSocial());
            empresa.setEmail(empresaRequest.getEmail());
            empresa.setTelefoneEmpresas(empresaRequest.getTelefoneEmpresas());
            empresa.setRamo(empresaRequest.getRamo());
            empresa.setPorteEmpresas(empresaRequest.getPorteEmpresas());
            empresa.setEndereco(empresaRequest.getEndereco());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
            return empresaRepository.findById(id).map(empresa -> {
                empresaRepository.delete(empresa);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
        }
    }
