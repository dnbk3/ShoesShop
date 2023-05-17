package vn.btl.hdvauthenservice.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class DaoUtils {

    public static Pageable createPageable(Integer page, Integer pageSize, String orderBy, String direction){
        Sort sort = null;
        if(direction.equalsIgnoreCase("desc"))
            sort = Sort.by(orderBy).descending();
        else
            sort = Sort.by(orderBy).ascending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return pageable;
    }
}
