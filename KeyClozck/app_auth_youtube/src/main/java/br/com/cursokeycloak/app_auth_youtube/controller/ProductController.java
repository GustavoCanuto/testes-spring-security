package br.com.cursokeycloak.app_auth_youtube.controller;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

	 @GetMapping("/{site}")
	    public String list(@PathVariable String site) {

	        if (site.equalsIgnoreCase("G1")) {
	            if (isAdmin()) {
	                return "Listando produtos - Site G1 (Admin)";
	            } else {
	                return "Acesso negado. Permissão necessária: Admin";
	            }
	        } else if (site.equalsIgnoreCase("VEJA")) {
	            if (isAdmin() || isUser()) {
	                return "Listando produtos - Site VEJA (Admin ou User)";
	            } else {
	                return "Acesso negado. Permissão necessária: Admin ou User";
	            }
	        } else {
	            return "Listando produtos - Outro site (Qualquer um)";
	        }
	    }

	    private boolean isAdmin() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
	    }

	    private boolean isUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        return authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
	    }

	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public String create() {
		return "Cadastrando produtos";
	}
	
}
