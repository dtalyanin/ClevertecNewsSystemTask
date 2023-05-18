package ru.clevertec.news.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Class for verifying that pageable is not sorted
 */
public class PageableHelper {

    /**
     * Check if pageable not sorted else return new pageable with same size and page params but unsorted
     * @param pageable page and maximum size of returning comments collections
     * @return pageable unsorted
     */
    public static Pageable setPageableUnsorted(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        }
        return pageable;
    }
}
