package br.com.douglas.petshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
@RequestMapping(path = "api/v1/upload")
public class UploadController {

    String uploadDirectory = "c:\\";

    @PostMapping(path = "/servicos", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> saveEmployee(@RequestParam("file") MultipartFile document) {
        try {

            // Gere um nome de arquivo único com UUID
            // java.util.UUID uuid = java.util.UUID.randomUUID();
            // String fileName = uuid.toString();

            //Nome do arquivo
            String fileName = "teste";
            String originalFileName = document.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // Construa o caminho completo para o arquivo
            java.nio.file.Path filePath = Paths.get(uploadDirectory, fileName);

            // Salve o arquivo no diretório
            File file = new File(uploadDirectory, fileName + fileExtension);
            document.transferTo(file);

            // Salve apenas o caminho no banco de dados...
            String imageUrl = uploadDirectory + "\\" + fileName + fileExtension;
            //FotoFesta foto = new FotoFesta();
            //foto.setUrl(imageUrl); // Salvar o URL no objeto Foto
            //return ResponseEntity.status(HttpStatus.OK).body(fotoService.inserir(foto));
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (IOException e) {
            // Tratar exceção de leitura de arquivo
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
        }
    }
}
