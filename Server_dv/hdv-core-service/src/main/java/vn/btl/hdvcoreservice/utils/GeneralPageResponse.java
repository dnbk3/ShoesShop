package vn.btl.hdvcoreservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralPageResponse<T> implements Serializable {

    List<T> data;
    
    PageResponse pageResponse;
}
