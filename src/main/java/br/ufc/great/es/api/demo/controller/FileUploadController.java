package br.ufc.great.es.api.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.great.es.api.demo.model.Person;
import br.ufc.great.es.api.demo.model.Picture;
import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.service.PersonService;
import br.ufc.great.es.api.demo.service.PictureService;
import br.ufc.great.es.api.demo.service.UsersService;
import br.ufc.great.es.api.demo.utils.Constantes;
import br.ufc.great.es.api.demo.utils.FileSaver;
import br.ufc.great.es.api.demo.utils.ManipuladorDatas;

@Controller
public class FileUploadController {

	private UsersService userService;
	private Users loginUser;
	private PersonService personService;
	private PictureService pictureService;
	
	@Autowired
	public void setUserService(UsersService userServices){
		this.userService = userServices;
	}
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Autowired
	public void setPictureService(PictureService pictureService) {
		this.pictureService = pictureService;
	}
	
	@Autowired
	private FileSaver fileSaver;

	private void checkUser() {
	}

	@RequestMapping("/uploads")
	public String UploadPage(Model model) {
		return "uploads/uploadview";
	}
	
	/**
	 * Carrega a página contendo todas a fotos do usuário
	 * @param id Id da pessoa
	 * @param model Model
	 * @return listMypictures
	 */
	@RequestMapping("/upload/person/{id}/picture")
	public String listMyPictures(@PathVariable Long id, Model model, final RedirectAttributes ra) {
		Person person = this.personService.get(id);
		
		if (person.getPictures().size() == 0) {
			ra.addFlashAttribute("errorFlash", "Você precisa cadastrar pelo menos uma imagem!");
			//redireciona para o formulário para adicionar uma nova foto
			return "redirect:/person/" + id + "/select/picture"; 
		}
		
		List<Picture> list = person.getPictures();
		
		checkUser();
		/*
		model.addAttribute("user", person.getUser());
		model.addAttribute("person", person);
		model.addAttribute("list", list);
		model.addAttribute("loginusername", loginUser.getUsername());
		model.addAttribute("loginemailuser", loginUser.getEmail());
		model.addAttribute("loginuserid", loginUser.getId());
		model.addAttribute("loginuser", loginUser);
		model.addAttribute("s3awsurl", new Constantes().s3awsurl);
		*/
		return "/uploads/listMyPictures";
	}
	
	/**
	 * Renomeia um arquivo
	 * @param file arquivo
	 * @param newName novo nome
	 * @return Arquivo com novo nome
	 * @throws IOException
	 */
	public File renameFile(File file, String newName) throws IOException {
		// File (or directory) with new name
		File file2 = new File(newName);

		if (file2.exists())
			throw new java.io.IOException("file exists");

		// Rename file (or directory)
		boolean success = file.renameTo(file2);

		if (success) {
			return file;
		}else {
			return null;
		}

	}

