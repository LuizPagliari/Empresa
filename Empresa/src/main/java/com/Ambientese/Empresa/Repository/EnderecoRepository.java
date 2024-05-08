    package com.Ambientese.Empresa.Repository;

    import com.Ambientese.Empresa.Model.Endereco;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    }