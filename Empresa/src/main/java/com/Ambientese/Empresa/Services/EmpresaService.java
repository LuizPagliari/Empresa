package com.Ambientese.Empresa.Services;

import com.Ambientese.Empresa.DTO.EmpresaRequest;
import com.Ambientese.Empresa.Repository.EmpresaRepository;
import com.Ambientese.Empresa.Exception.ValidacaoException;
import com.Ambientese.Empresa.Model.Empresa;
import com.Ambientese.Empresa.Model.Endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    private boolean isValidCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return false;
        }
        int[] digitos = new int[cnpj.length()];
        for (int i = 0; i < cnpj.length(); i++) {
            digitos[i] = Character.getNumericValue(cnpj.charAt(i));
        }
        int soma = 0;
        soma += digitos[0] * 5;
        soma += digitos[1] * 4;
        soma += digitos[2] * 3;
        soma += digitos[3] * 2;
        soma += digitos[4] * 9;
        soma += digitos[5] * 8;
        soma += digitos[6] * 7;
        soma += digitos[7] * 6;
        soma += digitos[8] * 5;
        soma += digitos[9] * 4;
        soma += digitos[10] * 3;
        soma += digitos[11] * 2;
        int resto = soma % 11;
        int dv1 = resto < 2 ? 0 : 11 - resto;
        if (digitos[12] != dv1) {
            return false;
        }
        soma = 0;
        soma += digitos[0] * 6;
        soma += digitos[1] * 5;
        soma += digitos[2] * 4;
        soma += digitos[3] * 3;
        soma += digitos[4] * 2;
        soma += digitos[5] * 9;
        soma += digitos[6] * 8;
        soma += digitos[7] * 7;
        soma += digitos[8] * 6;
        soma += digitos[9] * 5;
        soma += digitos[10] * 4;
        soma += digitos[11] * 3;
        soma += digitos[12] * 2;
        resto = soma % 11;
        int dv2 = resto < 2 ? 0 : 11 - resto;
        return digitos[13] == dv2;    }

    private boolean isValidTelefone(String telefone) {
        return telefone != null && telefone.matches("\\d{10,11}");
    }

    private boolean empresaJaExiste(String cnpj) {
        return empresaRepository.existsByCnpj(cnpj);
    }

    private boolean isValidCep(String cep) {
        String cepRegex = "\\d{8}";
        return cep != null && cep.matches(cepRegex);    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);    }

    private void validarCamposObrigatorios(EmpresaRequest empresaRequest) {
        if (empresaRequest.getNomeFantasia() == null || empresaRequest.getNomeFantasia().isEmpty()) {
            throw new ValidacaoException("O nome fantasia não pode estar em branco");
        }
        if (empresaRequest.getNomeSolicitante() == null || empresaRequest.getNomeSolicitante().isEmpty()) {
            throw new ValidacaoException("O nome do solicitante não pode estar em branco");
        }
        if (empresaRequest.getTelefoneSolicitante() == null || empresaRequest.getTelefoneSolicitante().isEmpty()) {
            throw new ValidacaoException("O telefone do solicitante não pode estar em branco");
        }
        if (empresaRequest.getRazaoSocial() == null || empresaRequest.getRazaoSocial().isEmpty()) {
            throw new ValidacaoException("A razão social não pode estar em branco");
        }
        if (empresaRequest.getCnpj() == null || empresaRequest.getCnpj().isEmpty()) {
            throw new ValidacaoException("O CNPJ não pode estar em branco");
        }
        if (!isValidCnpj(empresaRequest.getCnpj())) {
            throw new ValidacaoException("O CNPJ inserido não é válido");
        }
        if (empresaJaExiste(empresaRequest.getCnpj())) {
            throw new ValidacaoException("Já existe uma empresa cadastrada com este CNPJ");
        }
        if (empresaRequest.getTelefoneEmpresas() == null || empresaRequest.getTelefoneEmpresas().isEmpty()) {
            throw new ValidacaoException("O telefone da empresa não pode estar em branco");
        }
        if (empresaRequest.getRamo() == null || empresaRequest.getRamo().isEmpty()) {
            throw new ValidacaoException("O ramo não pode estar em branco");
        }
        if (empresaRequest.getPorteEmpresas() == null) {
            throw new ValidacaoException("O porte da empresa não pode estar em branco");
        }
        Endereco endereco = empresaRequest.getEndereco();
        if (endereco == null) {
            throw new ValidacaoException("É necessário associar um endereço à empresa");
        } else {
            if (endereco.getUF() == null || endereco.getUF().isEmpty()) {
                throw new ValidacaoException("O campo UF do endereço é obrigatório e não pode estar em branco");
            }
        }
    }

    public Empresa criarEmpresa(EmpresaRequest empresaRequest) {
        validarCamposObrigatorios(empresaRequest);
        Empresa empresa = new Empresa();
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
        return empresaRepository.save(empresa);
    }

    public Empresa atualizarEmpresa(Long id, EmpresaRequest empresaRequest) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Empresa não encontrada com o ID: " + id));
        validarCamposObrigatorios(empresaRequest);
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
        return empresaRepository.save(empresa);
    }
}
