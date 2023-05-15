package ru.clevertec.users.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableHelper {

    public static Pageable setPageableUnsorted(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        }
        return pageable;
    }
}