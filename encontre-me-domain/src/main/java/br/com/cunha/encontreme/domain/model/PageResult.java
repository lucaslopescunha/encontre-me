package br.com.cunha.encontreme.domain.model;

import java.util.List;

public record PageResult<T>(Integer page,
                            Integer size,
                            Long totalElements,
                            Integer totalPages,
                            List<T> content) {
}
