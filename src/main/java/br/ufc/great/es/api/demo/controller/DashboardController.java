package br.ufc.great.es.api.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.service.CommentService;
import br.ufc.great.es.api.demo.service.PictureService;
import br.ufc.great.es.api.demo.service.PostService;
import br.ufc.great.es.api.demo.service.UsersService;
import br.ufc.great.es.api.demo.utils.MyRequestInfo;

/**
 * Faz o controle do Dashboard
 * @author armandosoaressousa
 *
 */
@Controller
public class DashboardController {
	
	private UsersService userService;
	private String acesso;
	private CommentService commentService;
	private PictureService pictureService;
	private PostService postService;
	
	@Autowired
	private MyRequestInfo mySessionInfo;

	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Autowired
	public void setPictureService(PictureService pictureService) {
		this.pictureService = pictureService;
	}
	
	@Autowired
	public void setUserService(UsersService userService) {
		this.userService = userService;
	}
	
    @RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * Verifica quais são as permissões do usuário logado e direciona para o dashboard correto
	 * @param model
	 * @param principal
	 * @return
	 */
    @RequestMapping("/")
    public String index(Principal principal) {    
    	    
    	String servico="/dashboard";
    	
    	if (mySessionInfo.hasRole("ADMIN") && mySessionInfo.hasRole("USER")) {
    		servico = servico + "/admin";
    		return "redirect:"+servico;
    	}
    	if (mySessionInfo.hasRole("USER")) {
    		servico = servico + "/user";
    		return "redirect:"+servico;
    	}
		return "redirec:/logout";    	           	    	
    }
    
    /**
     * Carrega o dashboard do usuário administrador do sistema
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping("/dashboard/admin")
    public String indexAdmin(Principal principal) {
    	int totalUsers=0;
    	int totalComments=0;
    	int totalPictures=0;
    	int totalPosts=0;
    	
    	totalUsers = (int) this.userService.getListAll().size();
    	
    	Users loginUser = userService.getUserByUserName(mySessionInfo.getCurrentUser().getUsername());
    	
    	totalComments = loginUser.getPerson().getComments().size();
    	totalPictures = loginUser.getPerson().getPictures().size();
    	totalPosts = loginUser.getPerson().getPosts().size();
    	
    	acesso = mySessionInfo.getAcesso();
    	
    	List<Users> listUsers = this.userService.getListAll();
    	    	    	     	
        return "dashboard/index";
    }
    
    /**
     * Carrega o dashboard do usuário comum
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping("/dashboard/user")
    public String indexUser(Principal principal) {    	    	
    	int totalUsers = (int) this.userService.getListAll().size();    	
    	Users loginUser = mySessionInfo.getCurrentUser();
    	    	
    	acesso = mySessionInfo.getAcesso();
    	    	
        return "dashboard/indexUser";
    }
       
}