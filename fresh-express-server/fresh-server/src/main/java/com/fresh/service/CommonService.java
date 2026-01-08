package com.fresh.service;

public interface CommonService {
    /**
     * 文件上传
     *
     * @param bytes
     * @param originalFilename
     * @return
     */
    String upload(byte[] bytes, String originalFilename);
}
