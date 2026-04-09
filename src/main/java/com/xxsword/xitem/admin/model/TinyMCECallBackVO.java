package com.xxsword.xitem.admin.model;

import lombok.Data;

import java.util.Map;

@Data
public class TinyMCECallBackVO {
    private String url;// 文件路径
    /**
     * filetype为file时: { text: 'My text' }
     * filetype为image时: { alt: 'My alt text' }
     * filetype为media时: { source2: 'alt.ogg', poster: 'image.jpg' }
     */
    private Map<String, String> jsonInfo;
}
