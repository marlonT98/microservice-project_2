package org.tmarlon.springcloud.msvc.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tmarlon.springcloud.msvc.cursos.models.Usuario;

@FeignClient(name = "msvc-usuarios" ,url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario findById(@PathVariable Long id);

    @PostMapping("/")
    Usuario save (@RequestBody Usuario usuario);











}
