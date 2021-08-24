package com.cigarette.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author EdwardLee
 * @create 2021-08-21 21:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    List<T> data;

    Integer totalCount;

    Integer totalPage;

}
