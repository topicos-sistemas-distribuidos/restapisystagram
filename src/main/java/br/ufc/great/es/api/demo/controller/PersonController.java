package br.ufc.great.es.api.demo.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.great.es.api.demo.model.Comment;
import br.ufc.great.es.api.demo.model.Person;
import br.ufc.great.es.api.demo.model.Picture;
import br.ufc.great.es.api.demo.model.Post;
import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.service.CommentService;
import br.ufc.great.es.api.demo.service.PersonService;
import br.ufc.great.es.api.demo.service.PictureService;
import br.ufc.great.es.api.demo.service.PostService;
import br.ufc.great.es.api.demo.service.UsersService;
import br.ufc.great.es.api.demo.utils.ManipuladorDatas;
import br.ufc.great.es.api.demo.utils.MyRequestInfo;

/**
 * Faz o controle do domínio de Controle de Acesso
 * @author armandosoaressousa
 *
 */
@Controller
public class PersonController {
	
	private PersonService personService;
	private UsersService userService;
	private Users loginUser;
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
	public void setPictureService(PictureService pictureService) {
		this.pictureService = pictureService;
	}
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
    @Autowired
    public void setUserService(UsersService userService) {
    	this.userService = userService;
    }
	
    /**
     * Atualiza os dados do usuario logado
     */
	private void checkUser() {
		//TODO fazer a checagem do usuário a cada request
	}
	
	/**
	 * Lista todas as pessoas cadastradas no sistema
	 * @param model
	 * @return página com a lista de pessoas cadastradas
	 */
    @RequestMapping(value="/person", method = RequestMethod.GET)
    public String index() {
    	List<Person> list = this.personService.getListAll();
    	checkUser();
    	
        return "person/list";
    }
    
    /**
     * Dada uma pessoa, retorna todos os comentários que ela fez
     * @param id Id de pessoa
     * @param model Model do View
     * @return listMyComments.html
     */
    @RequestMapping(value="/person/{id}/comment")
    public String listMyComments(@PathVariable Long id) {
    	Person person = this.personService.get(id);
    	List<Comment> comments = new LinkedList<Comment>();
    	
    	comments = person.getComments();
    	checkUser();
    	
        return "person/listMyComments";

    }
          
    /**
     * Retorna todos os comentários de todas as pessoas
     * @param model Model
     * @return listComments.html
     */
    @RequestMapping(value="/person/comment")
    public String listAllComments() {
    	List<Comment> comments = this.commentService.getListAll();
    	
    	checkUser();
    	
    	return "person/listComments";
    }
    
    /**
     * Dada uma pessoa e um novo comentário mostra o formulário para inserir comentário
     * @param id Id da pessoa
     * @param model Model
     * @return formComment.html
     */
    @RequestMapping(value="/person/{id}/comment/add")
    public String addMyComment(@PathVariable Long id) {
    	checkUser();
    	Person person = this.personService.get(id);
    	List<Comment> comments = person.getComments();
    	    	
    	return "person/formComment";
    }
    
    /**
     * Dada uma pessoa e um comentário preenchido, salva esse comentário na lista de comentários da pessoa
     * @param id Id da Pessoa
     * @param comment Novo comentario
     * @param ra mensagem flash de retorno
     * @return listMyComments.html
     */
    @RequestMapping(value="/person/{id}/comment/save", method=RequestMethod.POST)
    public String saveMyComment(@PathVariable Long id, Comment comment, final RedirectAttributes ra) {
    	String local="";
    	
    	Person person = this.personService.get(id);
    	person.addComment(comment, person);
    	//Atualiza a pessoa com o novo comentario
    	this.personService.update(person);
    	ra.addFlashAttribute("successFlash", "Comentário salvo com sucesso.");

    	if (this.mySessionInfo.getAcesso().equals("ADMIN")) {
    		local = "/person/comment";
    	}else {
    		local = "/person/"+id+"/comment";
    	}
    	
    	return "redirect:"+local;
    }

    @RequestMapping(value="/person/{id}/comment/update", method=RequestMethod.POST)
    public String saveMyCommentEdited(@PathVariable Long id, Comment comment, final RedirectAttributes ra) {
    	String local="";
    	
    	Person person = this.personService.get(id);
    	comment.setPerson(person);
    	
    	this.commentService.update(comment);
    	ra.addFlashAttribute("successFlash", "Comentário salva com sucesso.");
    	
    	if (this.mySessionInfo.getAcesso().equals("ADMIN")) {
    		local = "/person/comment";
    	}else {
    		local = "/person/"+id+"/comment";
    	}
    	
    	return "redirect:"+local;
    }

    
    /**
     * Dada uma pessoa e um comentário selecionado faz a edição do mesmo
     * @param personId id da Pessoa
     * @param commentId id do Comentário selecionado
     * @param model Model
     * @return formEditMyComment.html
     */
    @RequestMapping(value="/person/{personId}/comment/{commentId}/edit")
    public String editMyComment(@PathVariable Long personId, @PathVariable Long commentId) {
    	Person person = this.personService.get(personId);
    	Comment comment = person.getMyComment(commentId);
    	
    	checkUser();
    	
        return "person/formEditMyComment";
    }
            
