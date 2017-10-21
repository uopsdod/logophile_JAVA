package com.util;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.controller.BasicController;
import com.controller.FileUploadController;
import com.model.pic.Pic;
import com.util.storage.StorageService;

@Component
public class FileUploadUtil {
	
	// 暫時處理方式,以後可能會變
//	public static String lastFileUrl = "";
	public static Pic lastPic = new Pic();

	private final StorageService storageService;
	
    @Autowired
    public FileUploadUtil(StorageService storageService) {
    	System.out.println("UtilUploadFile() called");
        this.storageService = storageService;
    }
    
    /**
     * 特別注意: 此方法須為form submit才可正常使用,因為需要有servletRequest的相關資訊***
     * @return
     */
	public String getFileUri() {
		String result = "";
		// model.put("message", "hello");
		// model.addAttribute("fileCount", fileCount);
		Stream<Path> loadAll = storageService.loadAll();
		// Path lastPath = loadAll.reduce((a, b) -> b).orElse(null);
		Optional<Path> lastPath = loadAll.findFirst();
		ResponseEntity<Resource> serveFile = null;
		if (lastPath != null) {
			// serveFile = this.serveFile(lastPath.getFileName().toString());
			try {
				result = MvcUriComponentsBuilder
						.fromMethodName(FileUploadController.class, "serveFile",
								lastPath.get().getFileName().toString())
						// .fromMethodName(BasicController.class, "serveFile",
						// lastPath.getFileName().toString())
						.build().toString();
			} catch (NoSuchElementException e) {
				Util.getConsoleLogger().info(
						"e.getStackTrace(): " + e.getStackTrace());
			}
		}
		return result;
	}
	
	
}
