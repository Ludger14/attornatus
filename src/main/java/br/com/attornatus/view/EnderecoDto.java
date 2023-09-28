package br.com.attornatus.view;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude
public class EnderecoDto {
	Long id;
	String cep;
	String logradouro;
	String numero;
	String cidade;
}
