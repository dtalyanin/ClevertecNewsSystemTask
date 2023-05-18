package generators.factories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.users.models.User;

import java.util.Collections;

import static generators.factories.UserFactory.*;

public class PageFactory {

    public static Page<User> getUsersEmptyPage() {
        return new PageImpl<>(Collections.emptyList());
    }

    public static Page<User> getUsersPage() {
        return new PageImpl<>(getAllUsers());
    }
}
