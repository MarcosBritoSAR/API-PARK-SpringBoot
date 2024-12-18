package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    //todo: Pesquisar como diabo isso funciona
    @Query("select c from Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);

    //todo: Procorar como diabo isso funciona.

   /*
Nota de contextualização:

Dentro de ClienteServices, precisei de uma classe que buscasse o cliente através de um ID de usuário.
Para isso, o professor simplesmente criou um metodo abstrato nesta classe, e funcionou.
Não entendi como isso é possível. Acredito que não seja um méetodo próprio do JpaRepository, já que "usuario" está escrito em português.

Localizador: Aula 91 - Sessão 14

Resultado de testes: {
 - Nota 1: Aparentemente, o JPA converte parte do nome do metodo em um script SQL.
   Ao usar "UsuarioID" e retornar um objeto do tipo CLiente ele entende que deve buscar a tabela "cliente" na coluna "usuario_id".
}
*/

    // A busca dele deve ser algo assim:("SELECT id FROM cliente WHERE id_usuario = {id};")
    Cliente findByUsuarioId(Long id);
}

