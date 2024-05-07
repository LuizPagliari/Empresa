    package com.Ambientese.Empresa.EmpresaRepository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import com.Ambientese.Empresa.Model.Empresas;

    public interface EmpresaRepository extends JpaRepository<Empresas, Long> {
        boolean existsByCnpj(String cnpj);
    }
