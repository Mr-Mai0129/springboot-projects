package com.hr_manage.controller;

import com.hr_manage.dto.ApiJson;
import com.hr_manage.dto.FileJson;
import com.hr_manage.entity.File;
import com.hr_manage.service.IFileService;
import com.hr_manage.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/file")
@RestController
public class FileController {

    @Value("${filePath}")
    private String filePath;

    @Autowired
    private IFileService fileService;

    @Autowired
    private FileJson fileJson;

    //上传文件
    @RequestMapping("/uploadfile.do")
    public FileJson uploadFile(MultipartFile file) {
        File f = new File();
        f.setName(file.getOriginalFilename());
        f.setPath(filePath+f.getName());
        try {
            FileUtil.uploadFile(file.getBytes(),filePath,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            fileJson.setCode(0);
            fileJson.setMessage("文件上传失败");
            return  fileJson;
        }
         fileService.add(f);
        fileJson.setCode(1);
        fileJson.setMessage("文件上传成功");
        fileJson.setId(f.getId());
        fileJson.setName(f.getName());
        fileJson.setPath(f.getPath());
        return fileJson;
    }

    //删除文件
    @RequestMapping("/remove.do")
    public FileJson removeFile(@RequestParam("id") Integer fid,String path){
        java.io.File file = new java.io.File(path);
        if (file.exists()){
            file.delete();
            fileService.deleteById(fid);
            fileJson.setCode(1);
            fileJson.setMessage("文件删除成功");
        }
        fileJson.setCode(0);
        fileJson.setMessage("文件删除失败");
        return  fileJson;
    }
}
