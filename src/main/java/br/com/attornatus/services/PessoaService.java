package br.com.attornatus.services;

import java.util.Objects;
import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatus.model.Pessoa;
import br.com.attornatus.model.Endereco;
import br.com.attornatus.repository.EnderecoRepository;
import br.com.attornatus.repository.PessoaRepository;
import br.com.attornatus.view.PessoaDto;
import br.com.attornatus.view.EnderecoDto;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public PessoaDto createPessoa(PessoaDto pessoa) throws ParseException {	
		PessoaDto pessoaDto = new PessoaDto();
		if(Objects.nonNull(pessoa)) {
			Pessoa newPessoa = new Pessoa();
			newPessoa.setNome(pessoa.getNome());			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dtNascimento = dateFormat.parse(pessoa.getDtNascimento());
            newPessoa.setDtNascimento(dtNascimento);
            
            newPessoa = pessoaRepository.save(newPessoa);
            
            if(pessoa.getEndereco() != null && !pessoa.getEndereco().isEmpty()) {
            	List<Endereco> enderecos = new ArrayList<>();
            	
            	for(EnderecoDto enderecoDto: pessoa.getEndereco()) {
	            	Endereco endereco = new Endereco();
	                endereco.setCep(enderecoDto.getCep());
	                endereco.setLogradouro(enderecoDto.getLogradouro());
	                endereco.setNumero(enderecoDto.getNumero());
	                endereco.setCidade(enderecoDto.getCidade());
	                endereco.setPessoa(newPessoa);
	                
	    			enderecos.add(endereco);
            	}
            	enderecoRepository.saveAll(enderecos);
            }
            
			pessoaDto.setMessage("O usuário foi cadastrado com sucesso.");
			pessoaDto.setSuccess(Boolean.TRUE);
			
			return pessoaDto;
		}else {
			pessoaDto.setMessage("O usuário não foi cadastrado.");
			pessoaDto.setSuccess(Boolean.FALSE);
			
			return pessoaDto;
		}
		
	}
	
	public PessoaDto editPessoa(Long id, PessoaDto pessoa) throws ParseException {	
	    PessoaDto pessoaDto = new PessoaDto();
	    Pessoa person = pessoaRepository.consultarPessoa(id);

	    if (Objects.nonNull(person)) {
	        person.setNome(pessoa.getNome());
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date dtNascimento = dateFormat.parse(pessoa.getDtNascimento());
	        person.setDtNascimento(dtNascimento);

	        if (pessoa.getEndereco() != null && !pessoa.getEndereco().isEmpty()) {
	            List<Endereco> enderecos = new ArrayList<>();

	            for (EnderecoDto enderecoDto : pessoa.getEndereco()) {
	                boolean existEndereco = false;
	                for (Endereco endereco : person.getEnderecos()) {
	                    if (endereco.getCep().equals(enderecoDto.getCep())) {
	                        if (endereco.getNumero().equals(enderecoDto.getNumero())) {
	                        	existEndereco = true;
	                            break;
	                        } else {
	                            endereco.setCep(enderecoDto.getCep());
	                            endereco.setLogradouro(enderecoDto.getLogradouro());
	                            endereco.setNumero(enderecoDto.getNumero());
	                            endereco.setCidade(enderecoDto.getCidade());
	                            existEndereco = true;
	                            break;
	                        }
	                    }
	                }

	                if (!existEndereco) {
	                    Endereco novoEndereco = new Endereco();
	                    novoEndereco.setCep(enderecoDto.getCep());
	                    novoEndereco.setLogradouro(enderecoDto.getLogradouro());
	                    novoEndereco.setNumero(enderecoDto.getNumero());
	                    novoEndereco.setCidade(enderecoDto.getCidade());
	                    novoEndereco.setPessoa(person);
	                    enderecos.add(novoEndereco);
	                }
	            }

	            person.setEnderecos(enderecos);
	        }

	        person.setEnderecos(this.removeDuplicado(person));

	        person = pessoaRepository.save(person);

	        pessoaDto.setMessage("As alterações foram feitas com sucesso.");
	        pessoaDto.setSuccess(Boolean.TRUE);

	        return pessoaDto;
	    } else {
	        pessoaDto.setMessage("As alterações não foram feitas.");
	        pessoaDto.setSuccess(Boolean.FALSE);

	        return pessoaDto;
	    }		
	}
	
	public List<Endereco> removeDuplicado(Pessoa pessoa){
		Set<String> ceps = new HashSet<>();
        List<Endereco> enderecoDuplicado = new ArrayList<>();
        for (Endereco endereco : pessoa.getEnderecos()) {
            if (ceps.add(endereco.getCep())) {
            	enderecoDuplicado.add(endereco);
            }
        }
        return enderecoDuplicado;
	}
	
	public PessoaDto consultarPessoa(Long id) throws ParseException {	
		PessoaDto pessoaDto = new PessoaDto();
		Pessoa person = pessoaRepository.consultarPessoa(id);
		if(Objects.nonNull(person)) {
			pessoaDto.setId(person.getId());
			pessoaDto.setNome(person.getNome());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dtNascimento = dateFormat.format(person.getDtNascimento());
			pessoaDto.setDtNascimento(dtNascimento);
			
			List<EnderecoDto> enderecoDtos = new ArrayList<>();
			for(Endereco endereco: person.getEnderecos()) {
				EnderecoDto enderecoDto = new EnderecoDto();
				enderecoDto.setCep(endereco.getCep());
				enderecoDto.setLogradouro(endereco.getLogradouro());
				enderecoDto.setNumero(endereco.getNumero());
				enderecoDto.setCidade(endereco.getCidade());
				enderecoDtos.add(enderecoDto);
			}
			
			pessoaDto.setEndereco(enderecoDtos);
			
			pessoaDto.setMessage("Aqui estão todos os dados do usuário.");
			pessoaDto.setSuccess(Boolean.TRUE);
			
			return pessoaDto;
		}else {
			pessoaDto.setMessage("Essa pessoa não existe na nossa base.");
			pessoaDto.setSuccess(Boolean.FALSE);
			
			return pessoaDto;
		}
	}
	
	public List<PessoaDto> listarPessoas(){
		List<Pessoa> person = pessoaRepository.listarPessoas();
	    List<PessoaDto> pessoaDto = person.stream()
	        .map(pessoa -> {
	            PessoaDto onePerson = new PessoaDto();
	            onePerson.setId(pessoa.getId());
	            onePerson.setNome(pessoa.getNome());
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dtNascimento = dateFormat.format(pessoa.getDtNascimento());
	            onePerson.setDtNascimento(dtNascimento);
	            
	            List<EnderecoDto> enderecosDto = pessoa.getEnderecos().stream()
                    .map(endereco -> {
                        EnderecoDto enderecoDto = new EnderecoDto();
                        enderecoDto.setCep(endereco.getCep());
                        enderecoDto.setLogradouro(endereco.getLogradouro());
                        enderecoDto.setNumero(endereco.getNumero());
                        enderecoDto.setCidade(endereco.getCidade());
                        return enderecoDto;
                    })
                    .collect(Collectors.toList());

	            onePerson.setEndereco(enderecosDto);
	            return onePerson;
	        })
	        .collect(Collectors.toList());
		
		return pessoaDto;
	}
	
	public EnderecoDto createEndereco(Long idPessoa, EnderecoDto enderecoDto) {
	    Pessoa pessoa = pessoaRepository.findById(idPessoa).orElse(null);

	    if (pessoa != null) {
	        Endereco newEndereco = new Endereco();
	        newEndereco.setCep(enderecoDto.getCep());
	        newEndereco.setLogradouro(enderecoDto.getLogradouro());
	        newEndereco.setNumero(enderecoDto.getNumero());
	        newEndereco.setCidade(enderecoDto.getCidade());

	        newEndereco.setPessoa(pessoa);
	        enderecoRepository.save(newEndereco);

	        EnderecoDto newEnderecoDto = new EnderecoDto();
	        newEnderecoDto.setCep(newEndereco.getCep());
	        newEnderecoDto.setLogradouro(newEndereco.getLogradouro());
	        newEnderecoDto.setNumero(newEndereco.getNumero());
	        newEnderecoDto.setCidade(newEndereco.getCidade());

	        return newEnderecoDto;
	    }

	    return null;
	}
	
	public List<EnderecoDto> listarAddressPessoa(Long idPessoa) {
	    Pessoa pessoa = pessoaRepository.findById(idPessoa).orElse(null);

	    if (pessoa != null) {
	        List<Endereco> enderecos = pessoa.getEnderecos();
	        List<EnderecoDto> enderecosDto = new ArrayList<>();

	        for (Endereco endereco : enderecos) {
	            EnderecoDto enderecoDto = new EnderecoDto();
	            enderecoDto.setCep(endereco.getCep());
	            enderecoDto.setLogradouro(endereco.getLogradouro());
	            enderecoDto.setNumero(endereco.getNumero());
	            enderecoDto.setCidade(endereco.getCidade());
	            enderecosDto.add(enderecoDto);
	        }

	        return enderecosDto;
	    }

	    return Collections.emptyList();
	}
	
	public PessoaDto principalAddress(Long idPessoa, String cep) {
		PessoaDto pessoaDto = new PessoaDto();
		Pessoa pessoa = pessoaRepository.consultarPessoa(idPessoa);
		
		if(pessoa != null) {
			List<Endereco> enderecos = pessoa.getEnderecos();
			for(Endereco endereco: enderecos) {
				if(endereco.getCep().equals(cep)) {
					endereco.setPrincipal(true);
					pessoaDto.setMessage(endereco.getLogradouro() + " é o principal.");
					pessoaDto.setSuccess(Boolean.TRUE);
				} else {
					endereco.setPrincipal(false);
				}
				enderecoRepository.save(endereco);
			}			
		}		
		return pessoaDto;
	}
}