	/**
	 * Converte um MultipartFile em File
	 * @param file
	 * @return
	 */
	public File convert(MultipartFile file)
	{    
	    File convFile = null;
		try {
			convFile = new File(file.getOriginalFilename());
			convFile.createNewFile(); 
			FileOutputStream fos = new FileOutputStream(convFile); 
			fos.write(file.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    
	    return convFile;
	}
	
	/**
	 * Faz o upload de uma imagem do usuário para o bucket de usuario
	 * @param idUser Id do Usuário
	 * @param model Model
	 * @param files Array de Arquivos
	 * @return Formulário do Usuário
	 */
	@RequestMapping("/upload/selected/image/users/{idUser}")
	public String upload(@PathVariable(value = "idUser") Long idUser, Model model,@RequestParam("photouser") MultipartFile files) {
		new Constantes(); 	  
		String idAux = String.valueOf(idUser);		
		String bucketName = Constantes.bucketPrincipal;
		
		File myFile = convert(files);
		File newFile = new File(idAux+".png");			
		
		//Transforma uma imagem qualquer em png para padronizar as imagens do sistema
		try {
			FileChannel src = new FileInputStream(myFile).getChannel();
			FileChannel dest = new FileOutputStream(newFile).getChannel();
			dest.transferFrom(src, 0, src.size());
			System.out.println("Arquivo transformado em .png com sucesso!");
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nao existe!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro ao transformar arquivo em .png.");
			e.printStackTrace();
		}
		
		MultipartFile multipartFileToSend=null;
		try {
			InputStream stream =  new FileInputStream(newFile);
			multipartFileToSend = new MockMultipartFile(idAux+".png", newFile.getName(), MediaType.IMAGE_PNG_VALUE, stream);
			System.out.println("Multipart transformado em arquivo com sucesso!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERRO: no Multipart transformado em arquivo.");
			e.printStackTrace();
		}	    
		
		if (!files.isEmpty()){
			String path = fileSaver.write(multipartFileToSend, "users");				
		}

		checkUser();
		Users user = this.userService.get(idUser);

		/*
		model.addAttribute("user", user);
		model.addAttribute("loginusername", loginUser.getUsername());
		model.addAttribute("loginemailuser", loginUser.getEmail());
		model.addAttribute("loginuserid", loginUser.getId());
		model.addAttribute("loginuser", loginUser);
		model.addAttribute("successFlash", "Successfully uploaded files " + newFile.getName());
		model.addAttribute("s3awsurl", new Constantes().s3awsurl);
		*/

		return "uploads/formpwd";
	}

	/**
	 * Faz o upload de fotos da pessoa para seu album de fotos
	 * @param personId id da Pessoa
	 * @param model Model
	 * @param files arquivos de imagens que serão carregados para o album de fotos
	 * @return carrega listMyPictures.html
	 */
	@RequestMapping(value="/upload/selected/picture/person/{personId}")
	public String uploadPicture(@PathVariable(value = "personId") Long personId, Picture picture, Model model,@RequestParam("photouser") MultipartFile files, RedirectAttributes ra) {
		new Constantes();
		String uploadFilePath = Constantes.picturesDirectory; 	  
		String idAux = String.valueOf(personId);
		String padrao = "yyyy/MM/dd HH:mm:ss";
		
		new ManipuladorDatas();
		String currentData = ManipuladorDatas.getCurrentDataTime(padrao);
		String dataAux = currentData.replace("/", "-");
		String data1 = dataAux.replaceAll(":", "-").trim();
		String data = data1.replace(" ", "-");
		
		//Define o diretório da imagem e o nome do arquivo que será salvo no filesystem
		String pathImage = uploadFilePath + FileSystems.getDefault().getSeparator() + idAux + "-" + data + ".png";
		String systemName = idAux + "-" + data;
		
    	picture.setPath(pathImage); //TODO: precisa corrigir para pegar a url da image no bucket
    	picture.setSystemName(systemName);
    	
    	//Dono da imagem
    	Person person = this.personService.get(personId);
    	person.addPicture(picture, person);    	    	
    	Person save = this.personService.save(person);				
		
		File myFile = convert(files);
		File newFile = new File(systemName +".png");			
		
		//Transforma uma imagem qualquer em png para padronizar as imagens do sistema
		try {
			FileChannel src = new FileInputStream(myFile).getChannel();
			FileChannel dest = new FileOutputStream(newFile).getChannel();
			dest.transferFrom(src, 0, src.size());
			System.out.println("Arquivo transformado em .png com sucesso!");
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nao existe!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro ao transformar arquivo em .png.");
			e.printStackTrace();
		}
		
		MultipartFile multipartFileToSend=null;
		try {
			InputStream stream =  new FileInputStream(newFile);
			multipartFileToSend = new MockMultipartFile(systemName + ".png", newFile.getName(), MediaType.IMAGE_PNG_VALUE, stream);
			System.out.println("Multipart transformado em arquivo com sucesso!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERRO: no Multipart transformado em arquivo.");
			e.printStackTrace();
		}	    
		
		if (!files.isEmpty()){
			String path = fileSaver.write(multipartFileToSend, "pictures");				
		}

		checkUser();
		
		//List de fotos da pessoa
		List<Picture> list = person.getPictures();

		/*
		model.addAttribute("user", person.getUser());
		model.addAttribute("person", person);
		model.addAttribute("list", list);
		model.addAttribute("loginusername", loginUser.getUsername());
		model.addAttribute("loginemailuser", loginUser.getEmail());
		model.addAttribute("loginuserid", loginUser.getId());
		model.addAttribute("loginuser", loginUser);
		model.addAttribute("successFlash", "Successfully uploaded files " + newFile.getName());
		model.addAttribute("s3awsurl", new Constantes().s3awsurl);
		*/
	
		return "/uploads/listMyPictures";
	}

	/**
	 * Carrega um array de bytes de uma foto
	 * @param imageName identificador interno da foto
	 * @return array de bytes do arquivo da foto
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload/picture/{pictureName}")
	@ResponseBody
	public byte[] getPicture(@PathVariable(value = "pictureName") String imageName) throws IOException {
		new Constantes();
		String uploadFilePath = Constantes.picturesDirectory;

		File serverFile = new File(uploadFilePath + FileSystems.getDefault().getSeparator() + imageName + ".png");
		
		if (serverFile.length() > 0) {
			return Files.readAllBytes(serverFile.toPath());
		}else {
			return null;
		}
		
	}

	/**
	 * Carrega um array de bytes de uma imagem de um usuário
	 * @param imageName id do Usuário
	 * @return array de bytes do arquivo da imagem
	 * @throws IOException
	 */	
	@RequestMapping(value = "/upload/image/users/{imageName}")
	@ResponseBody
	public byte[] getUserImage(@PathVariable(value = "imageName") String imageName) throws IOException {
		new Constantes();
		String uploadFilePath = Constantes.uploadUserDirectory;

		File serverFile = new File(uploadFilePath + FileSystems.getDefault().getSeparator() + imageName + ".png");      
		File userPadrao = new File(uploadFilePath + FileSystems.getDefault().getSeparator() + "anonymous2.png");

		if (serverFile.length() > 0) {
			return Files.readAllBytes(serverFile.toPath());	  
		}else {		  
			return Files.readAllBytes(userPadrao.toPath());
		}

	}


	@RequestMapping(value = "/upload/image/{imageName}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
		new Constantes();
		String uploadFilePath = Constantes.uploadDirectory;

		File serverFile = new File(uploadFilePath + FileSystems.getDefault().getSeparator() + imageName + ".png");
		return Files.readAllBytes(serverFile.toPath());
	}

	@RequestMapping(value="/viewFile")
	public String viewFile() {
		return "viewfileuploaded";
	}

	/**
	 * Dada uma foto selecionada de uma pessoa, remove essa foto
	 * @param pictureId Id da pessoa
	 * @param personId Id da fota
	 * @param ra mensagem de retorno
	 * @return listMyPictures
	 */
	@RequestMapping(value="/upload/delete/picture/{pictureId}/person/{personId}")
	public String deletePictureFromPerson(@PathVariable(value="pictureId") String pictureId, @PathVariable(value="personId") String personId,  
			final RedirectAttributes ra) {
		
		Person person = this.personService.get(Long.parseLong(personId));
		Picture picture = this.pictureService.get(Long.valueOf(pictureId));
		
		if (person.getPictures().remove(picture)) {
			this.personService.save(person);
			this.pictureService.delete(picture.getId());
			ra.addFlashAttribute("successFlash", "A foto " + pictureId + " foi removida com sucesso!");
		}else {
			ra.addFlashAttribute("errorFlash", "A foto " + pictureId + " NÁO foi removida.");
		}
		
		return "redirect:/upload/person/"+ personId + "/picture";
	}
	
}