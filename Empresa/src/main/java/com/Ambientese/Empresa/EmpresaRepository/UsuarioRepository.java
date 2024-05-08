package com.Ambientese.Empresa.EmpresaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Ambientese.Empresa.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
