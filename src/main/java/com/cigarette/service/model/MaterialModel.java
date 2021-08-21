package com.cigarette.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author EdwardLee
 * @create 2021-08-15 23:48
 */
@ApiModel(value = "com-cigarette-domain-Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialModel implements Serializable {

    private static final long serialVersionUID = -5391818737196508360L;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private String unit;
}
