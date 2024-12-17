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

    Dentro de Cliente Services, eu precisei de uma classe que buscasse o meu cliente através de um id usuario.
    Para isso, o professor simpliesmente criou um metodo abstrado aqui nessa classe e pronto, funcionou. Não entendi de forma alguma
    como isso é possivel. Acredito que não seja um metodo propio do JpaRepository já que usuarioEstá escrito em portugues

     */
    //@Query("select c from Cliente c where c.id = 1") testando
    Cliente findByUsuarioId(Long id);
}
