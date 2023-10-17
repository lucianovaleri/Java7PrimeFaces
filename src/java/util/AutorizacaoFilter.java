/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {

    private static final String[][] DIREITO_ACESSO = {
        {"administrador",
            "/faces/manutencaoCategoria.xhtml",
            "/faces/manutencaoMarcas.xhtml",
            "/faces/manutencaoUsuario.xhtml",
            "/faces/login2.xhtml", 
            "/faces/telaCadastro.xhtml",
            "/faces/telaInicial.xhtml",
            "/faces/telaMinhasMaquinas.xhtml",
            "/faces/telaRelatorios.xhtml",
            "/faces/telaMaquinasAguardando.xhtml"
        },
        {"comum",       
            "/faces/telaMinhasMaquinas.xhtml",
            "/faces/telaCadastroMaquinas.xhtml",
            "/faces/telaInicialUsuario.xhtml",
            "/faces/login2.xhtml", 
            "/faces/xxx.xhtml",
        },
        
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        Usuario user = null;

        HttpSession sess = ((HttpServletRequest) req).getSession();

        if (sess != null) {
            user = (Usuario) sess.getAttribute("usuarioLogado");
        }

        if ((user == null)
                && !request.getRequestURI().endsWith("/faces/login2.xhtml")
                && !request.getRequestURI().contains("/faces/telaCadastro.xhtml")
                && !request.getRequestURI().contains("/javax.faces.resource/")) {
            // && !request.getRequestURI().endsWith("/faces/newxhtml.xhtml") 
            response.sendRedirect(request.getContextPath() + "/faces/login2.xhtml");
        } else {
            try {
                boolean foi = false;
                if (user.getTipoUsuario().equals("administrador")) {
                    for (int i = 1; i < DIREITO_ACESSO[0].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[0][i])) {
                            System.out.println(DIREITO_ACESSO[0][i] + " - ADMINISTRADOR TEM ACESSO");
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "/faces/login2.xhtml");
                    }
                } else if (user.getTipoUsuario().equals("comum")) {
                    for (int i = 1; i < DIREITO_ACESSO[1].length; i++) {
                        if (request.getRequestURI().endsWith(DIREITO_ACESSO[1][i])) {
                            chain.doFilter(req, res);
                            foi = false;
                            break;
                        } else {
                            foi = true;
                        }
                    }
                    if (foi) {
                        response.sendRedirect(request.getContextPath() + "faces/login2.xhtml");
                    }
                }

                
            } catch (NullPointerException e) {
                chain.doFilter(req, res);
            }

        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }
}
