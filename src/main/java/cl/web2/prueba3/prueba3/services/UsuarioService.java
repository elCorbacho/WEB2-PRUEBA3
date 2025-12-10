package cl.web2.prueba3.prueba3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.web2.prueba3.prueba3.models.Usuario;
import cl.web2.prueba3.prueba3.models.Rol;
import cl.web2.prueba3.prueba3.repository.UsuarioRepository;
import java.util.Optional;
import java.util.List;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public List<Usuario> obtenerTodosLosUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }
    
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setActivo(usuarioActualizado.isActivo());
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    
    public boolean eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean validarCredenciales(String email, String password, String perfil) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().isActivo()) {
            Rol rolEsperado = perfil.equalsIgnoreCase("estudiante") ? Rol.ESTUDIANTE : Rol.PROFESOR;
            return usuario.get().getPassword().equals(password) && usuario.get().getRol() == rolEsperado;
        }
        return false;
    }
    
    public boolean validarCredenciales(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().isActivo()) {
            return usuario.get().getPassword().equals(password);
        }
        return false;
    }
}
