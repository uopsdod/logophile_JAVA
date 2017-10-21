package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.model.mem.Mem;
import com.model.pic.Pic;
import com.model.pic.PicRepository;
import com.util.Util;
import com.util.FileUploadUtil;
import com.util.MessageBrokerUtil;
import com.util.storage.StorageFileNotFoundException;
import com.util.storage.StorageService;

@Controller
public class FileUploadController {
	
	@Autowired
    PicRepository picRepository;
	
    private final StorageService storageService;
    private FileUploadUtil utilUploadFile;
    private MessageBrokerUtil utilWebOSocketMsgBroker;
    private static AtomicInteger fileCount = new AtomicInteger();
    
    @Autowired
    public FileUploadController(StorageService storageService, MessageBrokerUtil utilWebOSocketMsgBroker, FileUploadUtil utilUploadFile) {
    	System.out.println("BasicController() called");
        this.storageService = storageService;
        this.utilUploadFile = utilUploadFile;
        this.utilWebOSocketMsgBroker = utilWebOSocketMsgBroker;
    }
	
    
    /**
     * 
     * @param file
     * @param redirectAttributes
     * @return
     * @throws IOException 
     */
    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
    	/** 將file存入 **/
    	Util.getConsoleLogger().info("handleFileUpload() starts");
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        fileCount.incrementAndGet();
        
        Util.getConsoleLogger().info("handleFileUpload() here02");
        
        /** 寫入DB **/
		Pic pic = new Pic();
		pic.setPicUrl(this.utilUploadFile.getFileUri());
		pic.setPicFile(file.getBytes());
		
		pic = picRepository.save(pic);
		
		/** 紀錄當下照片(暫時作法,以後須修改) **/
		FileUploadUtil.lastPic = pic;
		
		/** 通知前端要更新畫面 **/
    	this.utilWebOSocketMsgBroker.sendJsonToTopicSubcriber(MessageBrokerUtil.CHANNEL_fileUploaded, pic);
        
        Util.getConsoleLogger().info("handleFileUpload() ends");
        return "redirect:/";
    }    
    
    /**
     * DONWLOAD FILE:
     * first, you'll get an uri for a certain file.
     * Then, you'll be able to use that uri to download the file through this mapping
     * This method is called in listUploadedFiles MvcUriComponentsBuilder.fromMethodName(... ,... ,...)
     * @param filename
     * @return
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    private ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    	Util.getConsoleLogger().info("serveFile() starts");
        Resource file = storageService.loadAsResource(filename);
        Util.getConsoleLogger().info("serveFile() ends");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
    	Util.getConsoleLogger().info("handleStorageFileNotFound() starts");
    	Util.getConsoleLogger().info("handleStorageFileNotFound() ends");
    	
        return ResponseEntity.notFound().build();
    }

// 先留下此段註解,作為以後reference使用
//  /**
//   * All files are cached in storageService
//   * If you type "/" you'll be redirected to "uploadForm"(jsp) and a list of file pathes will be put in thymeleaf with the key "files"
//   * @param model
//   * @return
//   * @throws IOException
//   */
//  @GetMapping("/")
//  public String listUploadedFiles(Model model) throws IOException {
//  	Util.getConsoleLogger().info("listUploadedFiles() starts");
//      model.addAttribute("files", storageService
//              .loadAll()
//              .map(path ->
//                      MvcUriComponentsBuilder
//                              .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
//                              .build().toString())
//              .collect(Collectors.toList()));
//      
//      /** 加上評分歷史紀錄,並讓最新的評論在最上面 **/
//      String RatingHistoryListResult = BasicController.getRatingHistoryListOutput();
//      System.out.println("listUploadedFiles() input RatingHistoryListResult: " + RatingHistoryListResult);
//      model.addAttribute("ratingHistoryList", RatingHistoryListResult); // "ratingHistoryList" 關聯前端，謹慎更動
//      
//      Util.getConsoleLogger().info("listUploadedFiles() ends");
//      
//      return "main_page";
////      return "uploadForm";
//  }
}
