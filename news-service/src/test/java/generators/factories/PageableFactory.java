package generators.factories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableFactory {

    public static Pageable getDefaultPageable() {
        return PageRequest.of(0, 20);
    }

    public static Pageable getPageableOutOfRange() {
        return PageRequest.of(10, 20);
    }

    public static Pageable getPageableWithSize1() {
        return PageRequest.of(0, 1);
    }

    public static Pageable getPageableWithSize2() {
        return PageRequest.of(0, 2);
    }

    public static Pageable getPageableWithPage2AndSize2() {
        return PageRequest.of(1, 2);
    }

    public static Pageable getDefaultPageableWithSortingByUsernameDesc() {
        return PageRequest.of(0, 20, Sort.Direction.DESC, "username");
    }
}
