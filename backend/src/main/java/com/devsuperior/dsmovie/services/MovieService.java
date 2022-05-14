package com.devsuperior.dsmovie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.Movie;
import com.devsuperior.dsmovie.repositories.MovieRepository;

@Service
public class MovieService {
	
	@Autowired //quando você já tem um sistema que já tem o gerenciamento de dependência não precisa colocar o atributo recebendo o new, o autowired já resolve isso.
	private MovieRepository repository;
	
	/*
	 *  Usamos page ao invés de lista para que o resultado seja mostrado em paginas.
	 *  A page é feita pelo MovieDTO pois é o Service/DTO que recebe as informações do front
	 *  passa para o db que retorna o resultado para o Service/DTO que por sua vez retorna ao front
	 *  Por chamamos o comando do page<MovideDTO> que realiza a busca por page<Movie> solicitando
	 *  o resultado ao repositório, depois retornamos ao <MovieDTO> para devolver a resposta da pesquisa.
	 *  A anotação transactional serve para garantir que tudo que seja feito com a JPA
	 *  seja feita por essa camada, readOnly para confirmar que é um método apenas de leitura.
	 */	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findAll(Pageable pageable) {
		
		Page<Movie> result = repository.findAll(pageable);
		Page<MovieDTO> page = result.map(x -> new MovieDTO(x));
		return page;
	}
	
	/*
	 * Método para a busca com o ID do filme, nesta função está
	 * sem a opção de consulta se o ID realmente existe na tabela,
	 * como queremos consultar apenas um id não é necessário chamar lista.
	 * comente o resultado.
	 * 
	 * Nota: após o final da semana verificar os caminhos que foram feitos
	 * e tentar novos.
	 */
	
	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		
		Movie result = repository.findById(id).get();
		MovieDTO dto = new MovieDTO(result);
		return dto;
	}
}
