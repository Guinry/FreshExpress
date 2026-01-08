package com.fresh.service.impl;

import com.fresh.constant.MessageConstant;
import com.fresh.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class CommonServiceImpl implements CommonService {
    private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Value("${file.storage.access-path}")
    private String accessPath;

    @Value("${file.storage.base-path}")
    private String basePath;

    @Value("${file.storage.project-path}")
    private String projectPath;
    /**
     * 文件上传
     *
     * @param bytes
     * @param originalFilename
     * @return
     */
    @Override
    public String upload(byte[] bytes, String originalFilename) {
        String suffix = "";
        int idx = originalFilename.lastIndexOf(".");
        if (idx != -1) {
            suffix = originalFilename.substring(idx);
        }
        String newFileName = UUID.randomUUID() + "_" + System.currentTimeMillis() + suffix;
        try {
            File dir = new File(basePath, projectPath);
            if (!dir.exists()) {
                boolean success = dir.mkdirs();
                log.info("创建目录：{}，结果：{}", dir.getAbsolutePath(), success);
            }

            File target = new File(dir, newFileName);
            try(FileOutputStream fos = new FileOutputStream(target)) {
                fos.write(bytes);
            }

            return accessPath + basePath + projectPath + "/" + newFileName;
        } catch (Exception e) {
            log.error("文件上传失败：",e);
            throw new RuntimeException(MessageConstant.UPLOAD_FAILED);
        }
    }
}
