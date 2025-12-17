package com.campus.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类 (对应数据库 product 表)
 */
public class Product {
    private Integer id;             // 商品ID
    private String name;            // 商品名称



    private Integer categoryId;     // 商品分类ID
    private Integer sellerId;       // 卖家ID
    private BigDecimal price;       // 价格 (使用 BigDecimal 避免精度问题)
    private String description;     // 商品描述
    private String imageUrl;        // 商品图片URL
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")// 商品状态 (1:在售, 0:已售出)
    private Date publishTime;       // 发布时间 (使用 java.util.Date)

    // TODO: 请在这里生成所有字段的 Getter 和 Setter 方法

    // 示例 Getter/Setter (省略其他)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // ... 其他 Getter/Setter ...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", sellerId=" + sellerId +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status=" + status +
                ", publishTime=" + publishTime +
                '}';
    }
}