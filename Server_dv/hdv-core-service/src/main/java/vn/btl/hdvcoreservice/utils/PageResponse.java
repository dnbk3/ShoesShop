package vn.btl.hdvcoreservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

    private Integer totalPages;
    private Integer currentPage;
    private Long totalElements;

}
