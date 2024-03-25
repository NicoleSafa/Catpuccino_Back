package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.UsuarioMapper;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Metodo para ReservaService
    public Usuario getByIdUsuario(Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }



    //LISTAR
    public List<UsuarioDTO> listarUsuario() {return usuarioMapper.toDTO(usuarioRepository.findAll());}


    @Override
    public Usuario loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        return usuarioRepository.findTopByNombreUsuario(nombreUsuario).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public UsuarioDTO getByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findTopByNombreUsuario(nombreUsuario).orElse(null);

        if (usuario!=null){
            return usuarioMapper.toDTO(usuario);
        }else{
            throw  new UsernameNotFoundException("Usuario no encontrado");
        }

    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO){
        return usuarioMapper.toDTO(usuarioRepository.save(usuarioMapper.toEntity(usuarioDTO)));
    }

    public Boolean existByCredentials(String nombreUsuario, String password){
        Usuario usuario = usuarioRepository.findTopByNombreUsuario(nombreUsuario).orElse(null);
        return usuario != null  && passwordEncoder.matches(password,usuario.getPassword());
    }

}
