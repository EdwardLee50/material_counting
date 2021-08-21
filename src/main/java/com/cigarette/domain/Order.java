package com.cigarette.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EdwardLee
 * @create 2021-08-13 20:16
 */
@ApiModel(value = "com-cigarette-domain-Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = -696292652280528409L;
    @ApiModelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "")
    private String material;

    @ApiModelProperty(value = "")
    private String unit;

    @ApiModelProperty(value = "")
    private Integer number;

    /**
     * 1,失效；2，存疑，3，配送中；4，已送达；5，已确认；6，已归档
     */
    @ApiModelProperty(value = "1,失效；2，存疑，3，配送中；4，已送达；5，已确认；6，已归档")
    private Integer status;

    @ApiModelProperty(value = "")
    private String description;

    @ApiModelProperty(value = "")
    private Integer shopSellerId;

    @ApiModelProperty(value = "")
    private Integer employeeId;

    @ApiModelProperty(value = "")
    private Date gmtCreate;

    @ApiModelProperty(value = "")
    private Date gmtModify;
}