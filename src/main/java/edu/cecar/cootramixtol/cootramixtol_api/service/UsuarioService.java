package edu.cecar.cootramixtol.cootramixtol_api.service;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Usuario;

public interface UsuarioService {

    Usuario registrar(Usuario usuario, String asociadoIdentificacion);

    Usuario buscarPorUsername(String username);
}
