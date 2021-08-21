package com.cigarette.controller.viewObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author EdwardLee
 * @create 2021-08-09 11:04
 *
 * 注册、查询对外暴露的user信息包装
 */
@ApiModel(value = "com-cigarette-domain-UserInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReturnVo implements Serializable {

    private static final long serialVersionUID = -7721512952333028639L;

    @ApiModelProperty(value = "")
    private Integer id;

    @NotNull(message = "姓名不可为空")
    @Length(max = 4, message = "姓名不可超过4位")
    @ApiModelProperty(value = "")
    private String name;

    @NotNull(message = "电话不可为空")
    @Length(max = 11, message = "联系电话需小于11位")
    @ApiModelProperty(value = "")
    private String tel;

    /**
     * -1,失效用户;1,employee;2,shop_seller;3,admin
     */
    @ApiModelProperty(value = "-1,失效用户;1,employee;2,shop_seller;3,admin")
    private Integer role;

}