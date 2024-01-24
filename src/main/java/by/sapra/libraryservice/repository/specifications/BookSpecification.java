package by.sapra.libraryservice.repository.specifications;

import by.sapra.libraryservice.models.BookEntity;
import by.sapra.libraryservice.services.model.ServiceFilter;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<BookEntity> withFilter(ServiceFilter filter) {
        return Specification.where(byTitle(filter.getTitle()))
                .and(byAuthor(filter.getAuthor()));
    }

    private static Specification<BookEntity> byAuthor(String author) {
        return (root, query, cb) -> {
            if (author == null) return null;
            return cb.equal(root.get("author"), author);
        };
    }

    private static Specification<BookEntity> byTitle(String title) {
        return (root, query, cb) -> {
            if (title == null) return null;
            return cb.equal(root.get("title"), title);
        };
    }
}
