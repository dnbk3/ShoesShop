package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    private Long dictItemId;

    private String itemCode;

    private String itemName;

    private Long parentItemId;

    private Integer level;

}
