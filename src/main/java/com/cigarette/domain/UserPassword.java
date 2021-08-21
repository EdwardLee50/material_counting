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
 * @create 2021-08-10 20:47
 */
@ApiModel(value = "com-cigarette-domain-UserPassword")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPassword implements Serializable {
    private static final long serialVersionUID = 4278370069538524454L;
    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "")
    private Integer userId;

    @ApiModelProperty(value = "")
    private String encryptedPassword;

    @ApiModelProperty(value = "")
    private Date gmtCreate;

    @ApiModelProperty(value = "")
    private Date gmtModify;
}