package br.com.attornatus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.attornatus.model.Endereco;
import br.com.attornatus.model.Pessoa;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	@Query(nativeQuery = true, value = "SELECT * FROM attornatus_endereco as atp \n" +
            " where atp.pessoa_id = :id ")
	Endereco checkAdressPessoa(@Param("id") Long id);
	
	List<Endereco> findByPessoaId(Long pessoaId);
}
