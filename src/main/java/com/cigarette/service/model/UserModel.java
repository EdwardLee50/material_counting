package com.cigarette.service.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *@author EdwardLee
 *@create 2021-08-09 11:04
 */
@ApiModel(value="com-cigarette-domain-UserInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {
    private static final long serialVersionUID = 304770532490634281L;
    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private String name;

    @ApiModelProperty(value="")
    private String password;

    @ApiModelProperty(value="")
    private String tel;

    /**
    * -1,失效用户;1,employee;2,shop_seller;3,admin
    */
    @ApiModelProperty(value="-1,失效用户;1,employee;2,shop_seller;3,admin")
    private Integer role;

    /**
     * 0表示未禁用，1表示已禁用
     */
    @ApiModelProperty(value = "0表示未禁用，1表示已禁用")
    private Boolean isDisabled;

    @ApiModelProperty(value="")
    private Date gmtCreate;

    @ApiModelProperty(value="")
    private Date gmtModify;


    private String subId;

}