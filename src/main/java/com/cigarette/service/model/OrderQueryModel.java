package com.cigarette.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author EdwardLee
 * @create 2021-08-19 22:15
 */
@ApiModel(value = "com-cigarette-domain-OrderQueryModel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryModel {

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
    private Date start;

    @ApiModelProperty(value = "")
    private Date end;
}
