    package com.Ambientese.Empresa.EmpresaRepository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import com.Ambientese.Empresa.Model.Empresa;

    public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
        boolean existsByCnpj(String cnpj);
    }
