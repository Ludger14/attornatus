package br.com.attornatus.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.attornatus.model.Pessoa;
import br.com.attornatus.services.PessoaService;
import br.com.attornatus.view.EnderecoDto;
import br.com.attornatus.view.PessoaDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost" }, maxAge = 3600)
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@PostMapping("/createPessoa")
	@Transactional(rollbackFor = Exception.class)
	public PessoaDto createPessoa(@RequestBody PessoaDto pessoa, HttpServletRequest request) throws ParseException{
			PessoaDto newPessoa = pessoaService.createPessoa(pessoa);
			return newPessoa;
	}	
	
	@PutMapping("/editPessoa/{id}")
	@Transactional(rollbackFor = Exception.class)
	public PessoaDto editPessoa(@PathVariable Long id, @RequestBody PessoaDto pessoa, HttpServletRequest request) throws ParseException{
			PessoaDto newPessoa = pessoaService.editPessoa(id, pessoa);
			return newPessoa;
	}
	
	@GetMapping("/consultarPessoa/{id}")
	public PessoaDto consultarPessoa(@PathVariable Long id, HttpServletRequest request) throws ParseException{
		PessoaDto newPessoa = pessoaService.consultarPessoa(id);
		return newPessoa;
	}
	
	@GetMapping("/listarPessoa")
	public List<PessoaDto> listarPessoa(HttpServletRequest request) throws ParseException{
		List<PessoaDto> newPessoa = pessoaService.listarPessoas();
		return newPessoa;
	}
	
	@PostMapping("/{id}/createEndereco")
	@Transactional(rollbackFor = Exception.class)
	public EnderecoDto createEndereco(@PathVariable Long id, @RequestBody EnderecoDto enderecoDto, HttpServletRequest request) throws ParseException{
			EnderecoDto newPessoa = pessoaService.createEndereco(id, enderecoDto);
			return newPessoa;
	}
	
	@GetMapping("/{id}/listarEndereco")
	public List<EnderecoDto> listarEndereco(@PathVariable Long id, HttpServletRequest request) throws ParseException{
		List<EnderecoDto> newPessoa = pessoaService.listarAddressPessoa(id);
		return newPessoa;
	}
	
	@PutMapping("/{id}/principalAddress")
	@Transactional(rollbackFor = Exception.class)
	public PessoaDto principalAddress(@PathVariable Long id, @RequestParam String cep, HttpServletRequest request) throws ParseException {
	    PessoaDto newPessoa = pessoaService.principalAddress(id, cep);
	    return newPessoa;
	}
}
