package br.com.attornatus.view;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude
public class PessoaDto {
	Long id;
	String nome;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    String dtNascimento;
	
	@JsonProperty("endereco")
	List<EnderecoDto> endereco;
	
	String message;
	Boolean success;
}
