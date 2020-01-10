package com.zelin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    private String queryString;
    private String catalogName;
    private String price;
    private String sort;
}
