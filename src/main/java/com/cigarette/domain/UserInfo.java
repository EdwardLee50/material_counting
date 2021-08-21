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
 * @create 2021-08-10 20:55
 */
@ApiModel(value = "com-cigarette-domain-UserInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1942351353127994512L;
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private String tel;

    /**
     * -1,失效用户;1,employee;2,shop_seller;3,admin
     */
    @ApiModelProperty(value = "-1,失效用户;1,employee;2,shop_seller;3,admin")
    private Integer role;

    /**
     * 0表示未禁用，1表示已禁用
     */
    @ApiModelProperty(value = "0表示未禁用，1表示已禁用")
    private Boolean isDisabled;

    @ApiModelProperty(value = "")
    private Date gmtCreate;

    @ApiModelProperty(value = "")
    private Date gmtModify;

}