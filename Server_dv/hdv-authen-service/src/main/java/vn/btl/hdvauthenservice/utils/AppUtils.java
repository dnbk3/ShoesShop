package vn.btl.hdvauthenservice.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.beans.PropertyDescriptor;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class AppUtils {
    public AppUtils() {
    }

    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        PropertyDescriptor[] var4 = pds;
        int var5 = pds.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return (String[])emptyNames.toArray(result);
    }

    public static String[] getNullPropertyNames(Object source, String... excludes) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        PropertyDescriptor[] var5 = pds;
        int var6 = pds.length;

        int var7;
        for(var7 = 0; var7 < var6; ++var7) {
            PropertyDescriptor pd = var5[var7];
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = excludes;
        var6 = excludes.length;

        for(var7 = 0; var7 < var6; ++var7) {
            String tmp = result[var7];
            emptyNames.add(tmp);
        }

        result = new String[emptyNames.size()];
        return (String[])emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target, String... excludes) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src, excludes));
    }

    public static String deAccent(String str) {
        if (str == null) {
            return "";
        } else {
            str = str.replaceAll("đ", "d").replaceAll("Đ", "d");
            String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(nfdNormalizedString).replaceAll("");
        }
    }

    public static Pageable createPageable(Integer page, Integer pageSize, String direction, String orderBy){
        Sort sort = null;
        if("asc".equalsIgnoreCase(direction))
            sort = Sort.by(orderBy).ascending();
        else
            sort = Sort.by(orderBy).descending();
        return PageRequest.of(page, pageSize, sort);
    }
}
