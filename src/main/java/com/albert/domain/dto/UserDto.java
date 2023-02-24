package com.albert.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private Long cartId;
    private Long accountId;
    private List<Long> exchangeOrderIds ;
    private String userName;

}
