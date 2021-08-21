package com.cigarette.controller;

import com.cigarette.common.annotation.NoRepeatSubmit;
import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.response.ApiRestResponse;
import com.cigarette.common.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author EdwardLee
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/download")
    @ResponseBody
    public ApiRestResponse fileDownLoad(HttpServletResponse response, @RequestParam("file_name") String fileName) throws BusinessException, IOException {
        Logger logger = LoggerFactory.getLogger(FileController.class);
        String pathName = getClassesPath() +
                "fileResources" + File.separator +
                "download" + File.separator +
                fileName;
        File file = new File(pathName);
        if (!file.exists()) {
            throw new BusinessException(EnumBusinessError.FILE_NOT_EXISTS);
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        OutputStream os = null;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
            logger.info("download the file" + fileName + "successfully. ");
        } catch (IOException e) {
            logger.error(String.valueOf(e));
            throw new BusinessException(EnumBusinessError.FILE_NOT_EXISTS);
        } finally {
            if (os != null) {
                os.close();
            }
        }
        return ApiRestResponse.success();
    }

    @NoRepeatSubmit
    @PostMapping("/upload")
    @ResponseBody
    public ApiRestResponse fileUpLoad(@RequestParam("file") MultipartFile file) throws BusinessException, IOException {
        // 获取时间戳
        String patternStr = "yyyyMMddHHmm";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String timeStamp = TimeUtils.getTimeStr(patternStr);
        String prefix = timeStamp + uuid;
        // 如果文件不为空，则写入上传路径
        if (!file.isEmpty()) {
            // 上传文件路径
            String upLoadPath = getClassesPath() +
                    "fileResources" + File.separator +
                    "upload";
            // 上传文件名
            String fileName = file.getOriginalFilename();
            if(fileName == null){
                throw new BusinessException(EnumBusinessError.FILE_NOT_EXISTS);
            }

            // 若文件后缀不为 .xls 或 .xlsx 结尾，则抛出异常
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if (!".xls".equals(suffixName) && !".xlsx".equals(suffixName)) {
                throw new BusinessException(EnumBusinessError.FILE_FORMAT_ERROR);
            }

            File filepath = null;
            filepath = new File(upLoadPath, fileName);
            // 判断路径是否存在，没有创建
            if (!filepath.getParentFile().exists()) {
                boolean mkdirs = filepath.getParentFile().mkdirs();
                if(!mkdirs){
                    throw new BusinessException(EnumBusinessError.FILE_NOT_EXISTS.setMessage("上传文件路径创建异常"));
                }
            }

            // 将上传文件保存到newFile中，存储的文件格式为：年月日+uuid+上传时文件本名
            File newFile = new File(upLoadPath + File.separator + prefix + fileName);
            file.transferTo(newFile);

            logger.info("upload the file" + newFile.getName() + " successfully. ");

            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("timeStamp", timeStamp);
            returnMap.put("uuid", uuid);
            returnMap.put("fileName", fileName);
            return ApiRestResponse.success(returnMap);
        }
        throw new BusinessException(EnumBusinessError.FILE_NOT_EXISTS);
    }

    private String getClassesPath() {
        return Objects.requireNonNull(Class.class.getResource("/")).getPath();
    }
}
