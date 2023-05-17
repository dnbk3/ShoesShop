package vn.btl.hdvauthenservice.utils;

import org.springframework.data.domain.Page;

public class PageResponseUtil {
    public static PageResponse buildPageResponse(Page page){
        int currentPage = page.getNumber();
        if (currentPage <= page.getTotalPages()) {
            return new PageResponse(page.getTotalPages(), currentPage, page.getTotalElements());
        } else {
            return new PageResponse(page.getTotalPages(), null, page.getTotalElements());
        }
    }

}
