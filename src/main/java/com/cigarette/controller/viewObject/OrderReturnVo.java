package com.cigarette.controller.viewObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author EdwardLee
 * @create 2021-08-13 20:16
 */
@ApiModel(value = "com-cigarette-domain-Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturnVo implements Serializable {

    private static final long serialVersionUID = -781838713034817083L;
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
    private UserReturnVo shopSeller;

    @ApiModelProperty(value = "")
    private UserReturnVo employee;

    @ApiModelProperty(value = "")
    private Date gmtCreate;

    @ApiModelProperty(value = "")
    private Date gmtModify;
}