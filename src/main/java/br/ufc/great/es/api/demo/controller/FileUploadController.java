package br.ufc.great.es.api.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.ufc.great.es.api.demo.utils.ManipuladorDatas;
import br.ufc.great.es.api.demo.utils.MyRequestInfo;

@Controller
public class FileUploadController {

	private UsersService userService;
	private Users loginUser;
	private PersonService personService;
	private PictureService pictureService;
	
	@Autowired
	private MyRequestInfo mySessionInfo;

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

	private void checkUser() {
		loginUser = mySessionInfo.getCurrentUser();
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
	public String listMyPictures(@PathVariable Long id, final RedirectAttributes ra) {
		Person person = this.personService.get(id);
		
		if (person.getPictures().size() == 0) {
			ra.addFlashAttribute("errorFlash", "Você precisa cadastrar pelo menos uma imagem!");
			//redireciona para o formulário para adicionar uma nova foto
			return "redirect:/person/" + id + "/select/picture"; 
		}
		
		List<Picture> list = person.getPictures();
		
		checkUser();
		
		return "/uploads/listMypictures";
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
	 * Faz o upload de uma imagem do usuário
	 * @param idUser Id do Usuário
	 * @param model Model
	 * @param files Array de Arquivos
	 * @return Formulário do Usuário
	 */
	@RequestMapping("/upload/selected/image/users/{idUser}")
	public String upload(@PathVariable(value = "idUser") Long idUser, @RequestParam("photouser") MultipartFile[] files) {
		StringBuilder fileNames = new StringBuilder();
		new Constantes();
		String uploadFilePath = Constantes.uploadUserDirectory; 	  
		String idAux = String.valueOf(idUser);

		for (MultipartFile file : files) {
			Path fileNameAndPath = Paths.get(uploadFilePath, idAux+".png");
			fileNames.append(idAux+".png"+" ");
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		checkUser();
		Users user = this.userService.get(idUser);

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
	public String uploadPicture(@PathVariable(value = "personId") Long personId, Picture picture, @RequestParam("photouser") MultipartFile[] files, RedirectAttributes ra) {
		StringBuilder fileNames = new StringBuilder();

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
		String path = uploadFilePath + FileSystems.getDefault().getSeparator() + idAux + "-" + data + ".png";
		String systemName = idAux + "-" + data;
		
    	picture.setPath(path);
    	picture.setSystemName(systemName);
    	
    	//Dono da imagem
    	Person person = this.personService.get(personId);
    	person.addPicture(picture, person);
    	    	
    	Person save = this.personService.save(person);
		
    	//Salva os bytes do arquivo no arquivo criado no filesystem
		for (MultipartFile file : files) {
			Path fileNameAndPath = Paths.get(uploadFilePath, idAux + "-" + data + ".png");
			fileNames.append(idAux + "-" + data + ".png"+" ");
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		checkUser();
		
		//List de fotos da pessoa
		List<Picture> list = person.getPictures();

		return "/uploads/listMypictures";
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