    /**
     * Seleciona uma foto da pessoa para ser adicionada no seu album
     * @param personId id da Pessoa
     * @param model model
     * @return formPicture.html
     */
	@RequestMapping(value = "/person/{personId}/select/picture")
	public String selectImage(@PathVariable(value = "personId") Long personId){
		Person person = this.personService.get(personId);
		Picture picture = new Picture();
		
		picture.setPerson(person);
		checkUser();
		    	
        return "person/formPicture";
	}
    
	@RequestMapping(value="/person/{personId}/picture/{pictureId}/edit")
    public String editMyPicture() {
    	//TODO
    	return null;
    }
	
	/**
	 * Carrega a página contendo todos os posts do usuário
	 * @param id Id da pessoa
	 * @param model Model
	 * @return listMyPosts.html
	 */
	@RequestMapping("/person/{id}/post")
	public String listMyPosts(@PathVariable Long id, final RedirectAttributes ra) {
		Person person = this.personService.get(id);
		
		if (person.getPosts().size() == 0) {
			ra.addFlashAttribute("errorFlash", "O usuário " + person.getName() + " ainda não possui posts!");
			return "redirect:/users/list";
		}
		
		List<Post> list = person.getPosts();
		
		checkUser();

		Comment comment = new Comment();

		return "/person/listMyPosts";
	}

	/**
	 * Dada uma figura selecionada por uma pessoa cria um post nessa figura
	 * @param pictureId Id da figura
	 * @param personId Id da pessoa
	 * @param model Model
	 * @return listMyPosts.html
	 */
	@RequestMapping("/person/{personId}/picture/{pictureId}/post")
	public String createPost(@PathVariable(value="pictureId") Long pictureId, @PathVariable(value="personId") Long personId
			, final RedirectAttributes ra) {
		
		Picture picture = this.pictureService.get(pictureId);
		Person person = this.personService.get(personId);
		
		//Cria um novo post e associa a uma pessoa e a uma figura
		Post post = new Post();
		post.setPerson(person);
		person.addPost(post);
		post.setPicture(picture);
		picture.setPost(post);

		//Recupera a data corrente do sistema
		String padrao = "yyyy/MM/dd HH:mm:ss";
		String currentData = ManipuladorDatas.getCurrentDataTime(padrao);
		new ManipuladorDatas();
		//Salva a data corrente no post
		post.setDate(ManipuladorDatas.getDate());
		
		//Carrega os dados do usuário logado
		checkUser();
		
		//Salva o post no repositório de posts
		this.postService.save(post);
		//Atualiza pessoa com o post
		this.personService.update(person);
		//Atualiza foto com o post
		this.pictureService.update(picture);
		
		List<Post> list = person.getPosts();
		
		Comment comment = new Comment();
		
		ra.addFlashAttribute("successFlash", "Post da imagem criado com sucesso!");
		
		return "redirect:/person/" + person.getId() + "/post"; 
	}
	
	/*
	 * 1. Crie um comentário
	 * 2. Faca o post do comentário passando o id do post 
	 * 
	 * /person/post/'} + ${row.id} + ${'/comment'
	 * 
	 * guarde a pessoa logada que está fazendo o comentário
	 * salve esta pessoa no comentário
	 * pegue a data corrente do comentário
	 * salve a data corrente no comentário
	 * 
	 * salve o comentário no post
	 * 
	 * salve o post no repositório de posts
	 *
	 * redirecione para o listMyPost do usuário selecionado com todas as atualizaçòes
	 */ 
	@RequestMapping(value="/person/{personLogged}/post/{postId}/comment", method = RequestMethod.POST)
	public String saveCommentInSelectedPost(Comment comment, @PathVariable(value="postId") Long postId, 
			@PathVariable(value="personLogged") Long personLogged, final RedirectAttributes ra) {

		//Pega o Post selecionado
		Post post = this.postService.get(postId);
		//Pega o Dono do post
		Person personPost = post.getPerson();
		//Pessoa que vai fazer o comentário no post selecionado
		Person personComment = this.personService.get(personLogged);

		//Recupera a data corrente do sistema
		new ManipuladorDatas();
		//Salva a data corrente no comment
		comment.setDate(ManipuladorDatas.getDate());

		//Adiciona o comment no repositorio
		this.commentService.save(comment);

		//Adiciona o comment no personComment
		personComment.addComment(comment, personComment);
		
		//Atualiza o comment
		this.commentService.update(comment);

		//Atualiza o personComment
		this.personService.update(personComment);
		
		//Adiciona o comment no post
		post.addComment(comment);

		//Atualiza o post
		this.postService.update(post);
		
		//Gera a lista de posts atualizada com o novo comentário
		List<Post> list = personPost.getPosts();
		
		Comment newComment = new Comment();
		
		ra.addFlashAttribute("successFlash", "O comentário foi salvo com sucesso.");
		
		return "redirect:/person/"+ personPost.getId() + "/post";
	}
	

}