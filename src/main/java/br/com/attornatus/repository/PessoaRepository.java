package br.com.attornatus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.attornatus.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	@Query(nativeQuery = true, value = "SELECT * FROM attornatus_pessoa as atp \n" +
            " where atp.id = :id ")
    Pessoa checkIdPessoa(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "SELECT atp.id, atp.txt_nome, atp.dt_nascimento, ate.txt_cep, \n" +
			" ate.txt_logradouro, ate.txt_numero, ate.txt_cidade FROM attornatus_pessoa as atp \n" +
		    "LEFT JOIN attornatus_endereco as ate ON atp.id = ate.pessoa_id " +
		    "WHERE atp.id = :id")
	Pessoa consultarPessoa(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "SELECT atp.id, atp.txt_nome, atp.dt_nascimento, ate.txt_cep, " +
		    "ate.txt_logradouro, ate.txt_numero, ate.txt_cidade FROM attornatus_pessoa as atp " +
		    "LEFT JOIN attornatus_endereco as ate ON atp.id = ate.pessoa_id")
	List<Pessoa> listarPessoas();
}
